package com.example.howtopassbms.UpdateActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.howtopassbms.R;
import com.example.howtopassbms.SubjectActivity;
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
        editText.setText(intent.getStringExtra("subjectName"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subject subject = new Subject();
                subject.setName(editText.getText().toString());
                subject.setSemesterId(semesterId);
                subject.setId(subjectId);
                updateSubject(subject);
                Intent SubjectActivity = new Intent(getApplicationContext(), com.example.howtopassbms.SubjectActivity.class);
                SubjectActivity.putExtra("semesterId", semesterId);
                SubjectActivity.putExtra("semesterName", intent.getStringExtra("semesterName"));
                startActivity(SubjectActivity);
            }
        });

        AppDatabase db = AppDatabase.getAppDatabase(this);
        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.subjectDao().delete(db.subjectDao().getSubjectById(subjectId));
                Intent SubjectActivity = new Intent(getApplicationContext(), com.example.howtopassbms.SubjectActivity.class);
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
