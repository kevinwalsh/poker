package com.example.kevpoker.services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.Toast;

import com.example.kevpoker.model.Card;
import com.example.kevpoker.model.Player;

import java.util.List;

public class ConsolePrintService {


    public void toastmessage (String message, Context context) {

        Toast toast= Toast.makeText(context,message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,200);
        toast.show();
    }

    public void AlertDialogBox(String title, String message, String yesanswer, String noanswer,
                               Context context){

        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(yesanswer, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  TODO pass in reference to desired method as input
                    }

                })
                .setNegativeButton(noanswer, null)
                .show();
    }

    public void PrintCardVals(List<Card> cards){
        String s = "CARDS: ";
        for (Card c : cards){
            s+= c.value + ",\t";
        }
        System.out.println(s);
    }

    public void PrintScores(List<Player> players, List<Card> tablecards){
        String msg = ("--------RESULTS---------\n\n"
                +"PLAYER \t\t STATUS \t POINTS \t CARDS \t\t CHIPS PAID \t  \n");
        for(Player p : players){
           String s = "";
            s+= p.name + "\t\t  ";
            s+= p.playerstatus + "\t\t";
            s+= (p.handScore != null ? p.handScore.handType : "null" )+ "\t\t";           // might cause problems
            s+= p.cards.get(0).value+"  " + p.cards.get(1).value+ "\t\t";
            s+= p.callpaid+ "\t";
            s+="\n"; msg+=s;
        }
        msg+= "TableCards:\t\t";
        for(Card c: tablecards){
            msg+="\t "+ c.value;
        }
        msg+="\n";
        System.out.print(msg);
    }



}
