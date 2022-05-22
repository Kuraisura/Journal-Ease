package com.example.notesapptutorial;

import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class createnote extends AppCompatActivity {

    private EditText mCreateTitleOfNote, mCreateContentOfNote;
    private FloatingActionButton mSaveNote;
    private ProgressBar mProgressBarOfCreateNote;

    private Button clearFieldsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);

        mSaveNote = findViewById(R.id.savenote);
        mCreateContentOfNote = findViewById(R.id.createcontentofnote);
        mCreateTitleOfNote = findViewById(R.id.createtitleofnote);
        mProgressBarOfCreateNote = findViewById(R.id.progressbarofcreatenote);
        clearFieldsButton = findViewById(R.id.clearFieldsButton);

        Toolbar toolbar = findViewById(R.id.toolbarofcreatenote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mCreateTitleOfNote.getText().toString().trim();
                String content = mCreateContentOfNote.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Both fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    mProgressBarOfCreateNote.setVisibility(View.VISIBLE);
                    LocalNoteStore.get(createnote.this).add(title, content);
                    Toast.makeText(getApplicationContext(), "Note Created Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(createnote.this, notesactivity.class));
                    finish();
                }
            }
        });

        clearFieldsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCreateTitleOfNote.setText("");
                mCreateContentOfNote.setText("");
                Toast.makeText(getApplicationContext(), "Fields cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
