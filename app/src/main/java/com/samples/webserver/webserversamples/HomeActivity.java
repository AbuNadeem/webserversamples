package com.samples.webserver.webserversamples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.samples.webserver.webserversamples.log_in_system.ui.log_in.LogInActivity;
import com.samples.webserver.webserversamples.log_in_system.ui.log_in.resetPassword.ResetPasswordActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onClick(View view) {
        Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
        startActivity(intent);
    }
}
