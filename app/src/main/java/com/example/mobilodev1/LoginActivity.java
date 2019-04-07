package com.example.mobilodev1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login Page");
    }
    public void login_onclick(View v){
        TextView un = findViewById(R.id.login_id);
        TextView pas = findViewById(R.id.password);
        if(un.getText().toString().equals("admin") && pas.getText().toString().equals("password"))
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Warning");
            alertDialog.setMessage("Invalid username or password!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }
}
