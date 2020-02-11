package com.example.howtopassbms.helper;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.howtopassbms.dal.GradeDao;
import com.example.howtopassbms.dal.SemesterDao;
import com.example.howtopassbms.dal.SubjectDao;
import com.example.howtopassbms.model.Grade;
import com.example.howtopassbms.model.Semester;
import com.example.howtopassbms.model.Subject;

@Database(entities = {Semester.class, Subject.class, Grade.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract SemesterDao semesterDao();

    public abstract SubjectDao subjectDao();

    public abstract GradeDao gradeDao();


    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "howtopassbms-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
