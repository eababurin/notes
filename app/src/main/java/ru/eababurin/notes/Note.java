package ru.eababurin.notes;

import java.util.ArrayList;
import java.util.Date;

public class Note {

    private final static ArrayList<Note> notes = new ArrayList<>();

    private int id;
    private String dateOfCreation;
    private String header;
    private String content;

    public Note(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
        notes.add(this);
    }

    public Note(int id, String dateOfCreation, String header, String content) {
        this.id = id;
        this.dateOfCreation = dateOfCreation;
        this.header = header;
        this.content = content;

        notes.add(this);
    }

    protected static Note getNoteById(int id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
