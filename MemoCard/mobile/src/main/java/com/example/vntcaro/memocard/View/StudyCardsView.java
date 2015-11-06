package com.example.vntcaro.memocard.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.example.vntcaro.memocard.Model.Card;
import com.example.vntcaro.memocard.R;
import com.example.vntcaro.memocard.View.Fragments.CardViewFragment;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.List;

/**
 * Created by vntcaro on 30/10/2015.
 */

/**This class handle the fragments to show card for study**/
public class StudyCardsView extends FragmentActivity {
    private long mDeck_id=-1;
    private List<Card> mListCards;
    private View mCardContainer;
    private View mbackContainer;

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
        mCardContainer = findViewById(R.id.fragment_container);
    }

    /**This function sets the fragments to show the cards**/
    private void showCardsFragment(){
        /**Check that the activity is using the layout  */
        if(mCardContainer!=null){
            CardViewFragment FrontCardFragment = new CardViewFragment();
            CardViewFragment BackCardFragment = new CardViewFragment();
            Bundle bunCard = new Bundle();
            bunCard.putString("FRONT",mListCards.get(0).front);  //Test 1
            FrontCardFragment.setArguments(bunCard);
            bunCard.putString("BACK", mListCards.get(0).back);
            BackCardFragment.setArguments(bunCard);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, FrontCardFragment).commit();


        }
    }
    /**This class show the back card when see_back_button is pressed*/
    public void showBackCard(View v){
        if(mCardContainer!=null) {
            View mbackContainer = mCardContainer.findViewById(R.id.back_container);
            View showButton = mCardContainer.findViewById(R.id.see_back_button);
            showButton.setVisibility(View.INVISIBLE);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    Glider.glide(Skill.QuadEaseOut, 3000, ObjectAnimator.ofFloat(mbackContainer, "translationY", -mCardContainer.getHeight(), mCardContainer.getHeight()))
            );
            mbackContainer.setVisibility(View.VISIBLE);
            set.setDuration(3000);
            set.start();
        }
    }
}
