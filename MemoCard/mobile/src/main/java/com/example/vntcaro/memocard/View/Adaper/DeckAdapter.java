package com.example.vntcaro.memocard.View.Adaper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vntcaro.memocard.Model.Deck;
import com.example.vntcaro.memocard.R;

import java.util.List;

/**
 * Created by vntcaro on 08/10/2015.
 */
public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.ViewHolder> {
    private List<Deck> mDecklist;

    /**Constructor of DeckAdapter**/
    public DeckAdapter (List<Deck> list ){
        mDecklist = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_card_view,parent, false);
//        itemView.setOnClickListener((View..OnClickListener) itemListener);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Deck itemDeck = mDecklist.get(position);
        holder.mNameView.setText(itemDeck.name);
        holder.mDescriptionView.setText(itemDeck.description);
    }

//    @Override
    public long getItemId(int position) {
        return mDecklist.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mDecklist.size();
    }

    public void addItem(Deck data){
        mDecklist.add(data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mNameView;
        public TextView mDescriptionView;
        private ImageView mImage;

        // Provide a reference to the views for each data item
        public ViewHolder(View v) {
            super(v);
            mImage = (ImageView) v.findViewById(R.id.deck_image);
            mNameView = (TextView) v.findViewById(R.id.deck_title);
            mDescriptionView = (TextView) v.findViewById(R.id.deck_description);
        }
    }


}
