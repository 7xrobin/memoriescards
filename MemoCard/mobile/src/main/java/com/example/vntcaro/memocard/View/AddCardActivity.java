package com.example.vntcaro.memocard.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.vntcaro.memocard.MainActivity;
import com.example.vntcaro.memocard.Model.Card;
import com.example.vntcaro.memocard.Model.Deck;
import com.example.vntcaro.memocard.R;
import com.example.vntcaro.memocard.Utils.FeedBack;

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


    /** This function it's called when the activity is started and get the mDeck_id from the intent sended by ViewDeckActivity */
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

    @Override
    protected void onStop(){
        super.onStop();
    }
    /**
     * Inicializate the edit fields and Listner
     * **/
    private void initComps(){
        mDeck= Deck.getDeck(mDeck_ID);
        mFrontEdit =  (EditText) findViewById(R.id.front_description);
        mBackEdit =  (EditText) findViewById(R.id.back_description);
        mHintEdit =  (EditText) findViewById(R.id.hint_description);
    }

    /**This function save a new card with te informations are inserted in the activity, and it is called when he button saved is pressed**/
    public void saveCard(View view){
        String frontCard = mFrontEdit.getText().toString();
        String backCard = mBackEdit.getText().toString();
        String hintCard = mHintEdit.getText().toString();
        if(validateFields(frontCard, backCard)) {
            Card card = new Card(mDeck, frontCard, backCard, hintCard);
            card.save();
            clearFields();
            ViewDeckActivity.onAddCard(card);
            FeedBack.showFeedBack(getApplicationContext(),FeedBack.SAVE_OK);
        }
    }
    /**This function notify the deck view activity that addition of new Cards it was canceled  */
    public void cancelCard(View v){
        Intent returnIntent= new Intent();
        setResult(RESULT_OK,returnIntent);
        finish();
    }
    /**This function clear all edit fields when a card is saved to provide save another card**/
    private void clearFields(){
        mFrontEdit.setText("");
        mBackEdit.setText("");
        mHintEdit.setText("");
    }
    /**This function verify if he fields was correcly settled **/
    private boolean validateFields(String frontCard, String backCard){
        if(frontCard.equals("")){
            FeedBack.showFeedBack(getApplicationContext(), FeedBack.FRONT_ERROR);
            return false;
        }
        if(backCard.equals("")){
            FeedBack.showFeedBack(getApplicationContext(), FeedBack.BACK_ERROR);
            return false;
        }
        return true;
    }
}
