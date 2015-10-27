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
 * Created by vntcaro on 08/10/2015.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardsViewHolder> {
    private List<Card> mCardlist;

    /**Constructor of CardAdapter**/
    public CardAdapter (List<Card> list ){
        mCardlist = list;
    }

    /**This function its used when The RecycleView create a new Holder
     * so this function link the layout whit the holder
     * **/
    @Override
    public CardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_list_view,parent, false);
        return new CardsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardsViewHolder holder, int position) {
        Card itemCard = mCardlist.get(position);
        holder.mNameView.setText(itemCard.front);
    }

    public long getItemId(int position) {
        return mCardlist.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mCardlist.size();
    }

    public void addItem(Card data){
        mCardlist.add(data);
    }

    public static class CardsViewHolder extends RecyclerView.ViewHolder{
        public TextView mNameView;

        // Provide a reference to the views for each data item
        public CardsViewHolder(View v) {
            super(v);
            mNameView = (TextView) v.findViewById(R.id.card_front_list);
        }
    }


}
