package com.example.howtopassbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.howtopassbms.helper.AppDatabase;
import com.example.howtopassbms.model.Grade;

public class CreateGradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent2 = getIntent();
        int subjectId = intent2.getIntExtra("subjectId", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_grade);
        setTitle("Note hinzufügen");
        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grade grade = new Grade();
                grade.setGrade(Double.parseDouble(editText.getText().toString()));
                grade.setSubjectId(subjectId);
                addGrade(grade);
                Intent intent = new Intent(getApplicationContext(), GradeActivity.class);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("subjectName", intent2.getStringExtra("subjectName"));
                startActivity(intent);
            }
        });
    }

    private Grade addGrade(Grade grade) {
        AppDatabase db = AppDatabase.getAppDatabase(this);
        db.gradeDao().insertAll(grade);
        return grade;
    }
}
