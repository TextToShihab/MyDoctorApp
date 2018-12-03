package com.example.admin.relativedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DocProfielEntryActivity extends AppCompatActivity {

    private EditText nameET, detailsET, emailET, phoneET;
    private Doctor doctor;
    private DocDatabaseSource docDatabaseSource;
    private UserDatabaseSource userDatabaseSource;
    private UserAuthentication userAuthentication;

    private String docName, docDetails, docEmail, docPhone;
    private int userId, docId, nowUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_profiel_entry);
        nameET = (EditText) findViewById(R.id.doc_name);
        detailsET = (EditText) findViewById(R.id.doc_details);
        emailET = (EditText) findViewById(R.id.doc_email);
        phoneET = (EditText) findViewById(R.id.doc_phone);

        docDatabaseSource = new DocDatabaseSource(this);
        userDatabaseSource = new UserDatabaseSource(this);
        userAuthentication = new UserAuthentication(this);
        userId = userDatabaseSource.getUserId();

        nowUserId = userAuthentication.getUserId();
        docId = getIntent().getIntExtra("docId",0);
        userId = getIntent().getIntExtra("userID",0);
        docName = getIntent().getStringExtra("docName");
        docDetails = getIntent().getStringExtra("docDetails");
        docEmail = getIntent().getStringExtra("docEmail");
        docPhone = getIntent().getStringExtra("docPhone");

        nameET.setText(docName);
        detailsET.setText(docDetails);
        emailET.setText(docEmail);
        phoneET.setText(docPhone);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        MenuInflater inflater2 = getMenuInflater();
        inflater2.inflate(R.menu.add_menu,menu);
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }
    public void logout(MenuItem item) {
        userAuthentication.deleteNowUser();
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(DocProfielEntryActivity.this, MainActivity.class));
    }
    public void add(MenuItem item) {
        String name = nameET.getText().toString();
        String details = detailsET.getText().toString();
        String email = emailET.getText().toString();
        String phone = phoneET.getText().toString();
        if(name.isEmpty() && details.isEmpty() && email.isEmpty() && phone.isEmpty()){
            if(name.isEmpty()){
                nameET.setError("Enter Doctor Name");
            }
            if(details.isEmpty()){
                detailsET.setError("Enter Doctor Details");
            }
            if(email.isEmpty()){
                emailET.setError("Enter Doctor Email");
            }
            if(phone.isEmpty()){
                phoneET.setError("Enter Doctor Phone");
            }
        }else {
            if(docId > 0){
                doctor = new Doctor(nowUserId,name,details,email,phone, docId);
                boolean status = docDatabaseSource.updateDoctor(doctor, docId);
                if(status){
                    Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DocProfielEntryActivity.this,DocListActivity.class)
                            .putExtra("docId", docId)
                            .putExtra("userId", userId)
                            .putExtra("docName",name)
                            .putExtra("docDetails",details)
                            .putExtra("docEmail",email)
                            .putExtra("docPhone",phone));
                }else {
                    Toast.makeText(this,"Failed to update",Toast.LENGTH_SHORT).show();
                }
            }else {
                doctor = new Doctor(nowUserId,name,details,email,phone);
                boolean status = docDatabaseSource.addDoctor(doctor);
                if(status){
                    Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DocProfielEntryActivity.this,DocListActivity.class)
                            .putExtra("userId", userId)
                            .putExtra("docId", docId)
                            .putExtra("docName",name)
                            .putExtra("docDetails",details)
                            .putExtra("docEmail",email)
                            .putExtra("docPhone",phone));
                }else {
                    Toast.makeText(this,"Could not save",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public void showList(View view) {
        startActivity(new Intent(DocProfielEntryActivity.this,DocListActivity.class));
    }
}
