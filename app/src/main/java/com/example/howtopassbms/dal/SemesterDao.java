package com.example.howtopassbms.dal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.howtopassbms.model.Semester;

import java.util.List;

@Dao
public interface SemesterDao {

    @Query("SELECT * FROM semester")
    List<Semester> getAll();

    @Insert
    void insertAll(Semester... semesters);

    @Query("UPDATE semester SET note = :note WHERE id = :id")
    void updateNote(double note, int id);

    @Update
    void updateSemester(Semester semeser);

    @Query("SELECT * FROM semester WHERE id LIKE :id")
    Semester getSemesterById(int id);

    @Delete
    void delete(Semester semester);
}