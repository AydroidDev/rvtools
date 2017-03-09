package com.olmur.recyclerviewtools;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.olmur.rvtools.BaseRecyclerAdapter;
import com.olmur.rvtools.GestureUtils;
import com.olmur.rvtools.SwipeContextMenuDrawer;
import com.olmur.rvtools.property.ISwipeContextMenuProvider;
import com.olmur.rvtools.property.IViewHolderSelector;
import com.olmur.rvtools.property.IOnOrderChangedListener;

import java.util.Arrays;

public class MainAdapter extends BaseRecyclerAdapter<MainEntity, MainAdapter.ViewHolder> implements IOnOrderChangedListener {
    private static final String TAG = "MainAdapter";

    private SwipeContextMenuDrawer mDrawer1;
    private SwipeContextMenuDrawer mDrawer2;

    MainAdapter(@NonNull Context context, MainEntity[] data) {
        super(context);
        mAdapterContent.addAll(Arrays.asList(data));
        mDrawer1 = new SwipeMenuDrawer1();
        mDrawer2 = new BackgroundDrawer2();
    }

    public class ViewHolder extends BaseRecyclerAdapter.BaseViewHolder<MainEntity> implements IViewHolderSelector, ISwipeContextMenuProvider {

        private TextView mTitleTv;

        ViewHolder(@NonNull View view) {
            super(view);
            mTitleTv = (TextView) view.findViewById(R.id.title_tv);
        }

        // Encapsulate your view holder
        @Override
        public void bindView(MainEntity element) {
            mTitleTv.setText(element.getTitle());
        }

        // Define view behaviour on item select
        @Override
        public void onSelected() {
            itemView.setBackgroundColor(Color.BLUE);
            mTitleTv.setTextColor(Color.WHITE);
        }

        // Define view behaviour on item deselect
        @Override
        public void onReleased() {
            itemView.setBackgroundColor(Color.WHITE);
            mTitleTv.setTextColor(Color.GRAY);
        }

        // Provide custom background drawer for current view holder
        @Override
        public SwipeContextMenuDrawer getSwipeMenuDrawer() {
            return mAdapterContent.get(getAdapterPosition()).isSwipeFlag() ? mDrawer1 : mDrawer2;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.view_main_entity, parent, false));
    }

    public void doSomething(int position) {
        mAdapterContent.get(position).changeSwipeFlag();
        notifyItemChanged(position);
    }

    public void move(int from, int to) {
        GestureUtils.move(mAdapterContent, from, to);
        notifyItemMoved(from, to);
    }

    @Override
    public void onOrderChanged() {
        Log.d(TAG, "onOrderChanged: ");
    }
}
