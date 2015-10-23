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
//
//    /**Interface for listener that communicate whit the view deck activity
//     * when cancel button is pressed,
//     * **/
//    public interface NoticeAddCardListener{
//        void onCancelClick();
//    }
//    NoticeAddCardListener mListener;
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
     * Inicializate the edit fields and Listner
     * **/
    private void initComps(){
        mDeck= Deck.getDeck(mDeck_ID);
        mFrontEdit =  (EditText) findViewById(R.id.front_description);
        mBackEdit =  (EditText) findViewById(R.id.back_description);
        mHintEdit =  (EditText) findViewById(R.id.hint_description);
//            try {
//                // Instantiate the NoticeDialogListener so we can send events to the host
//                mListener = (NoticeAddCardListener) mDeckAct;
//            } catch (ClassCastException e) {
//                // The activity doesn't implement the interface, throw exception
//                throw new ClassCastException(mDeckAct.toString()
//                        + " must implement NoticeDialogListener");
//            }

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
            FeedBack.showFeedBack(getApplicationContext(),FeedBack.SAVE_OK);
        }
    }
    /**This function notify the deck view activity that addition of new Cards it was canceled  */
    public void cancelCard(View v){
//        mListener.onCancelClick();
        Intent returnIntent= new Intent();
        setResult(1,returnIntent);
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
