package com.example.howtopassbms;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.howtopassbms.dal.SubjectDao;
import com.example.howtopassbms.helper.AppDatabase;
import com.example.howtopassbms.model.Grade;
import com.example.howtopassbms.model.Semester;
import com.example.howtopassbms.model.Subject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.List;

public class SubjectActivity extends AppCompatActivity {

    private int semesterId;
    private String semesterName;

    @Override
    protected void onStart() {
        Intent intent = getIntent();
        semesterId = intent.getIntExtra("semesterId", 0);
        semesterName = intent.getStringExtra("semesterName");
        calculateAverage(semesterId);
        super.onStart();
        setContentView(R.layout.activity_subject);
        setTitle(semesterName);
        addSubjectsToClickableList();
        createNewSubject(semesterId, semesterName);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void addSubjectsToClickableList() {
        Intent intent = getIntent();
        ListView subjects = findViewById(R.id.subjectlist);
        ArrayAdapter<Subject> subjectAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        AppDatabase db = AppDatabase.getAppDatabase(this);
        subjectAdapter.addAll(db.subjectDao().getAllBySemesterId(intent.getIntExtra("semesterId", 0)));
        subjects.setAdapter(subjectAdapter);
        AdapterView.OnItemClickListener ListClickedListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), GradeActivity.class);
                Subject selected = (Subject) parent.getItemAtPosition(position);

                intent.putExtra("subjectId", selected.getId());
                intent.putExtra("subjectName", selected.getName());
                intent.putExtra("semesterId", semesterId);
                intent.putExtra("semesterName", semesterName);
                startActivity(intent);
            }
        };

        subjects.setOnItemClickListener(ListClickedListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNewSubject(int semesterId, String semesterName) {
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateSubjectActivity.class);
                intent.putExtra("semesterId", semesterId);
                intent.putExtra("semesterName", semesterName);
                startActivity(intent);
            }
        });
    }

    private void calculateAverage(int semesterId){
        AppDatabase db = AppDatabase.getAppDatabase(this);
        List<Subject> subjects = db.subjectDao().getAllBySemesterId(semesterId);
        if(subjects.size() == 0){
            return;
        }
        for (Subject subject: subjects) {
            List<Grade> grades = db.gradeDao().getAllBySubjectId(subject.getId());
            double sumGrades = 0;
            if(grades.size() == 0){
                return;
            }
            for (Grade grade: grades) {
                sumGrades += grade.getGrade();
            }
            double average = sumGrades / grades.size();
            new DecimalFormat("#.00").format(average);
            subject.setNote(average);
            db.subjectDao().updateNote(subject.getNote(), subject.getId());
        }
    }

}
