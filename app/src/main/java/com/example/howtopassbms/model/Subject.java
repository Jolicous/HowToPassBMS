package com.example.howtopassbms.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.text.DecimalFormat;

@Entity(tableName = "subject")
public class Subject {

    public Subject() {
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "note")
    private double note;

    @ColumnInfo(name = "semesterid")
    private int semesterId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    @Override
    public String toString() {
        if(note != 0){
            //Das Resultat wird zu 0.00 umformatiert
            return name + "     " + new DecimalFormat("0.0#").format(note);
        }else{
            return name + "     kein Note erfasst";
        }
    }
}
