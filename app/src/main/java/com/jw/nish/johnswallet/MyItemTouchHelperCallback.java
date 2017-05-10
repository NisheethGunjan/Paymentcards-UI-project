package com.jw.nish.johnswallet;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Nisheeth on 2017-04-25.
 */

public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private final MyItemTouchAdapter myAdapter;
    public MyItemTouchHelperCallback(MyItemTouchAdapter adapter) {
        myAdapter = adapter;
    }
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {


            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags,swipeFlags);

    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {

    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        myAdapter.onMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }
}
