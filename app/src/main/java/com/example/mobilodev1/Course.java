package com.example.mobilodev1;

import android.os.Parcel;
import android.os.Parcelable;

public class Course implements  Comparable<Course>, Parcelable {
    private String name;
    private String note;

    protected Course(Parcel in) {
        name = in.readString();
        note = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(note);
    }

    @Override
    public int compareTo(Course o) {
        return this.name.compareTo(o.name);
    }

    public Course(String name, String note){
        this.name = name;
        this.note = note;
    }
    public String getNote() {
        return note;
    }

    public String getName() {
        return name;
    }


}
