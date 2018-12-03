package com.example.admin.relativedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

public class PreListActivity extends AppCompatActivity {

    private ListView preList;
    private TextView preNoValueTV;
    private PreAdapter preAdapter;
    private ArrayList<Prescription> prescriptions;
    private PreDatabaseSource preDatabaseSource;
    private FloatingActionButton preFab;
    private UserAuthentication userAuthentication;
    private String preDocName, preDocDetails;
    private int docId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_list);
        preList = (ListView) findViewById(R.id.preList);
        preNoValueTV = (TextView) findViewById(R.id.preNoValue);
        preFab = (FloatingActionButton) findViewById(R.id.pre_fab);
        userAuthentication = new UserAuthentication(this);
        preDatabaseSource = new PreDatabaseSource(this);
        prescriptions = preDatabaseSource.getAllPre();

        preDocName = getIntent().getStringExtra("docName");
        preDocDetails = getIntent().getStringExtra("docDetails");
        docId = getIntent().getIntExtra("docId",0);

        preFab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreListActivity.this,PreEntryActivity.class)
                        .putExtra("docId", docId)
                        .putExtra("docName",preDocName)
                        .putExtra("docDetails",preDocDetails));
            }
        });
        preAdapter = new PreAdapter(this, prescriptions);
        if(prescriptions.equals(null)){
            preNoValueTV.setText("NO VALUE FOUND");
        }
        preList.setAdapter(preAdapter);
        preList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String preDate = prescriptions.get(position).getPreDate();
                String preImage = prescriptions.get(position).getPreImage();
                String preDocName = prescriptions.get(position).getPreDocName();
                String preDocDetails = prescriptions.get(position).getPreDocDetails();
                int docId = prescriptions.get(position).getDocId();
                int rowPreId = prescriptions.get(position).getPreId();
                startActivity(new Intent(PreListActivity.this,PreDetailsActivity.class)
                        .putExtra("docName", preDocName)
                        .putExtra("docDetails", preDocDetails)
                        .putExtra("preId",rowPreId)
                        .putExtra("preDate",preDate)
                        .putExtra("preImage",preImage)
                        .putExtra("docId",docId));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pre_list_menu, menu);
        return true;
    }

    public void logout(MenuItem item) {
        userAuthentication.deleteNowUser();
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PreListActivity.this, MainActivity.class));
    }

    public void docList(MenuItem item) {
        startActivity(new Intent(PreListActivity.this,DocListActivity.class));
    }
}
