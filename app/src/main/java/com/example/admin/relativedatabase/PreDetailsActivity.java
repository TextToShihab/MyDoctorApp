package com.example.admin.relativedatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class PreDetailsActivity extends AppCompatActivity {

    private ImageView showPreImageIV;
    private TextView showPreDocNameTV, showPreDocDetailsTV, showPreDateTV, showPreRemarksTV;
    private PreDatabaseSource preDatabaseSource;
    private UserAuthentication userAuthentication;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton fabEdit, fabDelete;
    private String showPreImage, showPreDocName, showPreDocDetails, showPreDate, showPreRemarks;
    private int docId, preId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_details);
        preDatabaseSource = new PreDatabaseSource(this);
        userAuthentication = new UserAuthentication(this);
        showPreImageIV = (ImageView) findViewById(R.id.showPreImage);
        showPreDocNameTV = (TextView) findViewById(R.id.showPreDocName);
        showPreDocDetailsTV = (TextView) findViewById(R.id.showPreDocDetails);
        showPreDateTV = (TextView) findViewById(R.id.showPreDate);
        showPreRemarksTV = (TextView) findViewById(R.id.showPreRemarks);
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.fam_menu);
        fabEdit = (FloatingActionButton) findViewById(R.id.menu_item_1);
        fabDelete = (FloatingActionButton) findViewById(R.id.menu_item_2);

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
        bmo.inSampleSize = 20;
        bmo.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(showPreImage, bmo);
        showPreImageIV.setImageBitmap(bitmap);

        showPreDocNameTV.setText(showPreDocName);
        showPreDocDetailsTV.setText(showPreDocDetails);
        showPreDateTV.setText(showPreDate);
        showPreRemarksTV.setText(showPreRemarks);
        fabEdit.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreDetailsActivity.this,PreEntryActivity.class)
                        .putExtra("preImage",showPreImage)
                        .putExtra("preId",preId)
                        .putExtra("docName",showPreDocName)
                        .putExtra("docDetails",showPreDocDetails)
                        .putExtra("preDate",showPreDate)
                        .putExtra("preRemarks",showPreRemarks)
                        .putExtra("docId",docId));

            }
        });

        fabDelete.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(PreDetailsActivity.this);
                alert.setTitle("Delete Item");
                alert.setMessage("Are you sure to delete this item ?");
                alert.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean status = preDatabaseSource.deletePre(preId);
                        if(status){
                            Toast.makeText(PreDetailsActivity.this, "Item deleted",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PreDetailsActivity.this,PreListActivity.class)
                                    .putExtra("preImage",showPreImage)
                                    .putExtra("docId",docId)
                                    .putExtra("docName",showPreDocName)
                                    .putExtra("docDetails",showPreDocDetails));
                        }else {
                            Toast.makeText(PreDetailsActivity.this, "Couldn't Delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.setNegativeButton("Cancel",null);
                alert.show();
            }
        });
    }


    public void ShowImage(View view) {
        startActivity(new Intent(PreDetailsActivity.this,PreImageActivity.class)
                .putExtra("preImage",showPreImage)
                .putExtra("preId",preId)
                .putExtra("docName",showPreDocName)
                .putExtra("docDetails",showPreDocDetails)
                .putExtra("preDate",showPreDate)
                .putExtra("preRemarks",showPreRemarks)
                .putExtra("docId",docId));
    }

    public void allPreList(View view) {
        startActivity(new Intent(PreDetailsActivity.this,PreListActivity.class)
                .putExtra("docName",showPreDocName)
                .putExtra("docDetails",showPreDocDetails)
                .putExtra("docId",docId));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pre_details_menu, menu);
        return true;
    }

    public void logout(MenuItem item) {
        userAuthentication.deleteNowUser();
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PreDetailsActivity.this, MainActivity.class));
    }
    public void docList(MenuItem item) {
        startActivity(new Intent(PreDetailsActivity.this,DocListActivity.class));
    }
}
