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

public class CreateSubjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent2 = getIntent();
        int semesterId = intent2.getIntExtra("semesterId", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_subject);
        setTitle("Fach hinzufügen");
        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subject subject = new Subject();
                subject.setName(editText.getText().toString());
                subject.setSemesterId(semesterId);
                addSubject(subject);
                Intent intent = new Intent(getApplicationContext(), SubjectActivity.class);
                intent.putExtra("semesterId", semesterId);
                intent.putExtra("semesterName", intent2.getStringExtra("semesterName"));
                startActivity(intent);
            }
        });
    }

    private Subject addSubject(Subject subject) {
        AppDatabase db = AppDatabase.getAppDatabase(this);
        db.subjectDao().insertAll(subject);
        return subject;
    }
}
