package com.example.kevpoker;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevpoker.logic.PokerGameLogic;
import com.example.kevpoker.model.Card;
import com.example.kevpoker.model.Deck;
import com.example.kevpoker.model.Game;
import com.example.kevpoker.model.Player;
import com.example.kevpoker.services.ConsolePrintService;

import java.util.ArrayList;
import java.util.List;

public class PokerActivity extends AppCompatActivity implements OnClickListener {

    String names[];
    int numplayers;
    int chips[];

    Game mygame;

    List<TextView> tableCardstv;        // neater list of 5x tablecards
    TextView tvcard6;
    TextView tvcard7;
    TextView tvplayername;
    TextView tvplayerchips;

    TextView tvpot;
    TextView tvchipstocall;
    TextView tvplayers;
    TextView tvdealer;

    PokerGameLogic pokerGameLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
                    // one guide suggested AppContainer to store shared classes/helpers, needs Application context
        pokerGameLogic = new PokerGameLogic();

        tableCardstv = new ArrayList<TextView>();
            tableCardstv.add((TextView) findViewById(R.id.card1));
            tableCardstv.add((TextView) findViewById(R.id.card2));
            tableCardstv.add((TextView) findViewById(R.id.card3));
            tableCardstv.add((TextView) findViewById(R.id.card4));
            tableCardstv.add((TextView) findViewById(R.id.card5));
        tvcard6=(TextView) findViewById(R.id.card6);
        tvcard7=(TextView) findViewById(R.id.card7);
        tvplayername= (TextView) findViewById(R.id.player);
        tvplayerchips= (TextView) findViewById(R.id.chips);

        tvpot=(TextView) findViewById(R.id.pot);
        tvchipstocall=(TextView) findViewById(R.id.calldifference);
        tvplayers=(TextView) findViewById(R.id.activeplayers);
        tvdealer=(TextView) findViewById(R.id.dealer);

        Button b1= (Button) findViewById(R.id.b1);
        Button b2= (Button) findViewById(R.id.b2);
        Button b3= (Button) findViewById(R.id.b3);
        Button b4= (Button) findViewById(R.id.b4);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);

        // CreateGame & shuffle in OnCreate! otherwise going to debug will reset game
        Bundle b= getIntent().getExtras();
        if(b==null){
            // issue if app started from here instead of lobby
            throw new RuntimeException("KW_EmptyIntentException");
        }
        names=b.getStringArray("names");
        numplayers=names.length;
        mygame=new Game(this, numplayers,b.getStringArray("names"),b.getIntArray("chips"));
        mygame.CreateNewTable();
        mygame.StartFirstRound();
      }

    @Override
    protected void onStart() {
        super.onStart();

        setTableCards();
        setplayercards();
    }

    public void onClick(View v) {
        TextView tvtemp = (TextView) findViewById(R.id.card1);

        switch(v.getId()) {

            case R.id.b1:       //raise
                boolean canraise = mygame.table.Raise(mygame.table.getCurrentPlayer());
                if (!canraise) {
                    ConsolePrintService cps = new ConsolePrintService();
                    cps.toastmessage("ERROR: not enough chips to raise", this);
                }
                    // dont even need returned boolean, as nextPlayer is inside table.raise().call(), inside if/else
                break;
            case R.id.b2:       //call
                mygame.table.Call(mygame.table.getCurrentPlayer());
                break;

            case R.id.b3:       //fold
                mygame.table.Fold(mygame.table.getCurrentPlayer());

                break;
            case R.id.b4:       //reset
                // TODO game reset
                break;
        }
        setTableCards();
        setplayercards();

    }

    public void EndOldTableGame_StartNew(){
        mygame.EndOldGame();
        mygame.CreateNewTable();
        mygame.StartFirstRound();
    }


    public void setTableCards(){
        List<Card> tblcards = mygame.table.tablecards;
        for(int i = 0; i < tblcards.size(); i++){
            tableCardstv.get(i).setText(tblcards.get(i).value);
        }
        for(int i = tblcards.size(); i< tableCardstv.size();i++){
            tableCardstv.get(i).setText("[N/A]");
        }
        tvdealer.setText("dealer= "+ mygame.players.get(mygame.gameDealer).name);
    }

    public void setplayercards() {
        Player p = mygame.table.getCurrentPlayer();
        tvcard6.setText(p.cards.get(0).value);
        tvcard7.setText(p.cards.get(1).value);
        tvplayername.setText(p.name);
        int[] f = getPlayerColors();
        tvplayername.setTextColor(f[mygame.table.playerTurn]);

        tvpot.setText("Table call: "+mygame.table.call);
        tvplayerchips.setText("Chips: "+(p.chips - p.callpaid));
        tvchipstocall.setText("Chips req'd to call: " +
                Integer.toString(mygame.table.call - p.callpaid)
            );
    }

    public void onBackPressed() {
        ConsolePrintService cps = new ConsolePrintService();

        String title = ("Closing Activity");
        String message = "Are you sure you want to close this activity?";
        String yesbutton = "Yes";   String nobutton = "No";
        cps.AlertDialogBox(title,message,yesbutton,nobutton, this );
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater blowup= getMenuInflater();
        blowup.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
//            case R.id.aboutUs :
//                Intent i = new Intent("com.example.kevmenu4.ABOUTUS");
//                startActivity(i);
//                break;
//            case R.id.preferences :
//                Intent i2 = new Intent("com.example.kevmenu4.PREFS");
//                startActivity(i2);
//                break;
            case R.id.debug:
                Intent i2 = new Intent("com.example.kevpoker.DEBUG");
                i2.putExtra("players", mygame.players.size());
                startActivityForResult(i2,1);
                break;
            case R.id.exitapp:
                finish();
                break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== RESULT_OK) {
            int chosencards[]=data.getIntArrayExtra("cards");

            mygame.RearrangeDeck(chosencards);      //sep2020- should move to debug
            mygame.ReplaceCards();          // takeback currently dealt cards & replace w/ debug cards
        }
    }

    public int[] getPlayerColors(){
                    // TODO move this to a shared class
        return getResources().getIntArray(R.array.playerColors);
    }


}

/*Note: setting timers/delays- NEVER set a sleep/wait/delay timer inside a running UI thread!*/