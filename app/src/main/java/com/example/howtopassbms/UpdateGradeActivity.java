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
        Intent intent2 = getIntent();
        int subjectId = intent2.getIntExtra("subjectId", 0);
        int gradeId = intent2.getIntExtra("gradeId", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_grade);
        setTitle("Note bearbeiten");
        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grade grade = new Grade();
                grade.setId(gradeId);
                grade.setSubjectId(subjectId);
                try {
                    grade.setGrade(Double.parseDouble(editText.getText().toString()));
                } catch (Exception e){
                    editText.setError("Die Note muss zwischen 1 und 6 sein!");
                }
                if (grade.getGrade() >= 1 && grade.getGrade() <= 6) {
                    updateGrade(grade);
                    Intent intent = new Intent(getApplicationContext(), GradeActivity.class);
                    intent.putExtra("subjectId", subjectId);
                    intent.putExtra("subjectName", intent2.getStringExtra("subjectName"));
                    intent.putExtra("semesterId", intent2.getIntExtra("semesterId", 0));
                    intent.putExtra("semesterName", intent2.getStringExtra("semesterName"));
                    startActivity(intent);
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
