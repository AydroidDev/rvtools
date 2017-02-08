package com.olmur.recyclerviewtools;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.olmur.rvtools.BaseRecyclerAdapter;
import com.olmur.rvtools.GestureUtils;
import com.olmur.rvtools.property.IBackgroundDrawer;
import com.olmur.rvtools.property.IBinderViewHolder;
import com.olmur.rvtools.property.IDrawControlViewHolder;
import com.olmur.rvtools.property.IGestureSensitiveViewHolder;
import com.olmur.rvtools.property.ISortableAdapter;

import java.util.Arrays;

public class MainAdapter extends BaseRecyclerAdapter<MainEntity, MainAdapter.ViewHolder> implements ISortableAdapter {

    private IBackgroundDrawer mDrawer1;
    private IBackgroundDrawer mDrawer2;

    MainAdapter(@NonNull Context context, MainEntity[] data) {
        super(context);
        mAdapterContent.addAll(Arrays.asList(data));
        mDrawer1 = new BackgroundDrawer1();
        mDrawer2 = new BackgroundDrawer2();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements IBinderViewHolder<MainEntity>, IGestureSensitiveViewHolder, IDrawControlViewHolder {

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
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.BLUE);
            mTitleTv.setTextColor(Color.WHITE);
        }

        // Define view behaviour on item deselect
        @Override
        public void onItemReleased() {
            itemView.setBackgroundColor(Color.WHITE);
            mTitleTv.setTextColor(Color.GRAY);
        }

        // Provide custom background drawer for current view holder
        @Override
        public IBackgroundDrawer getBackgroundDrawer() {
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
    public void saveSorting() {
        // Save items order
    }
}
