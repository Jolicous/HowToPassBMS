package com.example.howtopassbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.howtopassbms.helper.AppDatabase;
import com.example.howtopassbms.model.Semester;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SemesterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);
        addSemesterToClickableList();
        createNewSemester();
    }

    public void addSemesterToClickableList() {
        ListView semesters = findViewById(R.id.semesterlist);
        ArrayAdapter<Semester> semesterAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        AppDatabase db = AppDatabase.getAppDatabase(this);
        semesterAdapter.addAll(db.semesterDao().getAll());
        semesters.setAdapter(semesterAdapter);

        AdapterView.OnItemClickListener ListClickedHandler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SubjectActivity.class);
                Semester selected = (Semester) parent.getItemAtPosition(position);

                intent.putExtra("semesterId", selected.getId());
                intent.putExtra("semesterName", selected.getName());
                startActivity(intent);
            }
        };

        semesters.setOnItemClickListener(ListClickedHandler);
    }

    private void createNewSemester() {
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateSemesterActivity.class);
                startActivity(intent);
            }
        });
    }
}
