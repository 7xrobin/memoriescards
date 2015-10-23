package com.example.vntcaro.memocard.View.Adaper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vntcaro.memocard.Model.Card;
import com.example.vntcaro.memocard.R;

import java.util.List;

/**
 * Created by vntcaro on 23/10/2015.
 */

public class CardAdapter  extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    public List<Card> mListCards;

    /**Constructor of DeckAdapter**/
    public CardAdapter (List<Card> list ){
        mListCards = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_list_view,parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Card itemCard = mListCards.get(position);
        holder.mNameView.setText(itemCard.front);
    }

    public long getItemId(int position) {
        return mListCards.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameView;

        public ViewHolder(View itemView) {
            super(itemView);
            mNameView = (TextView) itemView.findViewById(R.id.card_front_list);
        }
    }
}
