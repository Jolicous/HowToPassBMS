package com.example.howtopassbms.dal;

import com.example.howtopassbms.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectDao {
    public static List<Subject> getAll() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject(1, "Deutsch", 4.0));
        subjects.add(new Subject(2, "Englisch", 4.0));
        subjects.add(new Subject(3, "Mathematik", 5.0));
        subjects.add(new Subject(4, "Französisch", 5.0));

        return subjects;
    }
}
