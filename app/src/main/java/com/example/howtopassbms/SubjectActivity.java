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
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_subject);
        Intent intent = getIntent();
        semesterId = intent.getIntExtra("semesterId", 0);
        String semesterName = intent.getStringExtra("semesterName");
        setTitle(semesterName);
        calculateAverage(semesterId);
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
                intent.putExtra("semesterId", semesterId);
                intent.putExtra("subjectId", selected.getId());
                intent.putExtra("subjectName", selected.getName());
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
        for (Subject subject: db.subjectDao().getAllBySemesterId(semesterId)) {
            List<Grade> grades = db.gradeDao().getAllBySubjectId(subject.getId());
            double average = 0;
            for (Grade grade: grades) {
                average += grade.getGrade();
            }
            average = average / grades.size();
            new DecimalFormat("#.##").format(average);
            subject.setNote(average);
        }
    }

}
