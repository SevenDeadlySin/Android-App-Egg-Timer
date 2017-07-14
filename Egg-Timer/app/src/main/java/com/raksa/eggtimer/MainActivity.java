package com.raksa.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // if the value is true. it is the play , if not it is stop
    boolean play_or_stop_Button_state = true;
    //if the timer was countdown
    boolean ifTimeWasCountDown = false;

    //Media for audio plalying..
    MediaPlayer chickenAudio;

    int minute,second;
    String time;
    CountDownTimer countDownTimer;

    int settedMinute , settedSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set audio
        chickenAudio = MediaPlayer.create(getApplicationContext(),R.raw.baby_chick);
        //relese the audio data when finish
        chickenAudio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                chickenAudio.release();
            }
        });

        //View
        final SeekBar seekbar = (SeekBar) findViewById(R.id.timeSeekBar);
        final TextView timeTextView = (TextView) findViewById(R.id.timeTextView);
        final Button play_or_stop_button = (Button) findViewById(R.id.play_or_stop_button);
        final Button resetButton = (Button) findViewById(R.id.resetButton);


        // set value for seekbar and time TextView
        seekbar.setMax(600);
        seekbar.setProgress(30);

        minute = (int)seekbar.getProgress()/60;
        second = seekbar.getProgress()-minute * 60;

        settedMinute = minute;
        settedSecond = second;

        time = minute+":"+second;

        timeTextView.setText(time);

        //region : interraction with Seekbar
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                minute = (int)progress/60;
                second =   progress-minute *60;

                if (second<=0){
                    time = minute+":00";
                }
                else if(second<10){
                    time = minute+":0"+second;
                }
                else {
                    time = minute+":"+second;
                }
                timeTextView.setText(time);

                //set value for recount feature
                settedMinute = minute;
                settedSecond = second;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //endregion

        //region : interraction with Play or Stop Button
        play_or_stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // do the operation according to the state of the button
                if (play_or_stop_Button_state){


                    if (minute!=0||second!=0){

                        //set the timer was countdowned
                        ifTimeWasCountDown = true;

                        //change button background and state
                        play_or_stop_button.setBackgroundResource(R.drawable.stop_button);
                        play_or_stop_Button_state = false;
                        seekbar.setEnabled(false);
                        resetButton.setEnabled(false);

                        final int milliSecondInFuture = (minute * 60 + second)*1000;
                        int milliSecondInterval = 1000;
                        //run the count down
                        countDownTimer = new CountDownTimer(milliSecondInFuture+2000,1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                                //String for textView
                                String remainingTime;

                                //countdown the time
                                minute = (int)(millisUntilFinished-1000) / 60000;
                                second =(int)((int)((millisUntilFinished-1000) / 1000)-(minute * 60));

                                if (second<10){
                                    remainingTime = minute+":0"+second;
                                }
                                else {
                                    remainingTime = minute+":"+second;
                                }

                                //set value to Time TextView
                                timeTextView.setText(remainingTime);


                                // play the audio when it's reach the time
                                if (second==0&&minute==0){
                                    chickenAudio = MediaPlayer.create(getApplicationContext(),R.raw.baby_chick);
                                    chickenAudio.start();
                                }
                            }

                            @Override
                            public void onFinish() {

                                //change stateButton to play
                                play_or_stop_button.setBackgroundResource(R.drawable.play_button);
                                play_or_stop_Button_state = true;
                                seekbar.setEnabled(true);
                                resetButton.setEnabled(true);


                            }
                        };

                        countDownTimer.start();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Plz Choose the value at least over 0 in Minute or Second",Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    play_or_stop_button.setBackgroundResource(R.drawable.play_button);
                    play_or_stop_Button_state = true;
                    seekbar.setEnabled(true);
                    resetButton.setEnabled(true);

                    countDownTimer.cancel();

                }
            }
        });
        //endregion

        //region Interraction with reset button

            resetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (ifTimeWasCountDown){
                        //change button background and state
                        play_or_stop_button.setBackgroundResource(R.drawable.stop_button);
                        play_or_stop_Button_state = false;
                        resetButton.setEnabled(false);
                        seekbar.setEnabled(false);

                        final int milliSecondInFuture = (settedMinute * 60 + settedSecond)*1000;
                        int milliSecondInterval = 1000;
                        //run the count down
                        countDownTimer = new CountDownTimer(milliSecondInFuture+2000,1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                                //String for textView
                                String remainingTime;

                                //countdown the time
                                minute = (int)(millisUntilFinished-1000) / 60000;
                                second =(int)((int)((millisUntilFinished-1000) / 1000)-(minute * 60));

                                if (second<10){
                                    remainingTime = minute+":0"+second;
                                }
                                else {
                                    remainingTime = minute+":"+second;
                                }

                                //set value to Time TextView
                                timeTextView.setText(remainingTime);

                                // play the audio when it's reach the time
                                if (second==0&&minute==0){
                                    chickenAudio = MediaPlayer.create(getApplicationContext(),R.raw.baby_chick);
                                    chickenAudio.start();
                                }

                            }

                            @Override
                            public void onFinish() {

                                //change stateButton to play
                                play_or_stop_button.setBackgroundResource(R.drawable.play_button);
                                play_or_stop_Button_state = true;
                                seekbar.setEnabled(true);

                            }
                        };

                        countDownTimer.start();
                    }

                }
            });

        //endregion



    }
}
