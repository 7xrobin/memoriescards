package com.example.vntcaro.memocard.View;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
    public static int RETURN_ADDCARD ='1';
    private long mDeck_ID;
    private static ImageView mCover;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Card> mListCards;
    private static CardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deck_activity);
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null) {
            mDeck_ID = bundle.getLong(MainActivity.DECK_ID);
            if (mDeck_ID > 0) {
                initComps();
            }
        }
    }
    /**This function set the cover of the deck view, title and list of memories cards**/
    private void initComps(){
        mCover= (ImageView) findViewById(R.id.deck_header);
        Drawable myDrawable = ContextCompat.getDrawable(this, R.drawable.japan);
        mCover.setImageDrawable(myDrawable);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_cards_view);
        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mListCards = Card.getAll();
        mAdapter= new CardAdapter(mListCards);
        mRecyclerView.setAdapter(mAdapter);

    }
    /**This function call AddCardActivity when the fab_add_card is pressed**/
    public void addCard(View view){
        Intent intent = new Intent(this, AddCardActivity.class);
        intent.putExtra(MainActivity.DECK_ID, mDeck_ID);
        startActivityForResult(intent, RETURN_ADDCARD);
    }
    /**This Function it's called by AddCardActivity when a new card it's saved in database**/
    public static void onAddCard(Card newCard){
        mAdapter.addItem(newCard);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_OK) {
            if(resultCode == 1){
                mAdapter.notifyDataSetChanged();
            }
        }

    }
}
