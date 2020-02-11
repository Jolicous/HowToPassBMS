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
import android.widget.TextView;

import com.example.howtopassbms.helper.AppDatabase;
import com.example.howtopassbms.model.Grade;
import com.example.howtopassbms.model.Semester;
import com.example.howtopassbms.model.Subject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class GradeActivity extends AppCompatActivity {

    private int semesterId;
    private String semesterName;
    private int subjectId;
    private String subjectName;

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_grade);
        Intent intent = getIntent();
        semesterId = intent.getIntExtra("semesterId", 0);
        semesterName = intent.getStringExtra("semesterName");
        subjectId = intent.getIntExtra("subjectId", 0);
        subjectName = intent.getStringExtra("subjectName");
        setTitle(subjectName);
        addGradeToClickableList();
        createNewGrade(subjectId, subjectName);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        calculateGradeNeeded();
    }

    public void addGradeToClickableList(){
        Intent intent = getIntent();
        ListView grades = findViewById(R.id.gradelist);
        ArrayAdapter<Grade> gradeAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        AppDatabase db = AppDatabase.getAppDatabase(this);
        gradeAdapter.addAll(db.gradeDao().getAllBySubjectId(intent.getIntExtra("subjectId", 0)));
        grades.setAdapter(gradeAdapter);

        AdapterView.OnItemClickListener ListClickedHandler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UpdateGradeActivity.class);
                Grade selected = (Grade) parent.getItemAtPosition(position);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("gradeId", selected.getId());
                intent.putExtra("subjectName", subjectName);
                intent.putExtra("semesterId", semesterId);
                intent.putExtra("semesterName", semesterName);

                startActivity(intent);
            }
        };

        grades.setOnItemClickListener(ListClickedHandler);
    }

    public void calculateGradeNeeded(){
        Intent intent = getIntent();
        AppDatabase db = AppDatabase.getAppDatabase(this);
        List<Grade> gradeList = db.gradeDao().getAllBySubjectId(intent.getIntExtra("subjectId", 0));
        double value = 0;
        for (Grade grade: gradeList){
            value += grade.getGrade();
        }
        double result = 4 * (gradeList.size() + 1) - value;

        TextView gradeNeeded = findViewById(R.id.gradeNeeded);
        gradeNeeded.setText("Du benötigst eine " + result + " für eine 4.0 im Durchschnitt");
    }

    private void createNewGrade(int subjectId, String subjectName) {
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateGradeActivity.class);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("subjectName", subjectName);
                intent.putExtra("semesterId", semesterId);
                intent.putExtra("semesterName", semesterName);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), SubjectActivity.class);
                intent.putExtra("semesterId", semesterId);
                intent.putExtra("semesterName", semesterName);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
