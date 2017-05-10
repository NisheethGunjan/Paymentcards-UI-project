package com.jw.nish.johnswallet;

/**
 * Created by Nisheeth on 2017-04-25.
 */

public interface MyItemTouchAdapter {
    boolean onMove(int fromPosition, int toPosition);
    void onDismiss(int position);
}
