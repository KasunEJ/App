package com.birdlogbook.sajja.birdlogbook.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.birdlogbook.sajja.birdlogbook.R;
import com.birdlogbook.sajja.birdlogbook.controller.ImageController;
import com.birdlogbook.sajja.birdlogbook.controller.ImageSaver;
import com.birdlogbook.sajja.birdlogbook.controller.LogNoteController;
import com.birdlogbook.sajja.birdlogbook.model.Image;
import com.birdlogbook.sajja.birdlogbook.model.LogNote;
import com.birdlogbook.sajja.birdlogbook.validation.SpinnerListCreate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class NewLogNoteActivity extends AppCompatActivity {
    static final int request_image_capture=1;
    private ImageSaver imageSaver=null;
    private  Bitmap photo=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_log_note);
        guiCreate();



        //set province list
        List<String> provinceList = SpinnerListCreate.setProvinceSpinner();
        ArrayAdapter<String> provincrArrayAdapter=new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item,provinceList);
        provincrArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item);
        provinceSpinner.setAdapter(provincrArrayAdapter);

        //set Habitat list
        List<String> habitatList = SpinnerListCreate.setHabitatSpinner();
        ArrayAdapter<String> habitatArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,habitatList);
        habitatArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        habitatSpinner.setAdapter(habitatArrayAdapter);

        //set elevation list
        List<String> elevationList = SpinnerListCreate.setElevationSpinner();
        ArrayAdapter<String> elevationArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,elevationList);
        elevationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        elevationSpinner.setAdapter(elevationArrayAdapter);

        //set look like list
        List<String> lookLikeList = SpinnerListCreate.setLookLikeSpinner();
        ArrayAdapter<String> lookLikeArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lookLikeList);
        lookLikeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        looksLikeSpinner.setAdapter(lookLikeArrayAdapter);

        //set shape list
        List<String> shapeList  = SpinnerListCreate.setShapeSpinner();
        ArrayAdapter<String> shapeArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,shapeList);
        shapeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        shapeSpinner.setAdapter(shapeArrayAdapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==request_image_capture &&resultCode==RESULT_OK){
            //String data1 =data.getData().toString();
           // Toast.makeText(this,data1,Toast.LENGTH_SHORT).show();
            Bundle extras=data.getExtras();
            photo = (Bitmap) extras.get("data");
            newLogNoteImageView.setImageBitmap(photo);

        }
    }

    private void provinceSpinnerOnTouch(View view){
        try {

            String province = (String) provinceSpinner.getSelectedItem();
            List<String> cityList = SpinnerListCreate.setCitySpinner(province);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            nearstCitySpinner.setAdapter(arrayAdapter);
        }finally {

        }

    }
    private void saveButtonOnClick(View view){
        try {
            String province = provinceSpinner.getSelectedItem().toString();
            String city = nearstCitySpinner.getSelectedItem().toString();
            String elevation = elevationSpinner.getSelectedItem().toString();
            String habitat = habitatSpinner.getSelectedItem().toString();
            // String special = specialSpinner.getSelectedItem().toString();
            String lookLike = looksLikeSpinner.getSelectedItem().toString();
            String lx = lxEditText.getText().toString();
            String ly = lyEditText.getText().toString();
            String temperature = temperatureEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String shape = shapeSpinner.getSelectedItem().toString();
            String sizeHight = sizeHightEditText.getText().toString();
            String sizeWidth = sizeWidthEditText.getText().toString();
            String color = colorEditText.getText().toString();
            String behaviour = behaviourEditText.getText().toString();
            String otherNote = otherNoteEditText.getText().toString();

            if (province.equals("")){
                province="empty";
            }
            if (city.equals("")){
                city="empty";
            }
            if (elevation.equals("")){
                elevation="0";
            }
            if (habitat.equals("")){
                habitat="empty";
            }
            if (lookLike.equals("")){
                lookLike="empty";
            }
            if (lx.equals("")){
                lx="0";
            }
            if (ly.equals("")){
                ly="0";
            }
            if (temperature.equals("")){
                temperature="0";
            }
            if (name.equals("")){
                name="empty";
            }
            if (shape.equals("")){
                shape="empty";
            }
            if (sizeHight.equals("")){
                sizeHight="0";
            }
            if (sizeWidth.equals("")){
                sizeWidth="0";
            }
            if (color.equals("")){
                color="empty";
            }
            if (behaviour.equals("")){
                behaviour="empty";
            }
            if (otherNote.equals("")){
                otherNote="empty";
            }


            //get system date and time
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
            String date = df.format(c.getTime());
            String time = tf.format(c.getTime());

            Image image = new Image();
            image.setxI(Double.valueOf(lx));
            image.setyI(Double.valueOf(ly));
            image.setDate(date);
            image.setTime(time);

            ImageController ic = new ImageController(this);

            boolean imgRes = ic.addImage(image);
            int lastImageID = 6;
            //= ic.getLastImageID();

            LogNote logNote = new LogNote();


            logNote.setiID(lastImageID);
            logNote.setDate(date);
            logNote.setTime(time);
            logNote.setProvince(province);
            logNote.setNearestCity(city);
            logNote.setElevation(elevation);
            logNote.setlX(Double.parseDouble(lx));
            logNote.setlY(Double.parseDouble(ly));
            logNote.setHabitat(habitat);
            logNote.setSpecial("tt");
            logNote.setLooksLike(lookLike);
            logNote.setTemperature(Double.valueOf(temperature));
            logNote.setName(name);
            logNote.setShape(shape);
            logNote.setSizeHight(Double.valueOf(sizeHight));
            logNote.setSizeWidth(Double.valueOf(sizeWidth));
            logNote.setColour(color);
            logNote.setBehaviour(behaviour);
            logNote.setOtherNote(otherNote);



            //Toast.makeText(this, logNote.toString(), Toast.LENGTH_SHORT).show();

            LogNoteController A = new LogNoteController(this);
            boolean logNoteRes = A.addLogNote(logNote);
            imageSaver=new ImageSaver(this);
            imageSaver.setFileName(lastImageID+".jpeg").save(photo);

            if (imgRes && logNoteRes) {
                Toast.makeText(this, "Added to library", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainMenuActivity.class));
            } else {
                Toast.makeText(this, "cannot add to library", Toast.LENGTH_SHORT).show();
            }

        }finally {


        }

    }
    private void cameraButtonOnClick(View view){
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, request_image_capture);
        }finally {

        }
    }
    private void guiCreate(){
        provinceSpinner=(Spinner)findViewById(R.id.provinceSpinner);
        nearstCitySpinner=(Spinner)findViewById(R.id.nearestCitySpinner);
        elevationSpinner=(Spinner)findViewById(R.id.elevationSpinner);
        habitatSpinner=(Spinner)findViewById(R.id.habitatSpinner);
        specialSpinner=(Spinner)findViewById(R.id.specialSpinner);
        looksLikeSpinner=(Spinner)findViewById(R.id.looksLikeSpinner);
        shapeSpinner=(Spinner)findViewById(R.id.shapeSpinner);

        lxEditText=(EditText)findViewById(R.id.lxText);
        lyEditText=(EditText)findViewById(R.id.lyText);
        temperatureEditText=(EditText)findViewById(R.id.temText);
        nameEditText=(EditText)findViewById(R.id.nameText);
        sizeWidthEditText=(EditText)findViewById(R.id.widthText);
        sizeHightEditText=(EditText)findViewById(R.id.hightText);
        colorEditText=(EditText)findViewById(R.id.colorText);
        behaviourEditText=(EditText)findViewById(R.id.behaviourText);
        otherNoteEditText=(EditText)findViewById(R.id.otherNoteText);

        newLogNoteImageView=(ImageView)findViewById(R.id.logNoteimageView);
        
        cameraButton=(Button)findViewById(R.id.cameraNewLogNoteButton);
        galleryButton=(Button)findViewById(R.id.galleryNewLogNoteButton);
        savebutton=(Button)findViewById(R.id.saveLogNoteButton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonOnClick(view);
            }
        });

        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                provinceSpinnerOnTouch(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraButtonOnClick(view);

            }
        });

    }
    //gui define
    private Spinner provinceSpinner;
    private Spinner nearstCitySpinner;
    private Spinner elevationSpinner;
    private Spinner habitatSpinner;
    private Spinner specialSpinner;
    private Spinner looksLikeSpinner;
    private Spinner shapeSpinner;

    private EditText lxEditText;
    private EditText lyEditText;
    private EditText temperatureEditText;
    private EditText nameEditText;
    private EditText sizeWidthEditText;
    private EditText sizeHightEditText;
    private EditText colorEditText;
    private EditText behaviourEditText;
    private EditText otherNoteEditText;

    private ImageView newLogNoteImageView;
    
    private Button cameraButton;
    private Button galleryButton;
    private Button savebutton;

}
