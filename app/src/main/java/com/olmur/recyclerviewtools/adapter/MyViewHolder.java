package com.olmur.recyclerviewtools.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.olmur.recyclerviewtools.entities.MyEntity;
import com.olmur.recyclerviewtools.R;
import com.olmur.rvtools.adapter.RvtRecycleAdapter;
import com.olmur.rvtools.property.ViewHolderSelector;

public class MyViewHolder extends RvtRecycleAdapter.RvtViewHolder<MyEntity> implements ViewHolderSelector {

        private TextView mTitleTv;

        MyViewHolder(@NonNull View view) {
            super(view);
            mTitleTv = view.findViewById(R.id.title_tv);
        }

        @Override
        public void bindViewHolder(MyEntity element) {
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