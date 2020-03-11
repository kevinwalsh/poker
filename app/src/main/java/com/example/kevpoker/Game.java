package com.example.kevpoker;

public class Game {

    Player players[];
    Card table[];

    Deck mydeck;
    int playerturn=0;
    int raisecounter=0;         //for tracking when everyone has finished raising
    int gamecounter=0;
    int activeplayers;
    int pot=0;
    int potpledge[];                //didnt need in end, can do same thing with callpaid
    int call=10;    //the amount each player has to call to stay in the game(?)
    int minblind=10;

    String winnerstring;


    public  Game (int numplayers, String names[],int chips[]){
        mydeck = new Deck();

        players = new Player[numplayers];
        for (int i=0;i<numplayers;i++){
            players[i]= new Player(names[i], chips[i]);
            System.out.println(String.format("Player %d created, %s. Starting Chips=%d",i+1,players[i].name,players[i].chips));
        }
        table= new Card[5];
    }

    public void nextplayer(){
        playerturn++;
        if (playerturn>=players.length){playerturn=0;}
        System.out.println(String.format("Player changed to #%d, %s",playerturn, players[playerturn].name));
    }

    public void resetactiveplayers(){
    //    System.out.println("resetting players from FOLD or ALLIN to ACTIVE, and Broke players to BUST. (also resetting game and player calls)");
     //   System.out.println("RESETACTIVEPLAYERS: 0 chips -> Bust; Fold/Allin-> Active, reset counters");
        call=0;
        for (int i=0;i<players.length;i++){
            players[i].callpaid=0;

            if (players[i].chips<=0) {players[i].playerstatus="BUST";
                System.out.println("ELIMINATED! Player #"+i+", "+players[i].name+" has gone bust");}

            if (players[i].playerstatus=="FOLD" || players[i].playerstatus=="ALLIN"){
                activeplayers++;            //sept 2nd... been away awhile, but why is this here? isnt it overwritten below?
            players[i].playerstatus="ACTIVE";
            }
        }
    }
    public void checkactiveplayers(){
        activeplayers=0;
        for (int i=0;i<players.length;i++){
            if (players[i].playerstatus!="BUST"){
                activeplayers++;
            }
        }
        System.out.println(String.format("active players remaining=%d",activeplayers));
    }

    public Card[] sortcards(Card card1, Card card2){          //called currently by comparehands() / finalize(), now calcpoints
        Card temp[]= new Card[7];
        temp[0]= table[0];
        temp[1]= table[1];
        temp[2]= table[2];
        temp[3]= table[3];
        temp[4]= table[4];
        temp[5]= card1;
        temp[6]= card2;

        boolean sorted=false;
        int sortcount=0;
        while ( sorted==false){
            sorted=true;
            for (int z = temp.length - 1; z > 0; z--) {
                if (temp[z].rank<temp[z-1].rank) {
                    Card tempswitch= temp[z];
                    temp[z]=temp[z-1];temp[z-1]=tempswitch;sorted=false;sortcount++;}
            }
        }
        return temp;
    }


    public void calcpoints(){     //to calculate hands
        for (int i=0;i<players.length;i++){             //initial prob here! "player" cards will be mixed in after sort!
           Card temp[];
            temp=sortcards(players[i].cards[0],players[i].cards[1]);
            players[i].checkpoints(temp);

        }       //end of player i pointcount

        checkwinnernew(players);
    }

