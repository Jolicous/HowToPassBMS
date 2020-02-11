package com.example.howtopassbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.howtopassbms.helper.AppDatabase;
import com.example.howtopassbms.model.Semester;
import com.example.howtopassbms.model.Subject;

public class UpdateSubjectActivity extends AppCompatActivity {

    int subjectId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        int semesterId = intent.getIntExtra("semesterId", 0);
        subjectId = intent.getIntExtra("subjectId", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_subject);
        setTitle("Fach bearbeiten");
        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subject subject = new Subject();
                subject.setName(editText.getText().toString());
                subject.setSemesterId(semesterId);
                subject.setId(subjectId);
                updateSubject(subject);
                Intent SubjectActivity = new Intent(getApplicationContext(), SubjectActivity.class);
                SubjectActivity.putExtra("semesterId", semesterId);
                SubjectActivity.putExtra("semesterName", intent.getStringExtra("semesterName"));
                startActivity(SubjectActivity);
            }
        });
    }

    private Subject updateSubject(Subject subject) {
        AppDatabase db = AppDatabase.getAppDatabase(this);
        db.subjectDao().updateSubject(subject);
        return subject;
    }
}
