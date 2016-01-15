package com.example.vntcaro.memocard.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.example.vntcaro.memocard.Model.Card;
import com.example.vntcaro.memocard.R;
import com.example.vntcaro.memocard.Utils.FileManager;
import com.example.vntcaro.memocard.View.Fragment.CardViewFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vntcaro on 30/10/2015.
 */

/**This class handle the fragments to show card for study**/
public class StudyCardsView extends AppCompatActivity {
    private String SAVE_RONG_CARDS = "RONG_CARDS";
    public static long mDeck_id = -1;
    private List<Card> mListCards;
    private ViewGroup mCardContainer;
    private CardViewFragment mCardFragment;
    private ImageButton mBtnOk;
    private ImageButton mBtnErr;
    private int mCardNumber = 0; //Counter of actual card
    private int mNumCards=0; //Number of cards in this study session
    public static int counters[]; //Counters, number of cards in the deck, number of righ answears,and rongs;
    private List<Integer> mListRongs = new ArrayList<Integer>();
    private String mFILENAME = "PREFERENCES_FILE";
    private boolean isRong=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_cards);
        Intent intent = getIntent();
        initComps(intent);
        showCardFragment();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initComps(Intent intent) {
        Bundle bund = intent.getExtras();
        if (bund != null && bund.getInt("ISALLREVIEW") != 1 && bund.getInt("ISRONGREVIEW") != 1) {
            mDeck_id = bund.getLong("DECK_ID");
        }
        mListCards = Card.getAll(mDeck_id);
        mCardContainer = (ViewGroup) findViewById(R.id.fragment_container);
        mBtnOk = (ImageButton) findViewById(R.id.ok_response);
        mBtnErr = (ImageButton) findViewById(R.id.error_response);
        counters = new int[3];
        counters[0] = mListCards.size();
        if (bund != null && bund.getInt("ISRONGREVIEW") == 1) {
            isRongReview();
            counters[0]= mListRongs.size();
        }
        mNumCards=counters[0];
    }

    /**
     * This Function it's called when this activity it's used to review only rong cards answers from last study
     * */
    private void isRongReview() {
        File file = new File(this.getFilesDir(), mFILENAME);
        String textNumbers = FileManager.readFromFile(file);
        String[] raw = textNumbers.split("[,]");
        for (int i = 0; i < raw.length; i++) {
            mListRongs.add(Integer.parseInt(raw[i]));
        }
        isRong=true;
    }

    /**This function sets the fragments to show the cards**/
    private void showCardFragment() {
        if(mCardNumber < mNumCards) {
            mCardFragment = new CardViewFragment();
            Bundle bunCard = new Bundle();
            if (isRong) {            //Test if is a rong review and if the actul mCardNumber it's on rong cards list
                while (!mListRongs.contains(mCardNumber)) {
                    mCardNumber++;
                }
            }
            bunCard.putString("FRONT", mListCards.get(mCardNumber).front);
            bunCard.putString("BACK", mListCards.get(mCardNumber).back);
            mCardFragment.setArguments(bunCard);
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, mCardFragment).commit();
            showContainer();
        }
    }


    /**This function animate the enter of cardcontainer, it's called by  ShowCardFragment**/
    private void showContainer() {
        Animation enterAnim = AnimationUtils.loadAnimation(this, R.anim.enter_slide_down);
        mCardContainer.startAnimation(enterAnim);
    }


    /**This class show the back card when see_back_button is pressed**/
    public void showBackCard(View v) {
        View backText = mCardContainer.findViewById(R.id.back_container);
        ImageButton viewBut = (ImageButton) mCardContainer.findViewById(R.id.see_back_button);
        View line = mCardContainer.findViewById(R.id.line_text);
        backText.setVisibility(View.VISIBLE);
        viewBut.setVisibility(View.GONE);
        line.setVisibility(View.VISIBLE);
        showResponseButtons();
    }

    /**This class animate the show of buttons for respond if the user hit the card;
     * This function its called by showBackCard()
     * Now it's using animate but it may be better to switch to transition scenes too**/
    private void showResponseButtons() {
        if (mCardContainer != null) {
            mBtnOk.setAlpha(0f);
            mBtnOk.setVisibility(View.VISIBLE);
            mBtnOk.animate().alpha(1f)
                    .setDuration(500)
                    .start();
            mBtnErr.setAlpha(0f);
            mBtnErr.setVisibility(View.VISIBLE);
            mBtnErr.animate().alpha(1f)
                    .setDuration(500)
                    .start();
        }
        //Circular reveal
//        int cx = (mBtnOk.getLeft() + mBtnOk.getRight()) / 2; //center x
//        int cy = (mBtnOk.getTop() + mBtnOk.getBottom()) / 2; //center y
//        int finalRadius = Math.max(mBtnOk.getWidth(), mBtnOk.getHeight()); //max radius
//        Animator anim =
//                ViewAnimationUtils.createCircularReveal(mBtnOk, cx, cy, 0, finalRadius);
//        mBtnOk.setVisibility(View.VISIBLE);
//        anim.start();


    }

    /**This function clean the fragment after a button of a response was pressed */
    private void resetFragment() {
        mBtnOk.setVisibility(View.INVISIBLE);
        mBtnErr.setVisibility(View.INVISIBLE);
        hideContainer();
    }

    /**This function animate the exit of cardcontainer, it's called by  resetFragment
     * in the end of animation call showCardFragment too show a new fragment**/
    private void hideContainer() {
        Animation exitAnim = AnimationUtils.loadAnimation(this, R.anim.exit_slide_down);
        exitAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mCardFragment.hideField();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                showCardFragment();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mCardContainer.startAnimation(exitAnim);
    }

    /**This function clean the fragment after error response button was pressed
     * Clean the fragment and change to another with next Card, by incrementing CardNumber
     */
    public void onErroClick(View v) {
        mListRongs.add(mCardNumber);
        mCardNumber++;
        resetFragment();
        counters[0]--;
        counters[2]++;
        if (counters[0] <= 0) {
            saveRongCards();
            callResult();
        }
    }

    /**This function clean the fragment after ok response button was pressed
     * Clean the fragment and change to another with next Card, by incrementing CardNumber
     */
    public void onOkClick(View v) {
        mCardNumber++;
        resetFragment();
        counters[0]--;
        counters[1]++;
        if (counters[0] <= 0) {
            saveRongCards();
            callResult();
        }
    }

    /**
     * This function save the list of indexes of rong cards answears
     **/
    private void saveRongCards() {
        if(!mListRongs.isEmpty()) {
            String textNumbers = new String();
            for (int element : mListRongs) {
                textNumbers += element + ",";
            }
            File file = new File(this.getFilesDir(), mFILENAME);
            FileManager.writeToFile(file, textNumbers);
        }
    }

    /**
     * This function create a new Fragment of results
     */
    private void callResult() {
        ResultsFragment newResult = new ResultsFragment();
        newResult.show(getFragmentManager(), "Show Result");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_OK);
        finish();
    }

}
