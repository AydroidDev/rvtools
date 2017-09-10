package com.olmur.recyclerviewtools.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.olmur.recyclerviewtools.entities.MyEntity;
import com.olmur.recyclerviewtools.R;
import com.olmur.rvtools.adapter.RvtRecycleAdapter;
import com.olmur.rvtools.property.ViewHolderSelector;
import com.olmur.rvtools.utils.RvtUtils;

public class MyViewHolder extends RvtRecycleAdapter.RvtViewHolder<MyEntity> implements ViewHolderSelector {

    private TextView titleTv;

    MyViewHolder(@NonNull View view) {
        super(view);
        titleTv = view.findViewById(R.id.title_tv);
    }

    @Override
    public void bindViewHolder(MyEntity element) {
        titleTv.setText(element.getTitle());
    }

    // Define view behaviour on item select.
    @Override
    public void onSelected() {
        super.onSelected();
        itemView.setBackgroundColor(Color.GRAY);
        titleTv.setTextColor(Color.WHITE);
    }

    // Define view behaviour on item deselect.
    @Override
    public void onReleased() {
        super.onReleased();
        itemView.setBackgroundColor(Color.WHITE);
        titleTv.setTextColor(Color.GRAY);
    }

    // Provide selected vh height for getting elevation increase/decrease when vh is being selected.
    // API 21 and above only.
    @Override
    public int selectedVhHeightDp() {
        return RvtUtils.Resources.dpToPixels(itemView.getContext(), 8);
    }
}