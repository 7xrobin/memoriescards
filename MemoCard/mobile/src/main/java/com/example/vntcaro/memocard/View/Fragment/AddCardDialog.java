package com.example.vntcaro.memocard.View.Fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vntcaro.memocard.Model.Card;
import com.example.vntcaro.memocard.Model.Deck;
import com.example.vntcaro.memocard.R;
import com.example.vntcaro.memocard.View.ViewDeckActivity;

/**
 * Created by vntcaro on 19/10/2015.
 */
public class AddCardDialog extends DialogFragment {
    private final String TAG ="CARDADD";
    private Deck mDeck;
    private View mDialogView;
    private static EditText mFrontEdit;
    private static EditText mBackEdit;
    private static EditText mHintEdit;


    /** This function it's called when the activity is started and get the mDeck_id from the intent sended by ViewDeckActivity */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (ViewDeckActivity.mDeck_ID > 0) {
            initComps();
        }
        builder.setView(mDialogView);
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }
    /**This function prevent that the dialog close after save button pressed**/
    @Override
    public void onStart(){
        super.onStart();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Boolean wantToCloseDialog = false;
                    saveCard();
                    if(wantToCloseDialog)
                        d.dismiss();
                    //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                }
            });
        }
    }
    /**
     * Inicializate the edit fields, and the DialogVIew
     * **/
    private void initComps(){
        mDeck= Deck.getDeck(ViewDeckActivity.mDeck_ID);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mDialogView= inflater.inflate(R.layout.add_card_dialog,null);
        mFrontEdit =  (EditText) mDialogView.findViewById(R.id.front_description);
        mBackEdit =  (EditText) mDialogView.findViewById(R.id.back_description);
        mHintEdit =  (EditText) mDialogView.findViewById(R.id.hint_description);
    }

    /**This function save a new card with te informations are inserted in the dialog**/
    public void saveCard(){
        String frontCard = mFrontEdit.getText().toString();
        String backCard = mBackEdit.getText().toString();
        String hintCard = mHintEdit.getText().toString();
        if(validateFields(frontCard, backCard)) {
            Card card = new Card(mDeck, frontCard, backCard, hintCard);
            card.save();
            clearFields();
//            FeedBack.showFeedBack(this.getContext(), FeedBack.SAVE_OK);
            ViewDeckActivity.onAddCard(card);
        }
    }



    /**This function clear all edit fields when a card is saved to provide save another card**/
    private void clearFields(){
        mFrontEdit.setText("");
        mFrontEdit.requestFocus();
        mBackEdit.setText("");
        mHintEdit.setText("");
    }

    /**This function verify if he fields was correcly settled **/
    private boolean validateFields(String frontCard, String backCard){
        if(frontCard.equals("")){
            mFrontEdit.requestFocus();
            mFrontEdit.setFocusable(true);
            mFrontEdit.setHint(R.string.front_null_insert);
            mFrontEdit.setHintTextColor(R.color.mRed);
            return false;
        }
        if(backCard.equals("")){
            mBackEdit.requestFocus();
            mBackEdit.setFocusable(true);
            mBackEdit.setHint(R.string.back_null_insert);
            mBackEdit.setTextColor(R.color.mRed);
            return false;
        }
        return true;
    }
}
