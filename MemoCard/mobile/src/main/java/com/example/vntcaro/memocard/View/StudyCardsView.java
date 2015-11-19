package com.example.vntcaro.memocard.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vntcaro.memocard.Model.Card;
import com.example.vntcaro.memocard.R;
import com.example.vntcaro.memocard.View.Fragments.CardViewFragment;

import java.util.List;

/**
 * Created by vntcaro on 30/10/2015.
 */

/**This class handle the fragments to show card for study**/
public class StudyCardsView extends FragmentActivity {
    private long mDeck_id=-1;
    private List<Card> mListCards;
    private ViewGroup mCardContainer;
    private  ImageButton mBtnOk;
    private  ImageButton mBtnErr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_cards);
        Intent intent= getIntent();
        initComps(intent);
        showCardsFragment();
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
    private void showCardsFragment(){
        /**Check that the activity is using the layout  */
        if(mCardContainer!=null){
            CardViewFragment CardFragment = new CardViewFragment();
            Bundle bunCard = new Bundle();
            bunCard.putString("FRONT", mListCards.get(0).front);  //Teste para mostrar um card
            CardFragment.setArguments(bunCard);
            bunCard.putString("BACK", mListCards.get(0).back);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, CardFragment).commit();
        }
    }

    /**This class show the back card when see_back_button is pressed**/
    public void showBackCard(View v){
            Scene scene2= Scene.getSceneForLayout(mCardContainer, R.layout.card_fragment_2, this);
            scene2.setEnterAction(new Runnable() {
                @Override
                public void run() {
                    TextView frontText = (TextView) mCardContainer.findViewById(R.id.front_card_study);
                    TextView backText = (TextView) mCardContainer.findViewById(R.id.back_card_study);
                    frontText.setText(mListCards.get(0).front);
                    backText.setText(mListCards.get(0).back);
                }
            });
            Transition showTransition = TransitionInflater.from(this)
                    .inflateTransition(R.transition.trans_slide_down);
            TransitionManager.go(scene2, showTransition);
            showResponseButtons();
    }

    /**This class animate the show of buttons for respond if the user hit the card;
     * This function its called by showBackCard()
     * Now it's using animate but it may be better to switch to transition scenes too**/
    public void showResponseButtons(){
        if(mCardContainer!=null) {
            mBtnOk.setAlpha(0f);
            mBtnOk.setVisibility(View.VISIBLE);
            mBtnOk.animate().alpha(1f)
                    .setDuration(500)
                    .start();
            mBtnOk.setAlpha(0f);
            mBtnErr.setVisibility(View.VISIBLE);
            mBtnOk.animate().alpha(1f)
                    .setDuration(500)
                    .start();
        }
    }
}
