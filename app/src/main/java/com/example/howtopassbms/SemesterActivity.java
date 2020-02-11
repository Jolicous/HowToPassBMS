package com.example.howtopassbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.howtopassbms.CreateActivities.CreateSemesterActivity;
import com.example.howtopassbms.UpdateActivities.UpdateSemesterActivity;
import com.example.howtopassbms.helper.AppDatabase;
import com.example.howtopassbms.model.Grade;
import com.example.howtopassbms.model.Semester;
import com.example.howtopassbms.model.Subject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.List;

public class SemesterActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        calculateAverage();
        super.onStart();
        setContentView(R.layout.activity_semester);
        addSemesterToClickableList();
        createNewSemester();
    }

    //Alle Semester werden aus der DB geholt und bereitgestellt
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

        AdapterView.OnItemLongClickListener ListLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UpdateSemesterActivity.class);
                Semester selected = (Semester) parent.getItemAtPosition(position);

                intent.putExtra("semesterId", selected.getId());
                intent.putExtra("semesterName", selected.getName());
                startActivity(intent);
                return true;
            }
        };
        semesters.setOnItemLongClickListener(ListLongClickListener);
        semesters.setOnItemClickListener(ListClickedHandler);
    }

    //Die Activity fürs erstellen eines neuen Semesters wird aufgerufen
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

    //Der Semesterdurchschnitt wird für jedes Semester ausgerechnet
    private void calculateAverage() {
        AppDatabase db = AppDatabase.getAppDatabase(this);
        List<Semester> semesters = db.semesterDao().getAll();
        if (semesters.size() == 0) {
            return;
        }
        for (Semester semester : db.semesterDao().getAll()) {
            List<Subject> subjects = db.subjectDao().getAllBySemesterId(semester.getId());
            double sumSubjects = 0;
            if (subjects.size() == 0) {
                continue;
            }
            for (Subject subject : subjects) {
                List<Grade> grades = db.gradeDao().getAllBySubjectId(subject.getId());
                double sumGrades = 0;
                if (grades.size() == 0) {
                    continue;
                }
                for (Grade grade : grades) {
                    sumGrades += grade.getGrade();
                }
                double test = sumGrades / grades.size();
                //Das Resultat wird zu 0.00 umformatiert
                new DecimalFormat("#.##").format(test);
                subject.setNote(test);
                sumSubjects += subject.getNote();
            }
            double average = sumSubjects / subjects.size();
            semester.setNote(average);
            db.semesterDao().updateNote(semester.getNote(), semester.getId());
        }
    }
}
