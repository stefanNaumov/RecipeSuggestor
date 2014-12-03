package com.example.stefan.Activities;

import android.app.Application;

/**
 * Created by Stefan on 12/3/2014.
 */
//class used to set globally the text animation delay
public class ApplicationBase extends Application {
    private long animatorDelay = 30;

    public long getAnimatorDelay(){
        return animatorDelay;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }


}
