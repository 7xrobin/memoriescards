package com.example.vntcaro.memocard.View;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.vntcaro.memocard.MainActivity;
import com.example.vntcaro.memocard.R;

/**
 * Created by vntcaro on 16/10/2015.
 */
public class ViewDeckActivity extends AppCompatActivity {
    private static final String TAG ="VIEWDECK" ;
    private long mDeck_ID;
    private static ImageView mCover;

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
    /**This function set the cover of the deck view, title and list of momories cards**/
    private void initComps(){
        mCover= (ImageView) findViewById(R.id.deck_header);
        Drawable myDrawable = ContextCompat.getDrawable(this, R.drawable.japan);
        mCover.setImageDrawable(myDrawable);
    }
    /**This function call AddCardActivity when the fab_add_card is pressed**/
    public void addCard(View view){
        Intent intent = new Intent(this, AddCardActivity.class);
        intent.putExtra(MainActivity.DECK_ID, mDeck_ID);
        startActivity(intent);
    }
}
