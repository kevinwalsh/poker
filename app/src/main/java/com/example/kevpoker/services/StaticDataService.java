package com.example.kevpoker.services;

import android.app.Application;
import android.content.Context;

import com.example.kevpoker.R;

public class StaticDataService extends Application {

    private static Context mContext;

   /* @Override
    public void OnCreate(){
        super.onCreate();
        mContext = this;
    }*/
   public StaticDataService(){
       mContext = this;
   }

   // public static int colors[]={Color.RED, Color.BLUE,Color.GREEN,Color.MAGENTA,Color.CYAN,Color.parseColor("#ff8c00")};
    //public static int colors[]=

    public static int[] getPlayerColorsFromResFile(){
                        // prob not gonna work from here, might be messy to implement a context
        return mContext.getResources().getIntArray(R.array.playerColors);

    }
}
