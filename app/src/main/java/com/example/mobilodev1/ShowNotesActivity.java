package com.example.mobilodev1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShowNotesActivity extends AppCompatActivity {
    private  List<Course> courses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);
        setTitle("Show Notes");

        if(savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);
        }else{
            courses = generateSimpleList(40);
        }
        MyAdapter adapter = new MyAdapter(courses);
        RecyclerView recyclerView = findViewById(R.id.simple_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("courses", new ArrayList<>(courses));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        courses = savedInstanceState.getParcelableArrayList("courses");
    }

    private List<Course> generateSimpleList(int number) {
        List<Course> courselList = new ArrayList<>();
        Random rand = new Random();
        String notes[] = {"AA", "BA", "BB", "CB", "CC", "DC", "DD", "FD", "FF", "F0"};
        for (int i = 0; i < number; i++) {
            String cname = "";
            cname += rand.nextInt(4)+1;
            cname += rand.nextInt(900)+100;
            courselList.add(new Course("BLM"+cname, notes[rand.nextInt(10)]));
        }
        Collections.sort(courselList);
        return courselList;
    }
}
