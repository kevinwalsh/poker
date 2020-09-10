package com.example.kevpoker.logic;

import com.example.kevpoker.model.Player;

import java.util.Collections;
import java.util.List;

public class PokerBetLogic {


    public PokerBetLogic(){
        System.out.println("PokerBetLogic initialized");
    }

    public int Call(Player p, int currentCall){
        p.Call(currentCall);
        return 1;

    }

   /* public boolean raise(){
        //not needed unless we maybe do more complex stuff;     // in table, raising blind and "calling"
        return false;
    }

    public void fold(){          // prob shouldnt be here
        // not needed
    }
*/


   //------------------- payouts
   public void PayoutWinningsAll(List<Player> activePlayers){
       Collections.sort(activePlayers, Player.getPointsComparator());
       boolean sidePotRemaining = true;
       for(Player p : activePlayers){
           if(sidePotRemaining) {
               sidePotRemaining = PayoutWinningsSingle(p, activePlayers);
           }
       }
   }

    public boolean PayoutWinningsSingle(Player winner, List<Player> activePlayers){
        int chipsbefore = winner.chips;
        int winner_callpaid = winner.callpaid;   // need temp value, as for this algo, winner will also "payout"
        boolean isSidepot_remaining = false;
        for(Player p:activePlayers){
            int win = p.Payout(winner_callpaid);       // winners call OR whatever player paid in
            winner.chips+=win;      // cant do in 1 line as it keeps "old" chips val for winner

            if(p.callpaid > 0) {         // after winner takes their share; is anything still in pot
                isSidepot_remaining = true;
            }
        }
        String msg="player "+ winner.name + " won " + (winner.chips-chipsbefore) +" chips";
        System.out.println(msg);
        // cps.toastmessage(msg,activityContext);                // breaks unittests, not mocked
        return isSidepot_remaining;
    }

}
