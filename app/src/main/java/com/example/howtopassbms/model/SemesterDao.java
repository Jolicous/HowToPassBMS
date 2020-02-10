package com.example.howtopassbms.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SemesterDao {

    @Query("SELECT * FROM semester")
    List<Semester> getAll();

    @Insert
    void insertAll(Semester... semesters);

    @Delete
    void delete(Semester semester);
}