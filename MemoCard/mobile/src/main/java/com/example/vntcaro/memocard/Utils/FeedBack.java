package com.example.vntcaro.memocard.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.example.vntcaro.memocard.R;

/**
 * Created by vntcaro on 22/10/2015.
 */
public class FeedBack {
    public static final int FRONT_ERROR= 1;
    public static final int BACK_ERROR= 2;
    public static final int SAVE_OK= 3;
    public static final int TEST= 4;

    /**This function show toasts too help the user to fill the form card**/
    public static void showFeedBack(Context context,int cod){
        Resources res = context.getResources();
        switch (cod) {
            case FRONT_ERROR:
                Toast.makeText(context, res.getString(R.string.front_error), Toast.LENGTH_SHORT).show();
                break;
            case BACK_ERROR:
                Toast.makeText(context, res.getString(R.string.back_error), Toast.LENGTH_SHORT).show();
                break;
            case SAVE_OK:
                Toast.makeText(context, res.getString(R.string.save_card_ok), Toast.LENGTH_SHORT).show();
                break;
            case TEST:
                Toast.makeText(context,"Olá eu sou um teste", Toast.LENGTH_SHORT).show();
        }
    }
}
