package ru.eababurin.notes.engine;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class Memo implements Parcelable {

    private int id;
    private String header, content;
    private boolean isFavourite;
    private Date dateOfCreation, dateOfChange; // TODO : добавить время

    public static ArrayList<Memo> memos = new ArrayList<>();

    public Memo(Date dateOfCreation) { // конструктор для новой заметки заметки
        this.id = Memo.memos.size() + 1;
        this.isFavourite = false;
        this.dateOfCreation = dateOfCreation;
        memos.add(this);
    }

    public Memo(String header, String content, Date dateOfCreation) {
        this.id = Memo.memos.size() + 1;
        this.isFavourite = false;
        this.header = header;
        this.content = content;
        this.dateOfCreation = dateOfCreation;

        memos.add(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(Date dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite() {
        this.isFavourite = true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(header);
        parcel.writeString(content);
        parcel.writeByte((byte) (isFavourite ? 1 : 0));
    }

    public static final Creator<Memo> CREATOR = new Creator<Memo>() {
        @Override
        public Memo createFromParcel(Parcel in) {
            return new Memo(in);
        }

        @Override
        public Memo[] newArray(int size) {
            return new Memo[size];
        }
    };

    protected Memo(Parcel in) {
        id = in.readInt();
        header = in.readString();
        content = in.readString();
        isFavourite = in.readByte() != 0;
    }
}
