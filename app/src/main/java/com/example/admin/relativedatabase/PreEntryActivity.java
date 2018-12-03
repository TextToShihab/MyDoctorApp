package com.example.admin.relativedatabase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PreEntryActivity extends AppCompatActivity {

    private EditText preDateET;
    private TextView preDocNameTV, preDocDetailsTV;
    private Prescription prescription;
    private PreDatabaseSource preDatabaseSource;
    private UserAuthentication userAuthentication;
    private Button imageBtn, addBtn;

    private String  preDate, perImage, preDocName, preDocDetails;
    private int imagea, preId, docId;
    private String date;

    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    private ImageView imageView;
    private File image;
    private String imageFileName;

    String dName,dDetails; int dId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_entry);
        preDateET = (EditText) findViewById(R.id.preDateET);
        preDocNameTV = (TextView) findViewById(R.id.preDocNameTV);
        preDocDetailsTV = (TextView) findViewById(R.id.preDocDetailsTV);
        imageBtn = (Button) findViewById(R.id.addPreImage);
        addBtn = (Button) findViewById(R.id.saveBtn);
        imageView = (ImageView) findViewById(R.id.preImage);
        userAuthentication = new UserAuthentication(this);
        preDatabaseSource = new PreDatabaseSource(this);

        preId = getIntent().getIntExtra("preId",0);
        docId = getIntent().getIntExtra("docId",0);
        preDate = getIntent().getStringExtra("preDate");
        perImage = getIntent().getStringExtra("preImage");
        imagea = getIntent().getIntExtra("image",0);
        preDocName = getIntent().getStringExtra("docName");
        preDocDetails = getIntent().getStringExtra("docDetails");

        Bitmap bm = BitmapFactory.decodeFile(perImage);
        preDateET.setText(preDate);
        imageView.setImageBitmap(bm);

        dName= getIntent().getStringExtra("docName");
        dDetails= getIntent().getStringExtra("docDetails");
        dId= getIntent().getIntExtra("docId",0);

        preDateET.setText(preDate);/*
        imageBtn.setText(image);*/
        if(preId > 0){
            addBtn.setText("Update");
        }
    }
    public void addPreBtn(View view) {
        dispatchTakePictureIntent();

    }

    public void Save(View view) {
         date = preDateET.getText().toString();
         imagea = imageBtn.getImeActionId();

        if(date.isEmpty()){
            preDateET.setError("Enter Date");
        }else {
            if(preId > 0){
                prescription = new Prescription(docId,perImage,date,preDocName,preDocDetails,preId);
                boolean status = preDatabaseSource.updatePre(prescription, preId);
                if(status){
                    Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PreEntryActivity.this,PreListActivity.class)
                            .putExtra("docId", docId)
                            .putExtra("docName",preDocName)
                            .putExtra("docDetails",preDocDetails));
                }else {
                    Toast.makeText(this,"Failed to update",Toast.LENGTH_SHORT).show();
                }
            }else {
                prescription = new Prescription(docId,perImage, date,preDocName,preDocDetails);
                boolean status = preDatabaseSource.addPre(prescription);
                if(status){
                    Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PreEntryActivity.this,PreListActivity.class)
                            .putExtra("docId", docId)
                            .putExtra("docName",preDocName)
                            .putExtra("docDetails",preDocDetails));
                }else {
                    Toast.makeText(this,"Could not save",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void ShowList(View view) {
        startActivity(new Intent(PreEntryActivity.this,PreListActivity.class)
                .putExtra("docId", docId)
                .putExtra("docName",preDocName)
                .putExtra("docDetails",preDocDetails));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        MenuInflater inflater2 = getMenuInflater();
        inflater.inflate(R.menu.add_menu,menu);
        inflater2.inflate(R.menu.logout_menu, menu);
        return true;
    }

    public void logout(MenuItem item) {
        userAuthentication.deleteNowUser();
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PreEntryActivity.this, MainActivity.class));
    }
    public void add(MenuItem item) {
         date = preDateET.getText().toString();
        if(date.isEmpty()){
            preDateET.setError("Enter Doctor Name");
        }else {
            if(preId > 0){
                prescription = new Prescription(docId,perImage,date,preDocName,preDocDetails,preId);
                boolean status = preDatabaseSource.updatePre(prescription,preId);
                if(status){
                    Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PreEntryActivity.this,PreListActivity.class)
                            .putExtra("docId", docId)
                            .putExtra("docName",preDocName)
                            .putExtra("docDetails",preDocDetails));
                }else {
                    Toast.makeText(this,"Failed to update",Toast.LENGTH_SHORT).show();
                }
            }else {
                prescription = new Prescription(docId,perImage,date,preDocName,preDocDetails);
                boolean status = preDatabaseSource.addPre(prescription);
                if(status){
                    Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PreEntryActivity.this,PreListActivity.class)
                            .putExtra("docId", docId)
                            .putExtra("docName",preDocName)
                            .putExtra("docDetails",preDocDetails));
                }else {
                    Toast.makeText(this,"Could not save",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = Uri.fromFile(image);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            int targetW = imageView.getWidth();
            int targetH = imageView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            imageView.setImageBitmap(bitmap);
            perImage = mCurrentPhotoPath;

            /*Bitmap mBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            imageView.setImageBitmap(mBitmap);*/
            Toast.makeText(this, imageFileName, Toast.LENGTH_SHORT).show();

        }
    }

}
