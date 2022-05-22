package com.example.notesapptutorial;

import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class editnoteactivity extends AppCompatActivity {

    private Intent data;
    private EditText mEditTitleOfNote, mEditContentOfNote;
    private FloatingActionButton mSaveEditNote;
    private Button clearFieldsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnoteactivity);

        mEditTitleOfNote = findViewById(R.id.edittitleofnote);
        mEditContentOfNote = findViewById(R.id.editcontentofnote);
        mSaveEditNote = findViewById(R.id.saveeditnote);
        clearFieldsButton = findViewById(R.id.clearFieldsButton);

        data = getIntent();

        Toolbar toolbar = findViewById(R.id.toolbarofeditnote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        clearFieldsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTitleOfNote.setText("");
                mEditContentOfNote.setText("");
                Toast.makeText(getApplicationContext(), "Fields cleared", Toast.LENGTH_SHORT).show();
            }
        });

        mSaveEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = mEditTitleOfNote.getText().toString();
                String newContent = mEditContentOfNote.getText().toString();

                if (newTitle.isEmpty() || newContent.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Title or content is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                String noteId = data.getStringExtra("noteId");
                boolean updated = LocalNoteStore.get(editnoteactivity.this).update(noteId, newTitle, newContent);
                if (updated) {
                    Toast.makeText(getApplicationContext(), "Note updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(editnoteactivity.this, notesactivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to update note", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String noteTitle = data.getStringExtra("title");
        String noteContent = data.getStringExtra("content");

        mEditTitleOfNote.setText(noteTitle);
        mEditContentOfNote.setText(noteContent);
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
