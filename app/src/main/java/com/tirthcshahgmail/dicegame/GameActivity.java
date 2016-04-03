package com.tirthcshahgmail.dicegame;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class GameActivity extends ActionBarActivity {
    Random r = new Random();
    boolean userTurn = true;
    android.os.Handler handler=new android.os.Handler();
    int dice;
    ImageView diceImage;
    TextView TAG;
    Button roll, hold, reset;
    int[] dices = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};
    private int overallScoreComp, turnScoreComp, overallScoreUser, turnScoreUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        diceImage = (ImageView) findViewById(R.id.imageView);
        roll = (Button) findViewById(R.id.roll);
        hold = (Button) findViewById(R.id.hold);
        reset = (Button) findViewById(R.id.reset);
        TAG=(TextView)findViewById(R.id.textView);
        TAG.setText(getTag());
        reset(null);
    }

    private String getTag() {
        return "computer " + overallScoreComp + " player " + overallScoreUser;

    }
    public void rollDie(View view){
        roll();
    }

    private Runnable rollImage= new Runnable() {
        @Override
        public void run() {
            diceImage.setImageResource(dices[dice - 1]);
        }
    };

    public void roll() {
         dice = r.nextInt(6) + 1;
         handler.postDelayed(rollImage,40);
        if (userTurn) {
            if (dice == 1) {
                overallScoreUser = 0;
                userTurn = false;
                computerTurn();
            } else {
                overallScoreUser += dice;
            }
        } else {
            if (dice == 1) {
                overallScoreComp = 0;
                userTurn = true;
                computerTurn();

            } else {
                overallScoreComp += dice;
            }
            Log.d("comp score","dice is "+dice +" score is "+overallScoreComp);
        }
        TAG.setText(getTag());
    }

    public void hold(View v) {
        userTurn = !userTurn;
        computerTurn();
    }

    public void computerTurn() {
        if (userTurn) {
            roll.setClickable(true);
            hold.setClickable(true);
            return;
        }
        roll.setClickable(false);
        hold.setClickable(false);
        for (int i = 0; i < 3; i++) {
            if (userTurn) {
                return;
            }else {
                roll();
            }
//            Log.d("chance","comp turn is "+i);
        }
        userTurn=true;
        computerTurn();
    }

    public void reset(View v) {
        overallScoreComp = 0;
        overallScoreUser = 0;
        turnScoreComp = 0;
        turnScoreUser = 0;
        diceImage.setImageResource(R.drawable.dice1);
        TAG.setText(getTag());
        userTurn = true;
        roll.setClickable(true);
        hold.setClickable(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
