package com.example.howtopassbms.dal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.howtopassbms.model.Subject;

import java.util.List;

@Dao
public interface SubjectDao {

    @Query("SELECT * FROM subject")
    List<Subject> getAll();

    @Query("SELECT * FROM subject WHERE semesterid LIKE :semesterId")
    List<Subject> getAllBySemesterId(int semesterId);

    @Insert
    void insertAll(Subject... subjects);

    @Delete
    void delete(Subject subject);
}