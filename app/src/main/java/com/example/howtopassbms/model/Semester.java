package com.example.howtopassbms.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "semester")
public class Semester {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "note")
    private double note;

    @Ignore
    private List<Subject> subjectList  = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Subject> getSubjectList(){
        return subjectList;
    }

    public void addSubject(Subject subject){
        subjectList.add(subject);
    }

    public void removeSubject(Subject subject){
        subjectList.remove(subject);
    }

    @Override
    public String toString() {
        return name + " " + note;
    }
}