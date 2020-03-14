package com.example.kevpoker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
// import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kevpoker.view.LobbyPlayerViewModel;


public class MainLobby extends AppCompatActivity implements OnClickListener {
                            // TODO rename & redesign this; bad naming convention at least
   // static int colors[]={Color.RED, Color.BLUE,Color.GREEN,Color.MAGENTA,Color.CYAN,Color.parseColor("#ff8c00")};
                                    //dont need: moved to @Values/Colors, and referenced directly in XML layout
    int ids=1;
    LobbyPlayerViewModel p[];
   // String restype="t";         //resource type? set to T,E,B, etc for textview, button?
                                    // OR! use p/n/c as per xml.    (player, name, chips)

    int count=4;
    TextView title;
    Button bStart;
    Button bAdd;
    Button bSubtract;
    TextView players[];
    EditText names[];
    EditText chips[];

    @Override
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

    public int convertid (String type, int index){
        //  FROM    http://stackoverflow.com/questions/4865244/android-using-findviewbyid-with-a-string-in-a-loop
        String myid = type + Integer.toString(index);
        int x= getResources().getIdentifier(myid,"id",getPackageName());
        return x;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView((R.layout.lobby));

        title = (TextView) findViewById(R.id.title);
        p = new LobbyPlayerViewModel[6];
        for (int i=0;i<6;i++){
            p[i]= new LobbyPlayerViewModel();
            p[i].p = (TextView) findViewById(convertid("p",i+1));
            p[i].n = (EditText) findViewById(convertid("n",i+1));
            p[i].c = (EditText) findViewById(convertid("c",i+1));

            if (i>count-1){           //more than N players
                p[i].p.setVisibility(View.INVISIBLE);
                p[i].n.setVisibility(View.INVISIBLE);
                p[i].c.setVisibility(View.INVISIBLE);
            }
        }

        bAdd= (Button) findViewById(R.id.bAdd);
        bSubtract= (Button) findViewById(R.id.bSubtract);
        bStart= (Button) findViewById(R.id.bStart);
        bStart.setOnClickListener(this);
        bAdd.setOnClickListener(this);
        bSubtract.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("KW: onstop called from somewhere???");
        finish();
    }


    public void extraplayer () {
        System.out.println("adding");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bAdd:
                //extraplayer();
                System.out.println("add player");
                changepnum(1);

                break;
            case R.id.bSubtract:
                System.out.println("remove");
                changepnum(-1);
                break;
            case R.id.bStart:
                boolean playercheck = true;
                String names[]= new String[count];      //cos count will = length, but just not [maxindex]
                int chips[]= new int[count];
                for (int i=0;i<count;i++){          //dont need count-1. will stop before/when we reach count
                    names[i]=p[i].n.getText().toString();
                    chips[i]=Integer.parseInt(p[i].c.getText().toString());
                    if (names[i].isEmpty()==true || chips[i]<=0)    {playercheck=false;}
                    System.out.println("NAME_"+names[i]+"_END");
                }

            if (playercheck==true) {
                Bundle bundle = new Bundle();
                bundle.putIntArray("chips", chips);
                bundle.putStringArray("names", names);
                System.out.println("gamelobby just before intent start, length = " + chips.length);

                Intent iStart = new Intent("com.example.kevpoker.MAINACTIVITY");
                iStart.putExtras(bundle);
                startActivity(iStart);
            }
                else {
                System.out.println("playercheck false- Error somewhere. ensure all names not empty and chips > 0");
                    }
                break;
        }
    }

    public void changepnum(int x){              //player num
        if (x>0){
            if (count<6) {
                count++;
                p[count-1].p.setVisibility(View.VISIBLE);
                p[count-1].n.setVisibility(View.VISIBLE);
                p[count-1].c.setVisibility(View.VISIBLE);
                //p[count-1].p.setText("Just added");
            }
            else System.out.println("Error! Cant add more players! (maximum 6)");
        }
        else {
            if (count>2) {
//                p[count-1].p.setText("player removed");
                p[count-1].p.setVisibility(View.INVISIBLE);
                p[count-1].n.setVisibility(View.INVISIBLE);
                p[count-1].c.setVisibility(View.INVISIBLE);
                count--;
            }
            else {
                System.out.println("Error! cant remove! need at least 2 players");
            }
        }
    }



}
