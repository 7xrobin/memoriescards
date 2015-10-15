package com.example.vntcaro.memocard;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vntcaro.memocard.Model.Deck;

/**
 * Created by vntcaro on 09/10/2015.
 */
public class AddDeckFragmentDiolog extends DialogFragment {
    private static View mDialogView;
    private EditText mNameEdit;
    private EditText mDescriptionEdit;
    /**Interface for listener that communicate whit the host activity**/
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(Deck deck);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initComps();
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mDialogView);
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Deck deck = saveDeck();
                mListener.onDialogPositiveClick(deck);
            }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        mListener.onDialogNegativeClick(AddDeckFragmentDiolog.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    /**
     * inicializate components**/
    private void initComps(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mDialogView= inflater.inflate(R.layout.dialog_add_deck, null);
        mNameEdit =  (EditText) mDialogView.findViewById(R.id.add_deck_title);
        mDescriptionEdit=  (EditText)mDialogView.findViewById(R.id.add_deck_description);
    }
    /***
     * Save deck in DataBase
     * **/
    public Deck saveDeck(){
        String deckName = mNameEdit.getText().toString();
        String description = mDescriptionEdit.getText().toString();
        Deck deck = new Deck(deckName,description);
        deck.save();
        //toast confirmation
        Context context = getActivity();
        CharSequence text = "Deck Salvo Id N°: "+ deck.getId().toString();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        return deck;

    }

}
