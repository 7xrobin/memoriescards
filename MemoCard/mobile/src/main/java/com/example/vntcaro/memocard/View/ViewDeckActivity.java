package com.example.vntcaro.memocard.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.vntcaro.memocard.MainActivity;
import com.example.vntcaro.memocard.Model.Card;
import com.example.vntcaro.memocard.R;
import com.example.vntcaro.memocard.View.Adaper.CardAdapter;

import java.util.List;

/**
 * Created by vntcaro on 16/10/2015.
 */
public class ViewDeckActivity extends AppCompatActivity  {
    private static final String TAG ="VIEWDECK" ;
    private static final String RETURN_VIEWDECK = "NUMBERCARDS";
    public static int RETURN_ADDCARD ='1';
    public static long mDeck_ID;
    private static ImageView mCover;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Card> mListCards;
    private static CardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deck_view);
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null) {
            mDeck_ID = bundle.getLong(MainActivity.DECK_ID);
            if (mDeck_ID > 0) {
                initComps();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**This function save in shared preferences the actual deck_id*/
    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putString("deck_id", String.valueOf(mDeck_ID)).commit();
    }

    /**
     * This function guets the deck_id when is start, it's used when back from StudyCards Activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String value = prefs.getString("deck_id", "null");
        if(!value.equals("null")){
            mDeck_ID= Long.valueOf(value);
            initComps();
        }
    }

    /**This function set the cover of the deck view, title and list of memories cards**/
    private void initComps(){
        mCover= (ImageView) findViewById(R.id.deck_image_header);
        Drawable myDrawable = ContextCompat.getDrawable(this, R.drawable.japan);
        mCover.setImageDrawable(myDrawable);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_cards_view);
        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mListCards = Card.getAll(mDeck_ID);
        mAdapter= new CardAdapter(mListCards);
        mRecyclerView.setAdapter(mAdapter);

    }
    /**This function call AddCardDialog when the fab_add_card is pressed**/
    public void addCard(View view){
        AddCardDialog newDialog = new AddCardDialog();
        newDialog.show(getFragmentManager(), "Add Card Dialog");
    }

    /**This Function it's called by AddCardDialog when a new card it's saved in database**/
    public static void onAddCard(Card newCard){
        mAdapter.addItem(newCard);
    }

    /**This Function it's called when  studyButton it's pressed and call cardsView**/
    public void startStudy(View v){
        Intent intent = new Intent(this, StudyCardsView.class);
        intent.putExtra("DECK_ID", mDeck_ID);
        startActivityForResult(intent, 1);
    }

}
