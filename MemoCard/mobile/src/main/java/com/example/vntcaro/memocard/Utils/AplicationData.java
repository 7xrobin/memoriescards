package com.example.vntcaro.memocard.Utils;

import com.activeandroid.ActiveAndroid;

/**
 * Created by vntcaro on 06/10/2015.
 *
 *  * This class is used by Active library to get context of aplication with context of database
 */
public class AplicationData extends com.activeandroid.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
