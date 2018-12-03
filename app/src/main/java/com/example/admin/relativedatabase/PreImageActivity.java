package com.example.admin.relativedatabase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PreImageActivity extends AppCompatActivity {

    private ImageView showImageTV;
    private UserAuthentication userAuthentication;
    private String showPreImage, showPreDocName, showPreDocDetails, showPreDate, showPreRemarks;
    private int docId, preId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_image);
        userAuthentication = new UserAuthentication(this);
        showImageTV = (ImageView) findViewById(R.id.fullImage);

        showPreImage = getIntent().getStringExtra("preImage");
        showPreDocName = getIntent().getStringExtra("docName");
        showPreDocDetails = getIntent().getStringExtra("docDetails");
        showPreDate = getIntent().getStringExtra("preDate");
        showPreRemarks = getIntent().getStringExtra("preRemarks");
        preId = getIntent().getIntExtra("preId",0);
        docId = getIntent().getIntExtra("docId",0);

        BitmapFactory.Options bmo = new BitmapFactory.Options();
        bmo.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(showPreImage, bmo);
        bmo.inJustDecodeBounds = false;
        bmo.inSampleSize = 4;
        bmo.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(showPreImage, bmo);
        showImageTV.setImageBitmap(bitmap);
    }

    public void back(View view) {
        startActivity(new Intent(PreImageActivity.this,PreDetailsActivity.class)
                .putExtra("preImage",showPreImage)
                .putExtra("preId",preId)
                .putExtra("docName",showPreDocName)
                .putExtra("docDetails",showPreDocDetails)
                .putExtra("preDate",showPreDate)
                .putExtra("preRemarks",showPreRemarks)
                .putExtra("docId",docId));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.image_menu, menu);
        return true;
    }

    public void logout(MenuItem item) {
        userAuthentication.deleteNowUser();
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PreImageActivity.this, MainActivity.class));
    }
    public void docList(MenuItem item) {
        startActivity(new Intent(PreImageActivity.this,DocListActivity.class));
    }
    public void preList(MenuItem item) {
        startActivity(new Intent(PreImageActivity.this,PreListActivity.class));
    }
}
