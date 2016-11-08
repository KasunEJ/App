package com.birdlogbook.sajja.birdlogbook.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.birdlogbook.sajja.birdlogbook.db.DataBaseWrapper;
import com.birdlogbook.sajja.birdlogbook.model.Image;

import java.io.ByteArrayOutputStream;

/**
 * Created by sajja on 9/7/2016.
 */
public class ImageController {
    private static SQLiteDatabase sqLiteDatabase;
    private static DataBaseWrapper dataBaseWrapper;
    private static Context mContext;


    public ImageController(Context context){
        this.mContext=context;
        dataBaseWrapper =DataBaseWrapper.getHelper(mContext);
        openWritable();

    }

    public static void openWritable(){
        if (sqLiteDatabase==null){
            dataBaseWrapper=DataBaseWrapper.getHelper(mContext);
        }
        sqLiteDatabase=dataBaseWrapper.getWritableDatabase();

    }

    public void close(){
        dataBaseWrapper.close();

    }
    public static boolean addImage(Image image){

        ContentValues values=new ContentValues();
        values.put("photoDir",image.getphotoDir());
        values.put("lx",image.getxI());
        values.put("ly",image.getyI());
        values.put("date", image.getDate());
        values.put("time", image.getTime());
        long result=sqLiteDatabase.insert("image",null,values);
        if(result==-1){
            return false;
        }

        return true;
    }
  /*  public static boolean addImage1(byte[] image){
        ContentValues values=new ContentValues();
        //values.put("IID",image.getIID());

        values.put("a",image);
        long result=sqliteDatabase.insert("image",null,values);
        if(result==-1){
            return false;
        }

        return true;
    }*/
    public static boolean deleteImage(int id){


        int result=sqLiteDatabase.delete("image","iid='"+id+"'",null);
        if (result==-1){
            return false;
        }
        return true;
    }
    public static boolean isImageAvaliable(int IID){

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT iid FROM image WHERE iid='"+IID+"'", null);
        if (cursor.getCount()==0){
            return false;
        }
        return true;

    }
    public static int getLastImageID(){

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT iid FROM image ORDER BY iid DESC LIMIT 1", null);

        if (cursor.getCount()>0) {
           return cursor.getInt(cursor.getColumnIndex("iid"));
        }
        return 0;
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,outputStream);
        return outputStream.toByteArray();
    }
    public static Image getAllByPhotoDir(Image image){


        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM image WHERE photoDir='"+image.getphotoDir()+"'",null);
        //cursor.moveToFirst();
        if (cursor.getCount()>0) {
            Image image1 = new Image();
            image1.setxI(cursor.getDouble(cursor.getColumnIndex("lx")));
            image1.setyI(cursor.getDouble(cursor.getColumnIndex("ly")));
            image1.setDate(cursor.getString(cursor.getColumnIndex("date")));
            image1.setTime(cursor.getString(cursor.getColumnIndex("time")));

            return image1;
        }
        return null;
    }
}
