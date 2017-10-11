package com.olmur.recyclerviewtools.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.olmur.recyclerviewtools.R;
import com.olmur.recyclerviewtools.entities.MyEntity;
import com.olmur.rvtools.adapter.RvtViewHolder;
import com.olmur.rvtools.property.ViewHolderSelector;
import com.olmur.rvtools.utils.RvtUtils;

public class MyViewHolder extends RvtViewHolder<MyEntity> implements ViewHolderSelector {

    public static final int ON_BUTTON_CLICKED = 0;

    private TextView titleTv;
    private Button clickB;

    MyViewHolder(@NonNull View view) {
        super(view);
        titleTv = view.findViewById(R.id.title_tv);
        clickB = view.findViewById(R.id.click_b);
    }

    @Override
    public void bindViewHolder(MyEntity element) {
        titleTv.setText(element.getTitle());

        clickB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegateEvent(ON_BUTTON_CLICKED);
            }
        });
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