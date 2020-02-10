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

import com.example.howtopassbms.dal.SubjectDao;
import com.example.howtopassbms.helper.AppDatabase;
import com.example.howtopassbms.model.Subject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SubjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        Intent intent = getIntent();
        int semesterId = intent.getIntExtra("semesterId", 0);
        String semesterName = intent.getStringExtra("semesterName");
        setTitle(semesterName);
        addSubjectsToClickableList();
        createNewSubject(semesterId, semesterName);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void addSubjectsToClickableList() {
        Intent intent = getIntent();
        ListView subjects = findViewById(R.id.subjectlist);
        ArrayAdapter<Subject> subjectAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        AppDatabase db = AppDatabase.getAppDatabase(this);
        subjectAdapter.addAll(db.subjectDao().getAllBySemesterId(intent.getIntExtra("semesterId", 0)));
        subjects.setAdapter(subjectAdapter);
        AdapterView.OnItemClickListener ListClickedListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), GradeActivity.class);
                Subject selected = (Subject) parent.getItemAtPosition(position);

                intent.putExtra("subjectId", selected.getId());
                intent.putExtra("subjectName", selected.getName());
                startActivity(intent);
            }
        };

        subjects.setOnItemClickListener(ListClickedListener);
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

    private void createNewSubject(int semesterId, String semesterName) {
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateSubjectActivity.class);
                intent.putExtra("semesterId", semesterId);
                intent.putExtra("semesterName", semesterName);
                startActivity(intent);
            }
        });
    }

}
