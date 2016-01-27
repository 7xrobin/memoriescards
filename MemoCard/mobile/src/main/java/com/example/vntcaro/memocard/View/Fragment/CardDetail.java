package com.example.vntcaro.memocard.View.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.vntcaro.memocard.Model.Card;
import com.example.vntcaro.memocard.R;
import com.example.vntcaro.memocard.View.ViewDeckActivity;

/**
 * Created by vntcaro on 15/01/2016.
 */
public class CardDetail extends DialogFragment {
    private EditText mFront;
    private EditText mBack;
    private View mDialogView;
    private Card mActualCard;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        initComps();
        builder.setView(mDialogView);
        builder.setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveEdits();
            }
        });

        builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new DeleteDataBaseTask().execute(ViewDeckActivity.mActualIdCard);
            }
        });
        return builder.create();
    }

    private void saveEdits() {
        mActualCard.front= mFront.getText().toString();
        mActualCard.back= mBack.getText().toString();
        mActualCard.save();
        ViewDeckActivity.onAddCard(mActualCard);
    }

    private void initComps(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mDialogView =  inflater.inflate(R.layout.card_detail,null);
        mFront = (EditText) mDialogView.findViewById(R.id.front_card_detail);
        mBack = (EditText) mDialogView.findViewById(R.id.back_card_detail);
        mActualCard= Card.getCard(ViewDeckActivity.mActualIdCard);
        mFront.setText(mActualCard.front);
        mBack.setText(mActualCard.back);
    }

    private class DeleteDataBaseTask extends AsyncTask<Long, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Long... cardId) {
            Card.deleteCard(cardId[0]);
            return true;
        }

        protected void onPostExecute(Boolean result){
            ViewDeckActivity.onDeleteCard();
        }

    }
}


