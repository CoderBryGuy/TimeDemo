package com.example.timedemo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //15 min in seconds
    int maxTime = 15 * 60;
    // min time of 10 seconds
    int minTime = 10;
    int secondsFromSeekBar = 0;
    int minutes =0, seconds =0;
    TextView myTextView;
    SeekBar mySeekBar;
    Button button;
    MediaPlayer mediaPlayerBuzzer;
    boolean timerActive = false;
    CountDownTimer countDownTimer;

    public void toggleTimer(View view){

         if(!timerActive) {

            timerActive = true;
            mySeekBar.setEnabled(false);
            button.setText("STOP!");
            stopStopBuzzerIfRunning();

            countDownTimer = new CountDownTimer(secondsFromSeekBar * 1000, 1000) {

                @Override
                public void onTick(long milliSecondsUntilDone) {
                    updateMyTextView(milliSecondsUntilDone / 1000, myTextView);
                }

                @Override
                public void onFinish() {
                     ringBuzzer();
                     resetTimer();
                     }
            }.start();
        }else {
            resetTimer();
            stopStopBuzzerIfRunning();
        }

    }

    private void resetTimer() {
        timerActive = false;
        myTextView.setText("00:00");
        mySeekBar.setProgress(minTime);
        mySeekBar.setEnabled(true);
        countDownTimer.cancel();
        button.setText(R.string.goMsg);
    }

    private void stopStopBuzzerIfRunning() {
        if (mediaPlayerBuzzer != null) {
            if (mediaPlayerBuzzer.isPlaying()) {
                mediaPlayerBuzzer.stop();
                mediaPlayerBuzzer.release();
            }
        }
    }

    private void ringBuzzer() {
        mediaPlayerBuzzer = MediaPlayer.create(this, R.raw.school_bell_sound);
        mediaPlayerBuzzer.start();
    }

    private void updateMyTextView(long secondsFromSeekBar, TextView myTextView ){
            minutes = (int) secondsFromSeekBar / 60;
            seconds = (int) secondsFromSeekBar % 60;
            myTextView.setText(
                    new StringBuilder()
                            .append(String.valueOf(minutes))
                            .append(":")
                            .append(seconds < 10
                                    ? ("0" + String.valueOf(seconds))
                                    : String.valueOf(seconds)).toString()
            );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.myBtn);

        mySeekBar = (SeekBar) findViewById(R.id.mySeekBar);
        mySeekBar.setMax(maxTime);
        mySeekBar.setMin(minTime);
        myTextView = (TextView)findViewById(R.id.myTextView);

        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                secondsFromSeekBar = i;
                updateMyTextView(secondsFromSeekBar, myTextView);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


}