package com.example.notesapptutorial;

import static java.time.LocalDateTime.now;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class notesactivity extends AppCompatActivity {

    FloatingActionButton mcreatenotesfab;
    RecyclerView mrecyclerview;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    NotesAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notesactivity);

        mcreatenotesfab = findViewById(R.id.createnotefab);

        getSupportActionBar().setTitle("");

        mcreatenotesfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(notesactivity.this, createnote.class));
            }
        });

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        noteAdapter = new NotesAdapter();

        mrecyclerview = findViewById(R.id.recyclerview);
        mrecyclerview.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        mrecyclerview.setAdapter(noteAdapter);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView notetitle;
        private TextView notecontent;
        LinearLayout mnote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            notetitle = itemView.findViewById(R.id.notetitle);
            notecontent = itemView.findViewById(R.id.notecontent);
            mnote = itemView.findViewById(R.id.note);

            Animation translate_anim = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.translate_anim);
            mnote.setAnimation(translate_anim);
        }
    }

    private class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {
        private List<firebasemodel> notes = new ArrayList<>();

        void setNotes(List<firebasemodel> newNotes) {
            notes = newNotes;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout, parent, false);
            return new NoteViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {
            firebasemodel note = notes.get(i);
            ImageView popupbutton = noteViewHolder.itemView.findViewById(R.id.menupopbutton);

            int colourcode = getRandomColor();
            noteViewHolder.mnote.setBackgroundColor(noteViewHolder.itemView.getResources().getColor(colourcode, null));

            noteViewHolder.notetitle.setText(note.getTitle());
            noteViewHolder.notecontent.setText(note.getContent());

            String docId = note.getId();

            noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), notedetails.class);
                    intent.putExtra("title", note.getTitle());
                    intent.putExtra("content", note.getContent());
                    intent.putExtra("noteId", docId);
                    v.getContext().startActivity(intent);
                }
            });

            popupbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.setGravity(Gravity.END);
                    popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Intent intent = new Intent(v.getContext(), editnoteactivity.class);
                            intent.putExtra("title", note.getTitle());
                            intent.putExtra("content", note.getContent());
                            intent.putExtra("noteId", docId);
                            v.getContext().startActivity(intent);
                            return false;
                        }
                    });

                    popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(notesactivity.this);
                            builder.setTitle("Delete");
                            builder.setMessage("Are you sure you want to delete?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    boolean deleted = LocalNoteStore.get(notesactivity.this).delete(docId);
                                    if (deleted) {
                                        Toast.makeText(v.getContext(), "This note is deleted", Toast.LENGTH_SHORT).show();
                                        noteAdapter.setNotes(LocalNoteStore.get(notesactivity.this).getAll());
                                    } else {
                                        Toast.makeText(v.getContext(), "Failed To Delete", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            builder.create().show();
                            return false;
                        }
                    });
                    popupMenu.getMenu().add("Export PDF").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            LocalDateTime datetime1 = now();
                            DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
                            String formatDateTime = datetime1.format(format);

                            String stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/Note" + formatDateTime + ".pdf";
                            File file = new File(stringFilePath);

                            PdfDocument pdfDocument = new PdfDocument();
                            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
                            PdfDocument.Page page = pdfDocument.startPage(pageInfo);

                            Paint paint = new Paint();
                            String stringPDF = note.getTitle() + "\n\n" + note.getContent();

                            int x = 10, y = 25;

                            for (String line : stringPDF.split("\n")) {
                                page.getCanvas().drawText(line, x, y, paint);
                                y += paint.descent() - paint.ascent();
                            }
                            pdfDocument.finishPage(page);
                            try {
                                pdfDocument.writeTo(new FileOutputStream(file));
                                Toast.makeText(v.getContext(), "file pdf created", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(v.getContext(), "file pdf didn't create", Toast.LENGTH_SHORT).show();
                            }
                            pdfDocument.close();

                            return false;
                        }
                    });
                    popupMenu.getMenu().add("Export docx").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            LocalDateTime datetime1 = now();
                            DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
                            String formatDateTime = datetime1.format(format);

                            String stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/Note" + formatDateTime + ".docx";
                            File file = new File(stringFilePath);

                            try {
                                file.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(v.getContext(), "file docx didn't create", Toast.LENGTH_SHORT).show();
                            }

                            try {
                                XWPFDocument xwpfDocument = new XWPFDocument();
                                XWPFParagraph xwpfParagraph = xwpfDocument.createParagraph();
                                XWPFRun xwpfRuntt = xwpfParagraph.createRun();
                                XWPFRun xwpfRunct = xwpfParagraph.createRun();

                                xwpfRuntt.setText(note.getTitle() + "\n");
                                xwpfRuntt.addBreak();
                                xwpfRuntt.setFontSize(24);
                                xwpfRuntt.setBold(true);

                                String data = note.getContent();
                                if (data.contains("\n")) {
                                    String[] lines = data.split("\n");
                                    xwpfRunct.setText(lines[0], 0);
                                    for (int i = 1; i < lines.length; i++) {
                                        xwpfRunct.addBreak();
                                        xwpfRunct.setText(lines[i]);
                                    }
                                } else {
                                    xwpfRunct.setText(data, 0);
                                }
                                xwpfRunct.setFontSize(16);

                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                xwpfDocument.write(fileOutputStream);

                                xwpfDocument.close();
                                Toast.makeText(v.getContext(), "file docx created", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(v.getContext(), "file docx didn't create", Toast.LENGTH_SHORT).show();
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                DemoSession.logout(this);
                finish();
                startActivity(new Intent(notesactivity.this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (noteAdapter != null) {
            noteAdapter.setNotes(LocalNoteStore.get(this).getAll());
        }
    }

    private int getRandomColor() {
        List<Integer> colorcode = new ArrayList<>();
        colorcode.add(R.color.gray);
        colorcode.add(R.color.pink);
        colorcode.add(R.color.lightgreen);
        colorcode.add(R.color.skyblue);
        colorcode.add(R.color.color1);
        colorcode.add(R.color.color2);
        colorcode.add(R.color.color3);
        colorcode.add(R.color.color4);
        colorcode.add(R.color.color5);
        colorcode.add(R.color.green);

        Random random = new Random();
        int number = random.nextInt(colorcode.size());
        return colorcode.get(number);
    }
}
