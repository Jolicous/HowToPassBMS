package com.example.howtopassbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.howtopassbms.helper.AppDatabase;
import com.example.howtopassbms.model.Semester;

public class UpdateSemesterActivity extends AppCompatActivity {


    int semesterId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        semesterId = intent.getIntExtra("semesterId", 0);
        setContentView(R.layout.activity_update_semester);
        setTitle("Semester bearbeiten");
        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Semester semester = new Semester();
                semester.setId(semesterId);
                semester.setName(editText.getText().toString());
                updateSemester(semester);
                Intent intent = new Intent(getApplicationContext(), SemesterActivity.class);
                startActivity(intent);
            }
        });
    }

    private Semester updateSemester(Semester semester) {
        AppDatabase db = AppDatabase.getAppDatabase(this);
        db.semesterDao().updateSemester(semester);
        return semester;
    }

}
