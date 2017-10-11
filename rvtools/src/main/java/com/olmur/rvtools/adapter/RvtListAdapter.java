package com.olmur.rvtools.adapter;


import com.olmur.rvtools.utils.RvtUtils;

import java.util.List;

public abstract class RvtListAdapter<E, VH extends RvtViewHolder<E>> extends RvtAdapter<E, List<E>, VH> {

    @Override
    public E getItem(int position) {
        return adapterItems.get(position);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        super.onMove(fromPosition, toPosition);
        RvtUtils.Gestures.move(adapterItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }
}
