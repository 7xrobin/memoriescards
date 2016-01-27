package com.example.vntcaro.memocard.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.vntcaro.memocard.MainActivity;
import com.example.vntcaro.memocard.Model.Card;
import com.example.vntcaro.memocard.R;
import com.example.vntcaro.memocard.Utils.RecyclerItemClickListener;
import com.example.vntcaro.memocard.View.Adaper.CardAdapter;
import com.example.vntcaro.memocard.View.Fragment.AddCardDialog;
import com.example.vntcaro.memocard.View.Fragment.CardDetail;

import java.util.List;

/**
 * Created by vntcaro on 16/10/2015.
 */
public class ViewDeckActivity extends AppCompatActivity  {
    private static final String TAG ="VIEWDECK" ;
    private static final String RETURN_VIEWDECK = "NUMBERCARDS";
    private static final int START_STUDY = 1;
    private boolean mIsStart= false;
    public static long mDeck_ID;
    private static ImageView mCover;
    private static RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static List<Card> mListCards;
    private static CardAdapter mAdapter;
    private int mActualPositionCard;
    public static long mActualIdCard;

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
                mIsStart= true;
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
        if(!mIsStart){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String value = prefs.getString("deck_id", "null");
            if(!value.equals("null")){
                mDeck_ID= Long.valueOf(value);
                initComps();
                mIsStart= false;
            }
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
        final Context context = getApplicationContext();
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View vi, int position) {
                        viewCardDetail(vi, position);
                    }
                })
        );

    }

    private void viewCardDetail(View vi, int position) {
        mActualPositionCard= position;
        mActualIdCard= mAdapter.getItemId(position);
        CardDetail card = new CardDetail();
        card.show(getFragmentManager(), "Card Detail");
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

    /**This Function it's called by AddCardDialog when a new card it's saved in database**/
    public static void onEditCard(){
        mAdapter.notifyItemChanged(((int)mActualIdCard));
    }

    /**This Function it's called by CardDetail when a card it's deleted on database
     * this function refresh the list of cards**/
    public static void onDeleteCard(){
        mListCards = Card.getAll(mDeck_ID);
        mAdapter= new CardAdapter(mListCards);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    /**This Function it's called when  studyButton it's pressed and call cardsView**/
    public void startStudy(View v){
        Intent intent = new Intent(this, StudyCardsView.class);
        intent.putExtra("DECK_ID", mDeck_ID);
        startActivityForResult(intent,START_STUDY);
    }

    /**This function it's called when back from Study Activity**/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == START_STUDY) {
            if (resultCode == RESULT_OK){
                Log.d(TAG,"Chamou de Volta");
            }
        }
    }

}
