package com.example.kevpoker;

import android.content.Intent;
import android.os.Bundle;
// import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class debug extends AppCompatActivity implements View.OnClickListener {

    int mycards[];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug);
        int cards=getIntent().getIntExtra("cards",5);
        mycards= new int[cards];          //just do 1 array of 52, easier to check for duplicates

        for (int i=0;i>cards;i++){
            mycards[i]=i;
        }
        System.out.println(String.format("got %d cards via intent",cards));


        EditText et1a= (EditText) findViewById(R.id.etcard1a);
        EditText et1b= (EditText) findViewById(R.id.etcard1b);          //considering change to be 1x EditText, card 0-51
        TextView tv1= (TextView) findViewById(R.id.tvcard1);

        Button btable1= (Button) findViewById(R.id.bcard1);
        Button btable2= (Button) findViewById(R.id.bcard2);
        Button btable3= (Button) findViewById(R.id.bcard3);
        Button btable4= (Button) findViewById(R.id.bcard4);
        Button btable5= (Button) findViewById(R.id.bcard5);
        btable1.setOnClickListener(this);
        btable2.setOnClickListener(this);
        btable3.setOnClickListener(this);
        btable4.setOnClickListener(this);
        btable5.setOnClickListener(this);

        Button shuffle = (Button) findViewById(R.id.bshuffle);
        Button finish = (Button) findViewById(R.id.bfinish);
        Button straight= (Button) findViewById(R.id.straight);
        Button flush = (Button) findViewById(R.id.flush);
        Button flush2 = (Button) findViewById(R.id.flush2);
        Button fullhouse= (Button) findViewById(R.id.fullhouse);
        Button fourkind= (Button) findViewById(R.id.fourkind);
        shuffle.setOnClickListener(this);
        finish.setOnClickListener(this);
        straight.setOnClickListener(this);
        flush.setOnClickListener(this);
        flush2.setOnClickListener(this);
        fullhouse.setOnClickListener(this);
        fourkind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
                            // TODO: fix index out of bounds exception
                            // this used to work; setting 5x drawn cards + 2x player1 cards.
                            // need to investigate later

        switch (v.getId()){
            case R.id.straight:
                mycards[0]=4;            mycards[1]=33;
                mycards[2]=18;            mycards[3]=50;
                mycards[4]=51;            mycards[5]=19;
                mycards[6]=34;          //careful! must insert these later in deck! the 2x (playernum) cards come first always!
                break;
            case R.id.flush:
                mycards[0]=2;            mycards[1]=12;
                mycards[2]=18;            mycards[3]=50;
                mycards[4]=7;            mycards[5]=5;
                mycards[6]=11;
                break;
            case R.id.flush2:
                mycards[0]=2;            mycards[1]=14;
                mycards[2]=18;            mycards[3]=50;
                mycards[4]=7;            mycards[5]=5;
                mycards[6]=11;
                break;
            case R.id.fullhouse:
                mycards[0]=2;            mycards[1]=9;
                mycards[2]=18;            mycards[3]=50;
                mycards[4]=28;            mycards[5]=22;
                mycards[6]=15;
                break;
            case R.id.fourkind:
                mycards[0]=0;            mycards[1]=13;
                mycards[2]=18;            mycards[3]=50;
                mycards[4]=51;            mycards[5]=39;
                mycards[6]=26;
                break;
            case R.id.bfinish:
                Intent myintent= new Intent();
                myintent.putExtra("cards",mycards);

                setResult(RESULT_OK,myintent);
                finish();
                break;
        }
    }


}
