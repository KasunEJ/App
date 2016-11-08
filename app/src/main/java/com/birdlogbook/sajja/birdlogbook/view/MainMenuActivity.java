package com.birdlogbook.sajja.birdlogbook.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.birdlogbook.sajja.birdlogbook.R;
import com.birdlogbook.sajja.birdlogbook.controller.ImageController;
import com.birdlogbook.sajja.birdlogbook.controller.ImageSaver;
import com.birdlogbook.sajja.birdlogbook.controller.LogNoteController;
import com.birdlogbook.sajja.birdlogbook.model.Image;
import com.birdlogbook.sajja.birdlogbook.model.LogNote;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainMenuActivity extends AppCompatActivity {
    private  static ImageSaver imageSaver=null;
    ImageController imageController=null;
    int lastImageID=11;

    static final int request_image_capture=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imageSaver=null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        guiCreate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==request_image_capture && resultCode==RESULT_OK ) {
            Bundle extra = data.getExtras();
            Bitmap photo = (Bitmap) extra.get("data");


            try {
                imageController=new ImageController(this);
                //WANT CHANGE*************************
               lastImageID =imageController.getLastImageID();

                 //Toast.makeText(this,cursor.getString(cursor.getColumnIndex("iid")),Toast.LENGTH_SHORT).show();

                imageSaver = new ImageSaver(this);
                imageSaver.setFileName(String.valueOf(lastImageID)+".jpeg").save(photo);
                imageSaver.setBitmapPhoto(photo);

                //get system date and time
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
                String date = df.format(c.getTime());
                String time = tf.format(c.getTime());

                Image image=new Image(lastImageID,imageSaver.getUri().toString(),0.3,0.6,date,time);
                boolean imgRes = imageController.addImage(image);
                if (imgRes){
                    Toast.makeText(this,"photo saved",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"fail to save",Toast.LENGTH_SHORT).show();

                }


            }finally {
                imageController.close();

            }

            //  Bitmap bitmap=new ImageSaver(this).setFileName("00002.png").setDirectoryName("imagesmy").load();


            // byte[] bitmapAsByteArray = ImageController.getBitmapAsByteArray(photo);
            //  new ImageController(this);
            //boolean b = ImageController.addImage(bitmapAsByteArray);
            //  if (b){
            //   Toast.makeText(this,bitmapAsByteArray.length,Toast.LENGTH_SHORT).show();
            // }
        }
    }

    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }



    private void cameraButtonOnClick(View view){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, request_image_capture );
    }
    private void newLogNoteButtonOnClick(View view){
        Intent intent=new Intent(this,NewLogNoteActivity.class);
        startActivity(intent);

    }
    private void galleryButtonOnClick(View view){
        Intent intent=new Intent(this,GalleryActivity.class);
        startActivity(intent);

    }
    private void logBookButtonOnClick(View view){
        LogNote logNote=new LogNote();
        logNote.setlNID(1);
        LogNoteController logNoteController=new LogNoteController(this);
        LogNote logNote1 = logNoteController.searchByLNID(logNote);
    Toast.makeText(this,logNote1.getDate(),Toast.LENGTH_SHORT).show();

        //Intent intent=new Intent(this,LogBookActivity.class);
       // startActivity(intent);

    }
    private void mapButtonOnclick(){
       // Intent intent=new Intent(this,MapActivity.class);
        //startActivity(intent);
    }
    private void logOutButtonOnClick(View view){

        finish();
        startActivity(new Intent(this,LogInActivity.class));
    }
    private void guiCreate() {
        cameraButton = (ImageButton) findViewById(R.id.cameraButton);
        newLogNoteButton = (ImageButton) findViewById(R.id.newLogNoteButton);
        galleryButton = (ImageButton) findViewById(R.id.galleryButton);
        logBookButton = (ImageButton) findViewById(R.id.logBookButton);
        mapButton = (ImageButton) findViewById(R.id.mapBtn);
        logOutButton = (ImageButton) findViewById(R.id.logOutBtn);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraButtonOnClick(view);
            }
        });

        newLogNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newLogNoteButtonOnClick(view);
            }
        });
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryButtonOnClick(view);
            }
        });
        logBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logBookButtonOnClick(view);
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapButtonOnclick();

            }
        });
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutButtonOnClick(view);
            }
        });
    }


    //gui define
    private ImageButton cameraButton;
    private ImageButton newLogNoteButton;
    private ImageButton galleryButton;
    private ImageButton logBookButton;
    private ImageButton mapButton;
    private ImageButton logOutButton;
}
