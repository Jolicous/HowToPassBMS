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

import com.example.howtopassbms.helper.AppDatabase;
import com.example.howtopassbms.model.Grade;
import com.example.howtopassbms.model.Semester;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        addGradeToClickableList();
        createNewGrade();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void addGradeToClickableList(){
        Intent intent = getIntent();
        ListView grades = findViewById(R.id.gradelist);
        ArrayAdapter<Grade> gradeAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        AppDatabase db = AppDatabase.getAppDatabase(this);
        gradeAdapter.addAll(db.gradeDao().getAllBySubjectId(intent.getIntExtra("subjectId", 0)));
        grades.setAdapter(gradeAdapter);
    }

    private void createNewGrade() {
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateGradeActivity.class);
                startActivity(intent);
            }
        });
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
}
