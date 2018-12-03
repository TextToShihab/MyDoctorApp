package com.example.admin.relativedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

public class DocListActivity extends AppCompatActivity {

    private ListView docList;
    private TextView noValueTV;
    private DocAdapter docAdapter;
    private ArrayList<Doctor> doctors;
    private DocDatabaseSource docDatabaseSource;
    private FloatingActionButton fab;
    private UserAuthentication userAuthentication;
    private int nowUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_list);
        docList = (ListView) findViewById(R.id.docList);
        noValueTV = (TextView) findViewById(R.id.noValue);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        docDatabaseSource = new DocDatabaseSource(this);
        userAuthentication = new UserAuthentication(this);
        /*nowUserId = userAuthentication.getUserId();*/
        nowUserId = getIntent().getIntExtra("nowUserId",0);
        doctors = docDatabaseSource.getAllDoctor(nowUserId);

        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DocListActivity.this,DocProfielEntryActivity.class));
            }
        });
        docAdapter = new DocAdapter(this, doctors);
        if(doctors == null){
            noValueTV.setText("NO VALUE FOUND");
        }
        docList.setAdapter(docAdapter);
        docList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String docName = doctors.get(position).getDocName();
                String docDetails = doctors.get(position).getDocDetails();
                String docEmail = doctors.get(position).getDocEmail();
                String docPhone = doctors.get(position).getDocPhone();
                int docId = doctors.get(position).getDocId();
                startActivity(new Intent(DocListActivity.this,
                        DocProfileActivity.class)
                        .putExtra("nowUserId",nowUserId)
                        .putExtra("docId",docId)
                        .putExtra("docName",docName)
                        .putExtra("docDetails",docDetails)
                        .putExtra("docEmail",docEmail)
                        .putExtra("docPhone",docPhone));
            }
        });
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
        startActivity(new Intent(DocListActivity.this, MainActivity.class));
    }
}
