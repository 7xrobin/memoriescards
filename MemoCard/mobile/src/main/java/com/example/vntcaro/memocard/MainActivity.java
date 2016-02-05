package com.example.vntcaro.memocard;
/**
 * <h1>MemoryCards Program</h1>
 * This application helps users to memorize things through Cards that they input in the app,
 * so they can study reviewing the cards and test yours hits and errors.
 *
 *
 * @author Carlos Robson Alves de Oliveira
 * @version 1.0
 * @since 2015-10-01
 */

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
import com.example.vntcaro.memocard.View.Fragment.AddDeckFragmentDiolog;
import com.example.vntcaro.memocard.View.ViewDeckActivity;

import java.util.List;

/**
 * This is the main class of the app, it shows a List of Deck, menu options, header for tips and description
 * and button to add a new deck;
 * It's extends AppCompatActivity to use function of support library V7 Api 23
 */
public class MainActivity extends AppCompatActivity implements
        AddDeckFragmentDiolog.NoticeDialogListener{
    /**
     * @param DECK_ID                   It's used to Tag the intent that start ViewDeckActivity
     * @param RETURN_VIEWDECK           It's used to check the return of ViewDeckActivity
     * @param mRecyclerView             This is the ViewList that show all the decks of the user
     * @param mAdapter                  This is the instance of DeckAdapter used handle the deck information, and hold in the specif view of recycle view item
     * @param mLayoutManager            This is the Layout used in the RecyclerView
     * @param mDeckList                 It's a list of all deck in the user database
     *
     */
    public static final String DECK_ID = "com.exemple.vntcaro.memocard.DECK_ID";
    private static final int RETURN_VIEWDECK = 1;
    private RecyclerView mRecyclerView;
    private DeckAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<Deck> mDeckList;
    private int actualPositionDeck =-1;

    /**
     * This is used when the activity it's create. Set the layout and call initComps.
     * @param savedInstanceState  Bundle whit android saved state, it's not really used for us
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComps();
    }

    /**This  function initializes RecycleView, mLayoutManager , mDeckList, furthermore add  TouchListem for Recycleview*/
    public void initComps(){
        mRecyclerView = (RecyclerView) findViewById(R.id.list_decks_view);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDeckList = (List<Deck>)Deck.getAll();
        mAdapter = new DeckAdapter(mDeckList);
        mRecyclerView.setAdapter(mAdapter);
        final Context context = getApplicationContext();
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View vi, int position) {
                        viewDeck(vi, position);
                    }
                })
        );
    }

    /**
     * This function it's called when fab_add_deck button it's pressed
     * and  call the AddDeckFragmentDialog to user insert a new deck
     * **/
    public void addDeck(View view){
        AddDeckFragmentDiolog newDialog = new AddDeckFragmentDiolog();
        newDialog.show(getFragmentManager(), "Add Deck Dialog");
    }


    /**
     * This function it's created by default for the AppCompatActivity and generate a main_menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * this function handle the item selected in the main_menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**In case of save button pressed in AddDeckFragment
     * @param deck          The deck returned by The fragment Dialog
     **/
    @Override
    public void onDialogPositiveClick(Deck deck) {
        int endPos = mAdapter.getItemCount();
        mAdapter.addItem(deck);
        mAdapter.notifyItemInserted(endPos);
    }

    /**Class to call a activity view of a deck that was clicked
     * @param position      position passed by the touchlistner of the RecyclerView in the initComps()
     * **/
    public void viewDeck(View vi, int position){
        Intent intent = new Intent(this, ViewDeckActivity.class);
        actualPositionDeck= position;
        long deckId = mAdapter.getItemId(position);
        intent.putExtra(DECK_ID, deckId);


        startActivityForResult(intent, RETURN_VIEWDECK);

    }

    /**This function update the the number of card of a deck after ViewDeckActivity was closed*/
    /**
     * @param requestCode       Used to test if the return of ActivityResult was of ViewDeck
     * @param resultCode        Test is the return was a sucess
     * @param data              It's not used
     */
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
