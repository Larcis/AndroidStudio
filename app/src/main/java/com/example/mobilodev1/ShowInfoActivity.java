package com.example.mobilodev1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class ShowInfoActivity extends AppCompatActivity {

    private TextView name;
    private TextView surname;
    private TextView birth_place;
    private TextView identity_no;
    private TextView tel_no ;
    private TextView birth_date;
    private TextView age;
    private TextView email;

    private ImageView profile_photo;
    private Uri     my_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);
        setTitle("Showing Your Information");

        name          = findViewById(R.id.name);
        surname       = findViewById(R.id.surname);
        birth_place   = findViewById(R.id.birth_place);
        identity_no   = findViewById(R.id.identity_no);
        tel_no        = findViewById(R.id.tel_no);
        birth_date    = findViewById(R.id.birth_date);
        profile_photo = findViewById(R.id.profile_photo);
        age           = findViewById(R.id.age);
        email         = findViewById(R.id.email_t);
        update_components(savedInstanceState);

        Intent i  = getIntent();
        if(i != null) {
            name.setText("Name: " + i.getStringExtra("name"));
            surname.setText("Surname: " + i.getStringExtra("surname"));
            birth_place.setText("Birth Place: " + i.getStringExtra("birth_place"));
            birth_date.setText("Birth Date: " + i.getStringExtra("birth_date"));
            tel_no.setText("Tel. No: " + i.getStringExtra("telno"));
            identity_no.setText("TC No: " + i.getStringExtra("tcno"));
            age.setText(calculateAge(i.getStringExtra("birth_date")));
            email.setText(i.getStringExtra("email"));

            my_image = i.getParcelableExtra("image_uri");
            draw_image();
        }
    }
    private String calculateAge(String bd){
        Calendar today = Calendar.getInstance();
        String str[] = bd.split("/");
        int y, m , d;
        d = Integer.parseInt(str[0]);
        m = Integer.parseInt(str[1]);
        y = Integer.parseInt(str[2]);
        int age = today.get(Calendar.YEAR) - y;
        if(m > today.get(Calendar.MONTH)){
            age--;
        }else if(m == today.get(Calendar.MONTH) && d > today.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return ("Age: "+age);
    }
    public void callNumber(View view){
        if(isPermissionGranted()){
            call_action();
        }
    }
    public void send_mail(View v){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",email.getText().toString(), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Konu");
        //emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{});
        emailIntent.putExtra(Intent.EXTRA_TEXT, "\n\n\n\nÖdev 1 den göderildi....");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
    public void show_notes(View v){
        startActivity(new Intent(ShowInfoActivity.this, ShowNotesActivity.class));
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
        outState.putString("age", age.getText().toString());
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
                age.setText(savedInstanceState.getString("age"));
                my_image = savedInstanceState.getParcelable("image_uri");
                draw_image();
            }catch (Error e){
                e.printStackTrace();
            }
        }
    }
    public  boolean isPermissionGranted() {
        if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG","Permission is granted");
            return true;
        } else {
            Log.v("TAG","Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            return false;
        }

    }

    public void call_action(){
        String phnum = tel_no.getText().toString().split(":")[1];
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phnum));
        startActivity(callIntent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
