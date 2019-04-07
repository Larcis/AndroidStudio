package com.example.mobilodev1;

import android.content.Intent;
import android.icu.util.GregorianCalendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;

public class ShowCourseInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_course_info);

        Intent i  = getIntent();
        String ccode = i.getStringExtra("data");
        setTitle(ccode+" Course Info");
        Random rand  = new Random();
        String[] cl = new String[]{"Freshman", "Sophmore", "Junior", "Senior"};

        String out = String.format("\n\nCourse Name: Class for %s's with %s code \n\nCourse Date: %s \n\nCourse Code: %s \n\nNumber Of Students: %d",
                            cl[ccode.charAt(3) - 49] ,ccode.substring(4),generate_random_date() , ccode, rand.nextInt(50)+30);
        ((TextView)findViewById(R.id.course_info)).setText(out);
    }
    public String generate_random_date() {
        int saat = randBetween(9, 18);
        int dakika = randBetween(0, 59);
        return String.format("%02d - %02d - %d \n\nCourse Time: %02d.%02d : %02d.%02d",randBetween(1, 30), randBetween(1, 12),randBetween(2019, 2020)
        ,saat,dakika, randBetween(saat+1, 19), randBetween(dakika, 59) );
    }
    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
