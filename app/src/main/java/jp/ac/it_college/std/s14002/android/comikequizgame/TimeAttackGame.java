package jp.ac.it_college.std.s14002.android.comikequizgame;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class TimeAttackGame extends AppCompatActivity {
    private Chronometer chronometer;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_attack_game);
        chronometer = (Chronometer)findViewById(R.id.chronometer);
        time = getIntent().getStringExtra("Time");

        chronometer.start();
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (chronometer.getText().equals(time)) {
                    finish();
                }
                Log.e("Log :", String.valueOf(chronometer.getText()));
            }
        });


    }
}

