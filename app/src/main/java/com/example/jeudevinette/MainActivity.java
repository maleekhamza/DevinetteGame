package com.example.jeudevinette;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final long START_TIME_IN_MILLIS = 60000;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private Button newGame;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    public static final int MAX_NUMBER = 1000;
    public static final Random RANDOM = new Random();
    private TextView msgTv;
    private EditText numberEnteredEt;
    private Button validate;
    private int numberToFind, numberTries;
    private TextView text_view_countdown;
    private TextView liste;
//    private TextView nbClick;
    private int lescore =100;
    int count =0;
    private String Nombre="";
    private TextView score;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        newGame = findViewById(R.id.NewGame);

        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        text_view_countdown=findViewById(R.id.text_view_countdown);
        score=findViewById(R.id.score);
//        nbClick=findViewById(R.id.nbClick);



        msgTv = (TextView) findViewById(R.id.msg);
        numberEnteredEt = (EditText) findViewById(R.id.numberEnteredEt);
        liste = findViewById(R.id.liste);

        validate = (Button) findViewById(R.id.validate);

        validate.setOnClickListener(this);

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numberEnteredEt.setText("");
                text_view_countdown.setText("00:00");
            }
        });

        newGame();
        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else  {
                    startTimer();
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        updateCountDownText();



    }
    private void startTimer() {

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                int i=0;
                lescore= 100 -( i+numberTries);
                score.setText("le score est:" +lescore);

            }

            @Override
            public void onFinish() {

                Toast.makeText(MainActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                newGame.setVisibility(View.VISIBLE);

                ;            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);


    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.INVISIBLE);


    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.VISIBLE);

    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }


    @Override
    public void onClick(View view) {
        if (view == validate) {
            validate();
        }
    }

    private void validate() {
        newGame.setVisibility(View.VISIBLE);
        if (numberEnteredEt.getText().toString().trim().length() > 0) {
//           count++;
//            nbClick.setText(String.valueOf(count));
             Nombre  += numberEnteredEt.getText().toString()+"\n";
            liste.setText(" Your Tries:\n" + Nombre);


        }
        startTimer();
        int n = Integer.parseInt(numberEnteredEt.getText().toString());
        numberTries++;

        if (n == numberToFind) {
            Toast.makeText(this, "Congratulations ! You found the number " + numberToFind +
                    " in " + numberTries + " tries", Toast.LENGTH_SHORT).show();
            newGame();
        } else if (n > numberToFind) {
            msgTv.setText(R.string.too_high);
        } else if (n < numberToFind) {
            msgTv.setText(R.string.too_low);
        }


    }

    private void newGame() {
        numberToFind = RANDOM.nextInt(MAX_NUMBER) + 1;
        msgTv.setText(R.string.start_msg);
        numberEnteredEt.setText("");
        numberTries = 0;
    }
}