package com.example.howtopassbms.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "grade")
public class Grade {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "grade")
    private double grade;

    @ColumnInfo(name = "subjectid")
    private int subjectId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return String.valueOf(grade);
    }
}