    public void checkwinnernew(Player players[]){           //new one, to rank players. deal with ALLIN vs ACTIVE elsewhere
       //int playerid[]={0,1,2,3};                  //only catered for fixed 4 players! my codes prob riddled with this!
        int playerid[]= new int[players.length];
        for (int w=0;w<playerid .length;w++){playerid[w]=w;}
       //int playerscores[]=new int[4];
        int playerscores[]=new int[players.length];
       for (int i=0;i<playerid.length;i++){
           playerscores[i]=players[i].handpoints*15 +players[i].highcard;
       }
        boolean sort=true;          //sort function
        while (sort==true){
            sort=false;
            for (int q=1;q<playerid.length;q++){
                if (playerscores[q-1]<playerscores[q])  {
                    sort=true;
                    int temp=playerscores[q-1];
                    playerscores[q-1]=playerscores[q];
                    playerscores[q]=temp;

                                //also must switch playerids!
                    temp=playerid[q-1]; playerid[q-1]=playerid[q];  playerid[q]=temp;
                }
            }

        }
        // PLAYER SCORES
        System.out.println("-------------------------------------------------------------");
        System.out.println("RANK  PLAYER  NAME  \t STATUS\t\tCARD1 CARD2\t BET SCORE POINTS HIGHCARD BESTHAND");
        for (int q=0;q<playerid.length;q++){
         //   System.out.println(String.format("rank %d, player %d,name %s, score %d",q,playerid[q],players[playerid[q]].name,playerscores[q]));
            System.out.println(String.format("%d\t\t%d\t\t%s\t",q,playerid[q],players[playerid[q]].name)+
                    String.format("\t%s\t",players[playerid[q]].playerstatus)+
                    String.format("%s \t%s \t",players[playerid[q]].cards[0].value,players[playerid[q]].cards[1].value)+
                    String.format("%d\t%d\t",players[playerid[q]].callpaid, playerscores[q])+
                    String.format("%d\t\t%d\t\t%s  \t",players[playerid[q]].handpoints,players[playerid[q]].highcard,players[q].hand[players[playerid[q]].handpoints]));

            //OLD layout, Ive added to it and made it nicer.
//            System.out.println(String.format("%d \t %d \t\t %s \t ",q,playerid[q],players[playerid[q]].name)+
//                    String.format("%d    %s    \t",playerscores[q],players[playerid[q]].playerstatus)+
//                    String.format("%s \t %s \t",players[playerid[q]].cards[0].value,players[playerid[q]].cards[1].value)+
//                    String.format("%d \t %s \t",players[playerid[q]].callpaid, players[q].hand[players[playerid[q]].handpoints]));

        }
        System.out.println(String.format("TABLE CARDS: \t %s \t %s \t %s \t %s \t %s",table[0].value,table[1].value,table[2].value,table[3].value,table[4].value));
        System.out.println("-------------------------------------------------------------");


        printscores(players);
        int winnerrank=0;

        while (pot>0) {
            if (players[playerid[winnerrank]].playerstatus=="ACTIVE" || players[playerid[winnerrank]].playerstatus=="ALLIN") {
                int sidepot = 0;
                for (int q = 0; q < playerid.length; q++) {
                    sidepot += (Math.min(players[q].callpaid, players[playerid[winnerrank]].callpaid));
                    // NB, winnerrank=1st/2nd place, etc. ->playerid[winnerrank] = actual player!
                    //  ALSO, cant just use players[q] because they havent been ordered by winner rank!
                }
             System.out.println(String.format("Winnerrank %d, player %d, name %s,points=%d, status=%s, wins pot of %d. (pot of %d remains)",winnerrank,playerid[winnerrank],players[playerid[winnerrank]].name,players[playerid[winnerrank]].handpoints,players[playerid[winnerrank]].playerstatus,sidepot,pot));
                                    //REALLY long line above

                potwinner(players[playerid[winnerrank]], sidepot);       //  Still havent accounted for draws!
            }
        winnerrank++;
        }
    }

    public void printscores(Player players[]){
        for (int i=0;i<players.length;i++) {
           System.out.println(String.format("player %d=%s, cards= %s %s, chips=%d, callpaid=%d ", i,players[i].name,
                   players[i].cards[0].value, players[i].cards[1].value,players[i].chips,players[i].callpaid));
           System.out.println(String.format("player %d= %s, *POINTS*=%d, hand=%s, highcard=%d, status=%s",
           i, players[i].name, players[i].handpoints, players[i].hand[players[i].handpoints], players[i].highcard, players[i].playerstatus));
       }
    }

    public void potwinner(Player winner, int potwinnings){
        winnerstring= "End of Round #"+gamecounter +". "+winner.name+" wins $"+potwinnings+" sidepot. Pot remaining:"+pot;
    //    System.out.println(String.format("POT WINNER!!! %s wins %d",winner.name,potwinnings));
       winner.chips+=potwinnings;
       pot-=potwinnings;
    }

    public void addtopot(int chips){
        pot+=chips;
    }

    public void reset(){
        mydeck.deckCounter=0;
    //  playerturn=0;       //moving this to dealerBlinds, AND makes more sense to do from newgame(), rather than resetoldgame()
        mydeck.shuffle();
        resetactiveplayers();
        checkactiveplayers();
    }

    public void dealerblinds (){
        playerturn=(gamecounter+1)%activeplayers;        //shouldnt matter if this player is bust; dealerblinds will skip accordingly
        System.out.println("dealerblinds:playerturn set to "+playerturn);
      //  dealerblinds();
        for (int i=2;i>0;i--) {
            while (players[playerturn].playerstatus != "ACTIVE") {
                nextplayer();
            }
        players[playerturn].chips-=minblind/i;
        players[playerturn].callpaid+=minblind/i;
        addtopot(minblind/i);
        System.out.println(String.format("BLINDS: player %d, %s paid %d",playerturn,players[playerturn].name,minblind/i));
        nextplayer();
        }
    }



}
