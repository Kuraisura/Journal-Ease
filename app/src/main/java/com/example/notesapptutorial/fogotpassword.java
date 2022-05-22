package com.example.notesapptutorial;

import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class fogotpassword extends AppCompatActivity {

    private EditText forgotPasswordEditText;
    private RelativeLayout passwordRecoverButton;
    private RelativeLayout goBackToLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogotpassword);

        getSupportActionBar().hide();

        forgotPasswordEditText = findViewById(R.id.forgotpassword);
        passwordRecoverButton = findViewById(R.id.passwordrecoverbutton);
        goBackToLoginTextView = findViewById(R.id.gobacktologin);

        goBackToLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fogotpassword.this, MainActivity.class));
            }
        });

        passwordRecoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = forgotPasswordEditText.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your email first", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Demo mode: no reset email is sent. Use any password on login.", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(fogotpassword.this, MainActivity.class));
                }
            }
        });
    }
}
