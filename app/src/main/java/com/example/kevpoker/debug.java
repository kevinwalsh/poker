package com.example.kevpoker;

import android.content.Intent;
import android.os.Bundle;
// import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class debug extends AppCompatActivity implements View.OnClickListener {

    String[] ranks = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    String[] suits = {"♣", "♦", "❤", "♠"};

    //List<Card> selectedCards;
    int[] mycards;
    int players;
    TextView tvPlayer;
    TextView tvtablecards;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug);
        players = getIntent().getIntExtra("players", 4);
        // mycards = new int[players*2 + 5];    // if we were setting ALL playercards
        mycards = new int[7];

        tvPlayer = (TextView) findViewById(R.id.tvplayers);
        tvtablecards = (TextView) findViewById(R.id.tvtablecards);

        Button straight = (Button) findViewById(R.id.straight);
        Button flush = (Button) findViewById(R.id.flush);
        Button flush2 = (Button) findViewById(R.id.flush2);
        Button fullhouse = (Button) findViewById(R.id.fullhouse);
        Button fourkind = (Button) findViewById(R.id.fourkind);
        Button twotriple = (Button) findViewById(R.id.twotriple_fullhouse);
        Button onetriple = (Button) findViewById(R.id.onetriple);
        Button twopair = (Button) findViewById(R.id.twopair);
        Button onepair = (Button) findViewById(R.id.onepair);
        Button straightflush = (Button) findViewById(R.id.straightflush);
        Button royalstraight = (Button) findViewById(R.id.royalstraight);
        Button bReset = (Button) findViewById(R.id.bReset);
        Button finish = (Button) findViewById(R.id.bfinish);
        straight.setOnClickListener(this);
        flush.setOnClickListener(this);
        flush2.setOnClickListener(this);
        fullhouse.setOnClickListener(this);
        fourkind.setOnClickListener(this);
        twotriple.setOnClickListener(this);
        onetriple.setOnClickListener(this);
        twopair.setOnClickListener(this);
        onepair.setOnClickListener(this);
        straightflush.setOnClickListener(this);
        royalstraight.setOnClickListener(this);
        bReset.setOnClickListener(this);
        finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO: fix index out of bounds exception
        // this used to work; setting 5x drawn cards + 2x player1 cards.
        // need to investigate later

        switch (v.getId()) {
            case R.id.straight:
                mycards = new int[]{4, 33, 18, 50, 51, 19, 34};
                break;
            case R.id.flush:
                mycards = new int[]{2, 12, 18, 50, 7, 5, 11};
                break;
            case R.id.flush2:

                mycards = new int[]{2, 14, 18, 6, 7, 5, 11};
                break;
            case R.id.fullhouse:
                mycards = new int[]{2, 9, 18, 50, 28, 22, 15};
                break;
           case R.id.twotriple_fullhouse:
                mycards = new int[] {0,1,10,13,26,23,36};
                break;
           case R.id.onetriple:
                mycards = new int[]  {0,1,10,13,26,29,9};
                break;
           case R.id.twopair:
                mycards = new int[] {0,1,10,13,14,28,30};
                break;
           case R.id.onepair:
                mycards = new int[] {3,9,10,13,16,28,30};
                break;

           case R.id.straightflush:
                mycards = new int[] {2,3,4,6,10,5,20};
                break;
           case R.id.royalstraight:
                mycards= new int[] {51,50,48,49,33,1,47};
                break;
            case R.id.fourkind:
                mycards = new int[]{0, 13, 18, 50, 51, 39, 26};
                break;
            case R.id.bReset:
                mycards = new int[]{0, 1, 2, 3, 4, 5, 6};
                break;
            case R.id.bfinish:
                Intent myintent = new Intent();
                if (mycards == null) {
                    mycards = new int[]{0, 1, 2, 3, 4, 5, 6};
                }
                myintent.putExtra("cards", mycards);
                setResult(RESULT_OK, myintent);
                finish();
                break;

        }
        CardValsToTextViews(mycards);


    }

    private void CardValsToTextViews(int[] cards) {
        String p = "Player 1: \t";
        String t = "Table:\t";
        for (int i = 0; i < 2; i++) {
            p += ranks[cards[i] % 13] + suits[cards[i] / 13] + ", ";
        }
        for (int i = 2; i < cards.length; i++) {
            t += ranks[cards[i] % 13] + suits[cards[i] / 13] + ", ";
        }
        tvPlayer.setText(p);
        tvtablecards.setText(t);
    }
}