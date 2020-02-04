package com.example.moviesapp2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Reminder extends AppCompatActivity {

    private Broadcast broadcast = new Broadcast();
    private Broadcast2 broadcast2 = new Broadcast2();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Set Reminder");
        }

        final Switch switchdaily = findViewById(R.id.d_reminder);
        final Switch switchnewmovie  = findViewById(R.id.release);

        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        switchdaily.setChecked(sharedPreferences.getBoolean("value",false));

        SharedPreferences sharedPreferences2 = getSharedPreferences("save2",MODE_PRIVATE);
        switchnewmovie.setChecked(sharedPreferences2.getBoolean("value2",false));


        switchdaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.d("_Switch State=", ""+isChecked);

                if (switchdaily.isChecked()){

                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    switchdaily.setChecked(true);
                    broadcast.setRepeatingAlarm(getApplicationContext());

                }else{

                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switchdaily.setChecked(false);
                }


            }

        });


        switchnewmovie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.d("_Switch State2=", ""+isChecked);

                if (switchnewmovie.isChecked()){



                    broadcast2.setRepeatingAlarm(getApplicationContext());
                    SharedPreferences.Editor editor = getSharedPreferences("save2",MODE_PRIVATE).edit();
                    editor.putBoolean("value2",true);
                    editor.apply();
                    switchnewmovie.setChecked(true);
                    Toast.makeText(getApplicationContext(), "New Movie Reminder Set Up", Toast.LENGTH_SHORT).show();


                }else{

                    SharedPreferences.Editor editor = getSharedPreferences("save2",MODE_PRIVATE).edit();
                    editor.putBoolean("value2",false);
                    editor.apply();
                    switchnewmovie.setChecked(false);
                }


            }

        });




    }

}
