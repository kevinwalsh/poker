package com.example.kevpoker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
// import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevpoker.logic.PokerGameLogic;
import com.example.kevpoker.model.Card;
import com.example.kevpoker.model.Game;

public class PokerActivity extends AppCompatActivity implements OnClickListener {

    String names[];
    int numplayers;
    int chips[];

    Game mygame;

    TextView tvcard2;
    TextView tvcard1;
    TextView tvcard3;
    TextView tvcard4;
    TextView tvcard5;
    TextView tvcard6;
    TextView tvcard7;
    TextView tvplayername;
    TextView tvplayerchips;

    TextView tvpot;
    TextView tvchipstocall;
    TextView tvplayers;
    TextView tvdealer;
    int roundcounter=0;

    PokerGameLogic pokerGameLogic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
                    // one guide suggested AppContainer to store shared classes/helpers, needs Application context
        pokerGameLogic = new PokerGameLogic();

        tvcard1=(TextView) findViewById(R.id.card1);
        tvcard2=(TextView) findViewById(R.id.card2);
        tvcard3=(TextView) findViewById(R.id.card3);
        tvcard4=(TextView) findViewById(R.id.card4);
        tvcard5=(TextView) findViewById(R.id.card5);
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

      //  dealplayers();
      //  setcards();       //should be done by NEWGAME! and newgame should be written properly for reuse
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle b= getIntent().getExtras();
        if(b==null){
            // issue if app started from here instead of lobby
            throw new RuntimeException("KW_EmptyIntentException");
        }
        names=b.getStringArray("names");
        numplayers=names.length;
        mygame=new Game(numplayers,b.getStringArray("names"),b.getIntArray("chips"));
        endoldgame();
        newgame();
    }

    public void onClick(View v) {
        TextView tvtemp = (TextView) findViewById(R.id.card1);

        switch(v.getId()) {

            case R.id.b1:       //raise
                if (mygame.players[mygame.playerturn].chips >= mygame.call + 10 -mygame.players[mygame.playerturn].callpaid ) {
              //  if (mygame.players[mygame.playerturn].chips > mygame.call * 2 + 10  ) {
                   // mygame.call = mygame.call * 2 + 10;      // I want 1.5 or custom, BUT may create floating errors for now
                    mygame.call+=10;
                    mygame.raisecounter=0;      //counter for raising

                    mygame.addtopot(mygame.call-mygame.players[mygame.playerturn].callpaid);
                    mygame.players[mygame.playerturn].chips-=(mygame.call-mygame.players[mygame.playerturn].callpaid);
                    mygame.players[mygame.playerturn].callpaid+=(mygame.call-mygame.players[mygame.playerturn].callpaid);

                    tvpot.setText("Current Pot: "+mygame.pot);
                    System.out.println(mygame.players[mygame.playerturn].name+" has raised call to "+mygame.call);

                    nextplayer();
                } else {
                    System.out.println("error- not enough chips for "+mygame.players[mygame.playerturn].name+" to raise!");
                    toastmessage("ERROR: Not enough chips for "+mygame.players[mygame.playerturn].name+" to raise!");
                }
                break;
            case R.id.b2:       //call
               if (mygame.players[mygame.playerturn].chips>mygame.call-mygame.players[mygame.playerturn].callpaid) {
                   mygame.addtopot(mygame.call - mygame.players[mygame.playerturn].callpaid);
                   mygame.players[mygame.playerturn].chips -= (mygame.call - mygame.players[mygame.playerturn].callpaid);
                   mygame.players[mygame.playerturn].callpaid += (mygame.call-mygame.players[mygame.playerturn].callpaid);

                   tvpot.setText("Current Pot: " + mygame.pot);
                   System.out.println(mygame.players[mygame.playerturn].name+" has called");

                   nextplayer();
               }
                else {toastmessage(mygame.players[mygame.playerturn].name+" going all in");
                    System.out.println("Not enough chips to call!"+mygame.players[mygame.playerturn].name+" going all in");
                    mygame.addtopot(mygame.players[mygame.playerturn].chips);
                    mygame.players[mygame.playerturn].callpaid+=mygame.players[mygame.playerturn].chips;
                    mygame.players[mygame.playerturn].chips=0;

                    tvpot.setText("Current Pot: " + mygame.pot);
                    mygame.activeplayers--;
                    mygame.players[mygame.playerturn].playerstatus="ALLIN";
                    tvplayers.setText(("Active Players: "+mygame.activeplayers));

                    nextplayer();
                }
                break;

            case R.id.b3:       //fold
                mygame.players[mygame.playerturn].playerstatus = "FOLD";
                mygame.activeplayers--;
                System.out.println(mygame.players[mygame.playerturn].name+" has folded. "+mygame.activeplayers+" players remain.");
                tvplayers.setText(("Active Players: "+mygame.activeplayers));

                nextplayer();
                break;

            case R.id.b4:       //reset
                int requiredcards = 5+2;        //easier to just set table + one player
                Intent a = new Intent(PokerActivity.this, debug.class);
                a.putExtra("cards", requiredcards);
                startActivityForResult(a,0);
                break;
        }

        // OUTSIDE OF SWITCH STATEMENT
        if (mygame.activeplayers<2) {            // if only one person left, quickly cycle through remaining rounds
            while (roundcounter < 3) {
                nextround();
            }
            nextround();
        }

        else while (mygame.players[mygame.playerturn].playerstatus!="ACTIVE"){
            System.out.println(String.format("SKIPPING! Player %s (status = %s)",mygame.players[mygame.playerturn].name, mygame.players[mygame.playerturn].playerstatus));
            nextplayer();
        }

        if (mygame.raisecounter>=mygame.activeplayers){
                            // TODO fix problem here, with folding.
                            // if everyone calls at pre-flop but then fold during flop,
                            // only 1st two folds are counted, and game wrongly skips to next round though player3 & 4 did nothing

            //go to next round
            System.out.println("round "+roundcounter+" finished, move to next stage");
            nextround();
        }
        pokerGameLogic.signOfLife();
    }

    public void endoldgame(){
        roundcounter=0;     //taken from round4. do most tasks EXCEPT finalize(), now calcpoints!
        mygame.raisecounter=0;
        mygame.reset();
    }

    public void newgame (){
        TextView tvtemp = (TextView) findViewById(R.id.hi);
        mygame.gamecounter++;

        mygame.call=mygame.minblind;
        dealplayers();
        mygame.dealerblinds();         //set dealer/ starting player, and auto-take blinds

        tvtemp.setText(" starting game number "+mygame.gamecounter+", players=" + numplayers);
        setcards();

        tvpot.setText("Current Pot: "+mygame.pot);
        tvdealer.setText("Dealer: "+mygame.players[mygame.gamecounter%mygame.activeplayers].name);
        tvplayers.setText("Active Players: "+mygame.activeplayers);
        tvchipstocall.setText("Chips Req'd to Call: N/A");

        setplayercards();
                     // TODO : fix bug where the original raiser can keep raising if everyone else just calls


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== RESULT_OK) {
            endoldgame();           //had to split "new game/ redeal" into 2 parts. shuffle, THEN do debug, FINALLY deal out cards

            int chosencards[]=data.getIntArrayExtra("cards");
            System.out.println("got result from activity result");
            System.out.println(String.format("MAINACTIVITY, activeplayers=%d",mygame.activeplayers));
            mygame.mydeck.swapcards(chosencards,mygame.activeplayers);
            newgame();
        }
    }


    public void dealplayers() {
                        // TODO this assumes all players are in! later need to include a status check for bust players etc
                        // investigate if its possible for a "bust" player to win a pot
        for (int i = 0; i < mygame.players.length; i++) {
            mygame.players[i].cards.add(mygame.mydeck.dealnext());
            mygame.players[i].cards.add(mygame.mydeck.dealnext());
        }
        tvplayers.setText("Active Players: "+mygame.activeplayers);
    }

    public void setcards(){
        tvcard1.setText("[N/A]");       //changing function, this is only called initially, while setplayercards updates via nextplayer
        tvcard2.setText("[N/A]");
        tvcard3.setText("[N/A]");
        tvcard4.setText("[N/A]");
        tvcard5.setText("[N/A]");

       setplayercards();

    }
    public void setplayercards() {
        tvcard6.setText(mygame.players[mygame.playerturn].cards.get(0).value);
        tvcard7.setText(mygame.players[mygame.playerturn].cards.get(1).value);
        tvplayername.setText(mygame.players[mygame.playerturn].name);
        int[] f = getPlayerColors();
        tvplayername.setTextColor(f[mygame.playerturn]);

        tvpot.setText("Current Pot: "+mygame.pot);
        tvplayerchips.setText("Chips: "+mygame.players[mygame.playerturn].chips);
        tvchipstocall.setText("Chips req'd to call: "+Integer.toString(mygame.call-mygame.players[mygame.playerturn].callpaid));

    }

    public void nextround(){
        //  ROUNDS:         (0) Pre-flop,   (1) Flop (x3)       (2) Turn        (3) River
        for (int i=0;i<mygame.players.length;i++){
            System.out.println(String.format("player %d, name=%s,card1=%s, card2=%s",i,mygame.players[i].name,mygame.players[i].cards.get(0).value,mygame.players[i].cards.get(1).value));
        }
        roundcounter++;
        //mygame.call=0;        //thought this was a solution, but it led to (-)ve numbers with (call-callpaid) in 2nd rounds.
                                //players took money back! It may be sufficient to just only set call=blind at start.

//        for (int i=0;i<mygame.players.length;i++){
//            mygame.players[i].callpaid=0;
//        }                             // used to need this, but now callpaid should keep a totals per deal

        switch (roundcounter) {
            case 1:
                mygame.raisecounter=0;

                mygame.table[0]= mygame.mydeck.dealnext();
                tvcard1.setText(mygame.table[0].value);
                mygame.table[1]= mygame.mydeck.dealnext();
                tvcard2.setText(mygame.table[1].value);
                mygame.table[2]= mygame.mydeck.dealnext();
                tvcard3.setText(mygame.table[2].value);
                break;
            case 2:
                mygame.raisecounter=0;
                mygame.table[3]= mygame.mydeck.dealnext();
                tvcard4.setText(mygame.table[3].value);
                break;
            case 3:
                mygame.raisecounter=0;
                mygame.table[4]= mygame.mydeck.dealnext();
                tvcard5.setText(mygame.table[4].value);
                break;
            case 4:
                //compare all cards; sort pot & winner; reset player cards & table cards; shuffle deck; reset pots/req'd counters
                mygame.calcpoints();
                toastmessage(mygame.winnerstring);
                                    //      TODO: change this to an alert dialog instead of toast

                endoldgame();
                newgame();
        }
    }

    public void nextplayer(){
        mygame.raisecounter++;
       mygame.nextplayer();         //also calls function of same name inside GAME class
           setplayercards();
    }

    public void toastmessage (String message) {

        // Toast.makeText(context,mychars,duration);
        Toast toast= Toast.makeText(PokerActivity.this,message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,200);
        toast.show();
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
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
               startActivity(i2);
                break;
            case R.id.exitapp:
                finish();
                break;
        }
        return false;
    }

    public int[] getPlayerColors(){
                    // TODO move this to a shared class
        return getResources().getIntArray(R.array.playerColors);
    }


}

/*Note: setting timers/delays- NEVER set a sleep/wait/delay timer inside a running UI thread!*/