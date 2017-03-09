package com.olmur.recyclerviewtools;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.olmur.rvtools.BaseRecyclerAdapter;
import com.olmur.rvtools.GestureUtils;
import com.olmur.rvtools.property.IOnOrderChangedListener;
import com.olmur.rvtools.property.IViewHolderSelector;

import java.util.Arrays;

class MainAdapter extends BaseRecyclerAdapter<MainEntity, MainAdapter.ViewHolder> implements IOnOrderChangedListener {

    MainAdapter(@NonNull Context context, MainEntity[] data) {
        super(context);
        mAdapterContent.addAll(Arrays.asList(data));
    }

    class ViewHolder extends BaseRecyclerAdapter.BaseViewHolder<MainEntity> implements IViewHolderSelector {

        private TextView mTitleTv;

        ViewHolder(@NonNull View view) {
            super(view);
            mTitleTv = (TextView) view.findViewById(R.id.title_tv);
        }

        @Override
        public void bindView(MainEntity element) {
            mTitleTv.setText(element.getTitle());
        }

        // Define view behaviour on item select
        @Override
        public void onSelected() {
            itemView.setBackgroundColor(Color.GRAY);
            mTitleTv.setTextColor(Color.WHITE);
        }

        // Define view behaviour on item deselect
        @Override
        public void onReleased() {
            itemView.setBackgroundColor(Color.WHITE);
            mTitleTv.setTextColor(Color.GRAY);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.view_main_entity, parent, false));
    }

    void changeItem(int position) {
        MainEntity item = mAdapterContent.get(position);
        item.setTitle(item.getTitle() + " - was changed");
        notifyItemChanged(position);
    }

    void deleteItem(int position) {
        mAdapterContent.remove(position);
        notifyItemRemoved(position);
    }

    void move(int from, int to) {
        GestureUtils.move(mAdapterContent, from, to);
        notifyItemMoved(from, to);
    }

    @Override
    public void onOrderChanged() {
        Toast.makeText(mContext, "Order has been changed", Toast.LENGTH_SHORT).show();
    }
}
