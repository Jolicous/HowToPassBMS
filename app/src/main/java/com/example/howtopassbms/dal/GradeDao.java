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

    @Insert
    void insertAll(Grade... grades);

    @Update
    void updateGrade(Grade grade);

    @Query("SELECT * FROM grade WHERE id LIKE :id")
    Grade getGradeById(int id);

    @Delete
    void delete(Grade grade);
}
