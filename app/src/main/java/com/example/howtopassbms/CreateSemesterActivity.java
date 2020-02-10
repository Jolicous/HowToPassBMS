package com.example.howtopassbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.howtopassbms.helper.AppDatabase;
import com.example.howtopassbms.model.Semester;

public class CreateSemesterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_semester);
        setTitle("Semester hinzufügen");
        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Semester semester = new Semester();
                semester.setName(editText.getText().toString());
                addSemester(semester);
                Intent intent = new Intent(getApplicationContext(), SemesterActivity.class);
                startActivity(intent);
            }
        });
    }

    private Semester addSemester(Semester semester) {
        AppDatabase db = AppDatabase.getAppDatabase(this);
        db.semesterDao().insertAll(semester);
        return semester;
    }

}
