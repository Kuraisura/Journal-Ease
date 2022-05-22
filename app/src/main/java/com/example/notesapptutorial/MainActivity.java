package com.example.notesapptutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText loginEmailEditText, loginPasswordEditText;
    private RelativeLayout loginRelativeLayout, gotoSignupRelativeLayout;
    private TextView gotoForgotPasswordTextView;
    private ProgressBar mainActivityProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        loginEmailEditText = findViewById(R.id.loginemail);
        loginPasswordEditText = findViewById(R.id.loginpassword);
        loginRelativeLayout = findViewById(R.id.login);
        gotoForgotPasswordTextView = findViewById(R.id.gotoforgotpassword);
        gotoSignupRelativeLayout = findViewById(R.id.gotosignup);
        mainActivityProgressBar = findViewById(R.id.progressbarofmainactivity);

        if (DemoSession.isLoggedIn(this)) {
            startActivity(new Intent(MainActivity.this, notesactivity.class));
            finish();
            return;
        }

        gotoSignupRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, signup.class));
            }
        });

        gotoForgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, fogotpassword.class));
            }
        });

        loginRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmailEditText.getText().toString().trim();
                String password = loginPasswordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    mainActivityProgressBar.setVisibility(View.VISIBLE);
                    DemoSession.login(MainActivity.this);
                    LocalNoteStore.get(MainActivity.this);
                    Toast.makeText(getApplicationContext(), "Logged in (demo mode)", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, notesactivity.class));
                    finish();
                }
            }
        });
    }
}
