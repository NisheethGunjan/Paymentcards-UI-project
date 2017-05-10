package com.jw.nish.johnswallet;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Nisheeth on 2017-04-25.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> implements MyItemTouchAdapter {

    private List<CardInfo> cardList;
    private final MyItemDragListener myListener;

    public CardAdapter(List<CardInfo> cardList, MyItemDragListener myDragListener) {
        this.cardList = cardList;
        myListener = myDragListener;

    }
    public void updateList(List<CardInfo> cardList)
    {
        this.cardList=cardList;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, int i) {
        CardInfo ci = cardList.get(i);
        cardViewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_MOVE) {
                    myListener.onDragStart(cardViewHolder);
                }
                return false;
            }
        });
        cardViewHolder.vLabel.setText(ci.label);
        cardViewHolder.vBalance.setText(ci.balance);
        cardViewHolder.vAvailCredit.setText(ci.available_credit);
        cardViewHolder.vCardNum.setText(ci.card_num);
        if(!(cardViewHolder.vLabel.getText().equals("Credit Card"))) {
            cardViewHolder.vAvailCredit.setVisibility(View.GONE);
        }


    }
    @Override
    public void onDismiss(int position) {
        cardList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(cardList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(cardList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.activity_tab_layout, viewGroup, false);

        return new CardViewHolder(itemView);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder  {

        protected TextView vLabel;
        protected TextView vBalance;
        protected TextView vAvailCredit;
        protected TextView vCardNum;



        public CardViewHolder(View v) {
            super(v);

            vLabel = (TextView) v.findViewById(R.id.label);
            vBalance = (TextView) v.findViewById(R.id.balance);
            vAvailCredit = (TextView) v.findViewById(R.id.availcredit);
            vCardNum = (TextView) v.findViewById(R.id.cardnum);


        }

    }
}