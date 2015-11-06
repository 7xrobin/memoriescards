package com.example.vntcaro.memocard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.vntcaro.memocard.Model.Deck;
import com.example.vntcaro.memocard.Utils.RecyclerItemClickListener;
import com.example.vntcaro.memocard.View.Adaper.DeckAdapter;
import com.example.vntcaro.memocard.View.AddDeckFragmentDiolog;
import com.example.vntcaro.memocard.View.ViewDeckActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        AddDeckFragmentDiolog.NoticeDialogListener{

    public static final String DECK_ID = "com.exemple.vntcaro.memocard.DECK_ID";
    private static final int RETURN_VIEWDECK = 1;
    private boolean mResolvingError = false;
    private static final String TAG = "MAINACTIVITY";
    private RecyclerView mRecyclerView;
    private DeckAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<Deck> mDeck;
    public static final int REQUEST_ADD_DECK = 10;
    private int actualPositionDeck =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComps();
    }

    /**This  funstion inicializate RecycleView and his components, furthermore add  TouchListem for Recycleview*/
    public void initComps(){
        mRecyclerView = (RecyclerView) findViewById(R.id.list_decks_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Inicializate he list of decks saved in database
        mDeck= (List<Deck>)Deck.getAll();
        //specify a adapter
        mAdapter = new DeckAdapter(mDeck);
        mRecyclerView.setAdapter(mAdapter);
        final Context context = getApplicationContext();
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context,new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View vi, int position){
                        viewDeck(position);
                    }
                })
        );
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }
    /**
     * This function it's called when fab_add_deck button it's pressed
     * and  function invoques
     * **/
    public void addDeck(View view){
        AddDeckFragmentDiolog newDialog = new AddDeckFragmentDiolog();
        newDialog.show(getFragmentManager(), "Add Deck Dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**In case of save button pressed in AddDeckFragment**/
    @Override
    public void onDialogPositiveClick(Deck deck) {
        int endPos = mAdapter.getItemCount();
        mAdapter.addItem(deck);
        mAdapter.notifyItemInserted(endPos);
    }
    /**Class to call a activity view of a deck that was clicked**/
    public void viewDeck(int position){
        Intent intent = new Intent(this, ViewDeckActivity.class);
        actualPositionDeck= position;
        long deckId = mAdapter.getItemId(position);
        intent.putExtra(DECK_ID, deckId);
        startActivityForResult(intent, RETURN_VIEWDECK);
    }

    /**This function update the the number of card of a deck after ViewDeckActivity was closed*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RETURN_VIEWDECK) {
            if (resultCode == RESULT_OK){
                mAdapter.notifyItemChanged(actualPositionDeck);
                actualPositionDeck=-1;
            }
        }
    }

}
