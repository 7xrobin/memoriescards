package com.example.vntcaro.memocard.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.example.vntcaro.memocard.Model.Card;
import com.example.vntcaro.memocard.R;
import com.example.vntcaro.memocard.View.Fragment.CardViewFragment;

import java.util.List;

/**Created by vntcaro on 30/10/2015.*/

/**This class handle the fragments to show card for study**/
public class StudyCardsView extends FragmentActivity {
    private long mDeck_id=-1;
    private  List<Card> mListCards;
    private  ViewGroup mCardContainer;
    private  CardViewFragment mCardFragment;
    private  ImageButton mBtnOk;
    private  ImageButton mBtnErr;
    private  int mCardNumber =0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_cards);
        Intent intent= getIntent();
        initComps(intent);
        showCardFragment();
    }
    private void initComps(Intent intent){
        Bundle bund= intent.getExtras();
        if(bund!=null){
            mDeck_id=bund.getLong("DECK_ID");
        }
        mListCards= Card.getAll(mDeck_id);
        mCardContainer = (ViewGroup)findViewById(R.id.fragment_container);
        mBtnOk = (ImageButton) findViewById(R.id.ok_response);
        mBtnErr = (ImageButton) findViewById(R.id.error_response);
    }

    /**This function sets the fragments to show the cards**/
    private void showCardFragment(){
        if(mCardNumber <mListCards.size()) {
            mCardFragment = new CardViewFragment();
            Bundle bunCard = new Bundle();
            bunCard.putString("FRONT", mListCards.get(mCardNumber).front);
            bunCard.putString("BACK", mListCards.get(mCardNumber).back);
            mCardFragment.setArguments(bunCard);
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, mCardFragment).commit();
            showContainer();
        }
    }


    /**This function animate the enter of cardcontainer, it's called by  ShowCardFragment**/
    private void showContainer(){
        Animation enterAnim = AnimationUtils.loadAnimation(this, R.anim.enter_slide_down);
        mCardContainer.startAnimation(enterAnim);
    }


    /**This class show the back card when see_back_button is pressed**/
    public void showBackCard(View v){
            View backText =  mCardContainer.findViewById(R.id.back_container);
            ImageButton viewBut = (ImageButton) mCardContainer.findViewById(R.id.see_back_button);
            View line= mCardContainer.findViewById(R.id.line_text);
            backText.setVisibility(View.VISIBLE);
            viewBut.setVisibility(View.GONE);
            line.setVisibility(View.VISIBLE);
            showResponseButtons();
    }

    /**This class animate the show of buttons for respond if the user hit the card;
     * This function its called by showBackCard()
     * Now it's using animate but it may be better to switch to transition scenes too**/
    private void showResponseButtons(){
        if(mCardContainer!=null) {
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
//        int cx = (mBtnOk.getLeft() + mBtnOk.getRight()) / 2; //center x
//        int cy = (mBtnOk.getTop() + mBtnOk.getBottom()) / 2; //center y
//        int finalRadius = Math.max(mBtnOk.getWidth(), mBtnOk.getHeight()); //max radius
//        Animator anim =
//                ViewAnimationUtils.createCircularReveal(mBtnOk, cx, cy, 0, finalRadius);
//        mBtnOk.setVisibility(View.VISIBLE);
//        anim.start();



    }

    /**This function clean the fragment after a button of a response was pressed */
    private void resetFragment(){
        mBtnOk.setVisibility(View.INVISIBLE);
        mBtnErr.setVisibility(View.INVISIBLE);
        hideContainer();
    }

    /**This function animate the exit of cardcontainer, it's called by  resetFragment
     * in the end of animation call showCardFragment too show a new fragment**/
    private void hideContainer(){
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
    public void onErroClick(View v){
        mCardNumber++;
        resetFragment();
    }

    /**This function clean the fragment after ok response button was pressed
     * Clean the fragment and change to another with next Card, by incrementing CardNumber
     */
    public void onOkClick(View v){
        mCardNumber++;
        resetFragment();
    }


}
