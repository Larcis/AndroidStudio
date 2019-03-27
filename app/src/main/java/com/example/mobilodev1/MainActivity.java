package com.example.mobilodev1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG = 1;
    private TextView  name, surname, birth_place, identity_no, tel_no, birth_date;
    private ImageView profile_photo;
    private Uri       my_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Page");
        name          = findViewById(R.id.name);
        surname       = findViewById(R.id.surname);
        birth_place   = findViewById(R.id.birth_place);
        identity_no   = findViewById(R.id.identity_no);
        tel_no        = findViewById(R.id.phone_number);
        birth_date    = findViewById(R.id.birth_date);
        profile_photo = findViewById(R.id.profile_photo);

        birth_date.setShowSoftInputOnFocus(false);
        birth_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    choose_date(view);
                }
            }
        });
        update_components(savedInstanceState);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name",        name.getText().toString());
        outState.putString("surname",     surname.getText().toString());
        outState.putString("birth_place", birth_place.getText().toString() );
        outState.putString("telno",       tel_no.getText().toString());
        outState.putString("birth_date",  birth_date.getText().toString());
        outState.putString("identity_no", identity_no.getText().toString());
        outState.putParcelable("image_uri", my_image);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        update_components(savedInstanceState);
    }
    private void draw_image(){
        if(my_image != null) {
            try {
                final InputStream imageStream = getContentResolver().openInputStream(my_image);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profile_photo.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    private void update_components(Bundle savedInstanceState){
        if(savedInstanceState != null){
            try {
                name.setText(savedInstanceState.getString("name"));
                surname.setText(savedInstanceState.getString("surname"));
                birth_place.setText(savedInstanceState.getString("birth_place"));
                tel_no.setText(savedInstanceState.getString("telno"));
                birth_date.setText(savedInstanceState.getString("birth_date"));
                birth_place.setText(savedInstanceState.getString("birth_place"));
                my_image = savedInstanceState.getParcelable("image_uri");
                //profile_photo.setImageURI(my_image);
                draw_image();
            }catch (Error e){
                e.printStackTrace();
            }
        }
    }
    public void pick_image(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    private  boolean checkIsEmpty(String params[]){
        String names[] = {"Name", "Surname","Birth place", "Birth date"};
        for(int i = 0; i < params.length; i++){
            if(params[i].equals("")){
                Toast.makeText(MainActivity.this, names[i]+" cannot be empty!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
    public void go_next_activity(View view){
        if(my_image == null){
            Toast.makeText(MainActivity.this, "Profile photo must be selected!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(tel_no.getText().toString().length() < 11){
            Toast.makeText(MainActivity.this, "Please enter your phone number as 11 digit.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(identity_no.getText().toString().length() < 11){
            Toast.makeText(MainActivity.this, "Please enter your identity number as 11 digit.", Toast.LENGTH_SHORT).show();
            return;
        }
        String params[]  = new String[4];
        params[0] = name.getText().toString();
        params[1] = surname.getText().toString();
        params[2] = birth_place.getText().toString();
        params[3] = birth_date.getText().toString();

        if(checkIsEmpty(params)) {
            Intent i = new Intent(this, ShowInfoActivity.class);
            i.putExtra("name", params[0]);
            i.putExtra("surname", params[1]);
            i.putExtra("birth_place", params[2]);
            i.putExtra("birth_date", params[3]);
            i.putExtra("telno", tel_no.getText().toString());
            i.putExtra("tcno", identity_no.getText().toString());
            i.putExtra("image_uri", my_image);
            startActivity(i);
        }
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if(resultCode != RESULT_OK){
            Toast.makeText(MainActivity.this, "Choose operation canceled by user.", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (reqCode) {
            case RESULT_LOAD_IMG:
                    my_image = data.getData();
                    //profile_photo.setImageURI(my_image);
                    draw_image();
            break;
        }
    }
    public void clear_form(View view){
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void choose_date(View view){
        hideKeyboard(MainActivity.this);
        final Calendar myCalendar = Calendar.getInstance();
        final int nyear  = myCalendar.get(Calendar.YEAR);
        final int nmonth = myCalendar.get(Calendar.MONTH);
        final int nday   = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(      nyear  > year ||
                        (nyear == year && nmonth  > monthOfYear)||
                        (nyear == year && nmonth == monthOfYear && nday > dayOfMonth)){

                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    String day = Integer.toString(dayOfMonth+100);
                    String month = Integer.toString(monthOfYear+101);
                    birth_date.setText(String.format("%s/%s/%d", day.substring(1), month.substring(1), year));
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Unvalid Birth Date");
                    builder.setMessage("Selected birth date must be smaller than current date.");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
            }
        };
        int y = 1999, m = 9 ,d = 10;
        if(!birth_date.getText().toString().matches("")) {
            String date_[] = birth_date.getText().toString().split("/");
            y = Integer.parseInt(date_[2]);
            m = Integer.parseInt(date_[1])-1;
            d = Integer.parseInt(date_[0]);
        }
        new DatePickerDialog(MainActivity.this, dateListener, y, m, d).show();
    }
}
