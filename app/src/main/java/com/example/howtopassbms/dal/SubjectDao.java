package com.example.howtopassbms.dal;

import com.example.howtopassbms.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectDao {
    public static List<Subject> getAll() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("Deutsch", 4.0));
        subjects.add(new Subject("Englisch", 4.0));
        subjects.add(new Subject("Mathematik", 5.0));
        subjects.add(new Subject("Französisch", 5.0));

        return subjects;
    }
}
