package com.example.vntcaro.memocard.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.vntcaro.memocard.MainActivity;
import com.example.vntcaro.memocard.Model.Card;
import com.example.vntcaro.memocard.Model.Deck;
import com.example.vntcaro.memocard.R;

/**
 * Created by vntcaro on 19/10/2015.
 */
public class AddCardActivity extends AppCompatActivity {
    private final String TAG ="CARDADD";
    private long mDeck_ID;
    private Deck mDeck;
    private EditText mFrontEdit;
    private EditText mBackEdit;
    private EditText mHintEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card);
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null) {
            mDeck_ID = bundle.getLong(MainActivity.DECK_ID);
            if (mDeck_ID > 0) {
                initComps();
            }
        }
    }
    /**
     * Inicializate the edit fields
     * **/
    private void initComps(){
        mDeck= Deck.getDeck(mDeck_ID);
        mFrontEdit =  (EditText) findViewById(R.id.front_description);
        mBackEdit =  (EditText) findViewById(R.id.back_description);
        mHintEdit =  (EditText) findViewById(R.id.hint_description);
        Log.v(TAG, mDeck.name);
    }
    /**This function save a new card with te informations are inserted in the activity, and it is called when he button saved is pressed**/
    public void saveCard(View view){
        String cardFront = mFrontEdit.getText().toString();
        String cardBack = mBackEdit.getText().toString();
        String cardHint = mHintEdit.getText().toString();
        Card card = new Card(mDeck,cardFront,cardBack, cardHint);
        card.save();
    }
}
