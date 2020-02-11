package com.example.howtopassbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.howtopassbms.helper.AppDatabase;
import com.example.howtopassbms.model.Grade;

public class UpdateGradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        int subjectId = intent.getIntExtra("subjectId", 0);
        int gradeId = intent.getIntExtra("gradeId", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_grade);
        setTitle("Note bearbeiten");
        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.editText);
        editText.setText(String.valueOf(intent.getDoubleExtra("currentGrade", 0)));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grade grade = new Grade();
                grade.setId(gradeId);
                try {
                    grade.setGrade(Double.parseDouble(editText.getText().toString()));
                } catch (Exception e){
                    editText.setError("Die Note muss zwischen 1 und 6 sein!");
                }
                grade.setSubjectId(subjectId);
                if (grade.getGrade() >= 1 && grade.getGrade() <= 6) {
                    updateGrade(grade);
                    Intent GradeIntent = new Intent(getApplicationContext(), GradeActivity.class);
                    GradeIntent.putExtra("subjectId", subjectId);
                    GradeIntent.putExtra("subjectName", intent.getStringExtra("subjectName"));
                    GradeIntent.putExtra("semesterId", intent.getIntExtra("semesterId", 0));
                    GradeIntent.putExtra("semesterName", intent.getStringExtra("semesterName"));
                    startActivity(GradeIntent);
                } else {
                    editText.setError("Die Note muss zwischen 1 und 6 sein!");
                }
            }
        });

    }

    private Grade updateGrade(Grade grade) {
        AppDatabase db = AppDatabase.getAppDatabase(this);
        db.gradeDao().updateGrade(grade);
        return grade;
    }

}
