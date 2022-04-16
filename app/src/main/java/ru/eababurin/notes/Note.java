package ru.eababurin.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Note implements Parcelable {

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
    private final static ArrayList<Note> notes = new ArrayList<>();
    private int id;
    private String dateOfCreation;
    private String header;
    private String content;

    public Note(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
        notes.add(this);
    }

    private Note() {
    }

    public Note(int id, String dateOfCreation, String header, String content) {
        this.id = id;
        this.dateOfCreation = dateOfCreation;
        this.header = header;
        this.content = content;

        notes.add(this);
    }

    protected Note(Parcel in) {
        id = in.readInt();
        dateOfCreation = in.readString();
        header = in.readString();
        content = in.readString();
    }

    protected static Note getNoteById(int id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }
        return new Note();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateOfCreation() {
        return ((dateOfCreation != null) && (!dateOfCreation.equals(""))) ? dateOfCreation : "неизвестно";
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(dateOfCreation);
        parcel.writeString(header);
        parcel.writeString(content);
    }
}
