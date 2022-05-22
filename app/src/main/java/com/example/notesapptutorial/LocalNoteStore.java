package com.example.notesapptutorial;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Local note storage for demo mode. Replaces Firestore so the app works offline.
 */
public class LocalNoteStore {

    private static final String PREFS = "demo_notes";
    private static final String KEY_NOTES = "notes_json";

    private static LocalNoteStore instance;

    private final SharedPreferences prefs;
    private final List<firebasemodel> notes = new ArrayList<>();

    public static synchronized LocalNoteStore get(Context context) {
        if (instance == null) {
            instance = new LocalNoteStore(context.getApplicationContext());
        }
        return instance;
    }

    private LocalNoteStore(Context context) {
        prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        load();
        if (notes.isEmpty()) {
            seed();
        }
    }

    public List<firebasemodel> getAll() {
        List<firebasemodel> copy = new ArrayList<>(notes);
        Collections.sort(copy, new Comparator<firebasemodel>() {
            @Override
            public int compare(firebasemodel a, firebasemodel b) {
                String ta = a.getTitle() == null ? "" : a.getTitle();
                String tb = b.getTitle() == null ? "" : b.getTitle();
                return ta.compareToIgnoreCase(tb);
            }
        });
        return copy;
    }

    public firebasemodel add(String title, String content) {
        firebasemodel note = new firebasemodel(UUID.randomUUID().toString(), title, content);
        notes.add(note);
        persist();
        return note;
    }

    public boolean update(String id, String title, String content) {
        for (firebasemodel note : notes) {
            if (id.equals(note.getId())) {
                note.setTitle(title);
                note.setContent(content);
                persist();
                return true;
            }
        }
        return false;
    }

    public boolean delete(String id) {
        for (int i = 0; i < notes.size(); i++) {
            if (id.equals(notes.get(i).getId())) {
                notes.remove(i);
                persist();
                return true;
            }
        }
        return false;
    }

    private void seed() {
        add("Welcome to JournalEase", "This is a demo note. Create, edit, export, or delete notes without signing in to Firebase.");
        add("Demo tip", "Log in with any email and password. Sign up and forgot-password screens also work locally.");
    }

    private void persist() {
        JSONArray array = new JSONArray();
        try {
            for (firebasemodel note : notes) {
                JSONObject object = new JSONObject();
                object.put("id", note.getId());
                object.put("title", note.getTitle());
                object.put("content", note.getContent());
                array.put(object);
            }
            prefs.edit().putString(KEY_NOTES, array.toString()).apply();
        } catch (JSONException ignored) {
        }
    }

    private void load() {
        notes.clear();
        String json = prefs.getString(KEY_NOTES, "");
        if (json == null || json.isEmpty()) {
            return;
        }
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                notes.add(new firebasemodel(
                        object.optString("id"),
                        object.optString("title"),
                        object.optString("content")
                ));
            }
        } catch (JSONException ignored) {
        }
    }
}
