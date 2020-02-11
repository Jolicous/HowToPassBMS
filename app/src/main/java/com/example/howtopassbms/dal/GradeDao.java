package com.example.howtopassbms.dal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.howtopassbms.model.Grade;

import java.util.List;

@Dao
public interface GradeDao {
    @Query("SELECT * FROM grade")
    List<Grade> getAll();

    @Query("SELECT * FROM grade WHERE subjectid LIKE :subjectId")
    List<Grade> getAllBySubjectId(int subjectId);

    @Query("SELECT grade FROM grade WHERE id like :id")
    List<Grade> getAllById(int id);

    @Insert
    void insertAll(Grade... grades);

    @Update
    void updateGrades(Grade... grades);

    @Delete
    void delete(Grade semester);
}
