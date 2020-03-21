package com.example.kevpoker.logic;

import com.example.kevpoker.model.Player;

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

}
