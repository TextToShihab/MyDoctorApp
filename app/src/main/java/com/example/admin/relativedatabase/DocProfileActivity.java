package com.example.admin.relativedatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class DocProfileActivity extends AppCompatActivity {

    private String docName,docDetails, docEmail, docPhone;
    private int rowId, nowUserId;
    private TextView nameTV, detailsTV, emailTV, phoneTV;
    private DocDatabaseSource docDatabaseSource;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton fabEdit, fabDelete;
    private UserAuthentication userAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_profile);
        docDatabaseSource = new DocDatabaseSource(this);
        nameTV = (TextView) findViewById(R.id.show_doc_name);
        detailsTV = (TextView) findViewById(R.id.show_doc_details);
        emailTV = (TextView) findViewById(R.id.show_doc_email);
        phoneTV = (TextView) findViewById(R.id.show_doc_phone);
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.fam_menu);
        fabEdit = (FloatingActionButton) findViewById(R.id.menu_item_1);
        fabDelete = (FloatingActionButton) findViewById(R.id.menu_item_2);
        userAuthentication = new UserAuthentication(this);

        nowUserId = userAuthentication.getUserId();
        docName = getIntent().getStringExtra("docName");
        docDetails = getIntent().getStringExtra("docDetails");
        docEmail = getIntent().getStringExtra("docEmail");
        docPhone = getIntent().getStringExtra("docPhone");
        rowId = getIntent().getIntExtra("docId",0);

        nameTV.setText(nameTV.getText().toString()+docName);
        detailsTV.setText(detailsTV.getText().toString()+docDetails);
        emailTV.setText(emailTV.getText().toString()+docEmail);
        phoneTV.setText(phoneTV.getText().toString()+docPhone);

        fabEdit.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DocProfileActivity.this,DocProfielEntryActivity.class)
                        .putExtra("nowUserId",nowUserId)
                        .putExtra("docName",docName)
                        .putExtra("docDetails",docDetails)
                        .putExtra("docEmail",docEmail)
                        .putExtra("docPhone",docPhone)
                        .putExtra("docId",rowId));
            }
        });

        fabDelete.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DocProfileActivity.this);
                alert.setTitle("Delete Item");
                alert.setMessage("Are you sure to delete this item ?");
                alert.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean status = docDatabaseSource.deleteDoc(rowId);
                        if(status){
                            Toast.makeText(DocProfileActivity.this, "Item deleted",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DocProfileActivity.this,DocListActivity.class)
                                    .putExtra("nowUserId",nowUserId)
                                    .putExtra("docName",docName)
                                    .putExtra("docDetails",docDetails)
                                    .putExtra("docEmail",docEmail)
                                    .putExtra("docPhone",docPhone)
                                    .putExtra("docId",rowId));
                        }else {
                            Toast.makeText(DocProfileActivity.this, "Couldn't Delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.setNegativeButton("Cancel",null);
                alert.show();
            }
        });
    }
    public void addPrescription(View view) {
        startActivity(new Intent(DocProfileActivity.this, PreEntryActivity.class)
                         .putExtra("nowUserId",nowUserId)
                         .putExtra("docName",docName)
                         .putExtra("docDetails",docDetails)
                         .putExtra("docId",rowId));
    }

    public void showPrescriptions(View view) {
        startActivity(new Intent(DocProfileActivity.this, PreListActivity.class)
                .putExtra("docName",docName)
                .putExtra("docDetails",docDetails)
                .putExtra("docId",rowId));
    }

    public void allDocList(View view) {
        startActivity(new Intent(DocProfileActivity.this, DocListActivity.class)
                .putExtra("nowUserId",nowUserId));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    public void logout(MenuItem item) {
        userAuthentication.deleteNowUser();
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(DocProfileActivity.this, MainActivity.class));
    }
}
