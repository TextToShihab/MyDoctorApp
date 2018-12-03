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

public class SignUp extends AppCompatActivity {

    private EditText userNameET, userEmailET, userPassET, userConpassET;
    private User user;
    private UserDatabaseSource userDatabaseSource;

    /*private String userName, userEmail, userPass, userConPass;
    private int roweId;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userNameET = (EditText) findViewById(R.id.userName);
        userEmailET = (EditText) findViewById(R.id.userEmail);
        userPassET = (EditText) findViewById(R.id.userPass);
        userConpassET = (EditText) findViewById(R.id.userConPass);
        userDatabaseSource = new UserDatabaseSource(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu,menu);
        return true;
    }

    public void add(MenuItem item) {
        String userName = userNameET.getText().toString();
        String userEmail = userEmailET.getText().toString();
        String userPass = userPassET.getText().toString();
        String userConPass = userConpassET.getText().toString();
        if (userName.isEmpty() || userEmail.isEmpty() || userPass.isEmpty() || userConPass.isEmpty()){
            if (userName.isEmpty()){
                userNameET.setError("Enter Name");
            }
            if (userEmail.isEmpty()){
                userEmailET.setError("Enter Email");
            }
            if (userPass.isEmpty()){
                userPassET.setError("Enter Password");
            }
            if (userConPass.isEmpty()){
                userConpassET.setError("Enter confirm Password");
            }
        }else if (userPass.equals(userConPass)) {
            user = new User(userName, userEmail, userPass, userConPass);
            boolean status = userDatabaseSource.addUser(user);
            if(status){
                Toast.makeText(this,"Sign Up Complete",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUp.this,MainActivity.class));
            }else {
                Toast.makeText(this,"Could not Sing Up",Toast.LENGTH_SHORT).show();
            }
        }else {
            userPassET.setError("Password & Confirm Password not Match");
            userConpassET.setError("Password & Confirm Password not Match");
        }
    }

    public void login(View view) {
        startActivity(new Intent(SignUp.this, MainActivity.class));
    }
}
