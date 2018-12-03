package com.example.admin.relativedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText emailET, passET;
    private String userEmail, userPass;
    private int userId;
    private UserDatabaseSource userDatabaseSource;
    private UserAuthentication userAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailET = (EditText) findViewById(R.id.userEmail);
        passET = (EditText) findViewById(R.id.userPass);
        userDatabaseSource = new UserDatabaseSource(this);
        userAuthentication = new UserAuthentication(this);

        userId = userAuthentication.getUserId();
        userEmail = userAuthentication.getUserEmail();
        userPass = userAuthentication.getUserPass();
        if(userEmail.equals(userAuthentication.DEFAULT_MSG) && userPass.equals(userAuthentication.DEFAULT_MSG)){

        }else {
            startActivity(new Intent(MainActivity.this, DocListActivity.class)
                    .putExtra("nowUserID",userId));
        }
    }

    public void login(View view) {
        String userEmailIn = emailET.getText().toString();
        String userPassIn = passET.getText().toString();
        if (userEmailIn.isEmpty() || userPassIn.isEmpty()){
            if (userEmailIn.isEmpty()){
                emailET.setError("Enter Email");
            }
            if (userPassIn.isEmpty()){
                passET.setError("Enter Password");
            }
        }else {
            boolean status = userDatabaseSource.matchUser(userEmailIn,userPassIn);

            if (status){
                Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
                userAuthentication.saveNowUser(userId, userEmailIn, userPassIn);
                startActivity(new Intent(MainActivity.this,DocListActivity.class)
                        .putExtra("nowUserId",userId));
            }else{
                Toast.makeText(this,"Account Doesn't Match",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void signUp(View view) {
        startActivity(new Intent(MainActivity.this, SignUp.class));
    }
}
