package com.example.vntcaro.memocard;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.vntcaro.memocard.Model.Deck;
import com.example.vntcaro.memocard.View.Adaper.DeckAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        AddDeckFragmentDiolog.NoticeDialogListener{

    private boolean mResolvingError = false;
    private static final String TAG = "MAIN MOBILE";
    private RecyclerView mRecyclerView;
    private DeckAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<Deck> mDeck;
    public static final int REQUEST_ADD_DECK = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_decks_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Inicializate he list of decks saved in database
        mDeck= (List<Deck>)Deck.getAll();
        //specify a adapter
        mAdapter =new DeckAdapter(mDeck);
        mRecyclerView.setAdapter(mAdapter);

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
        AddDeckFragmentDiolog  newDialog = new AddDeckFragmentDiolog();
        newDialog.show(getFragmentManager(),"Add Deck Dialog");
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
        mAdapter.notifyItemInserted(endPos+1);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
