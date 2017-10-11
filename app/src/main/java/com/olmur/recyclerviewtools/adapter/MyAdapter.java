package com.olmur.recyclerviewtools.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.olmur.recyclerviewtools.R;
import com.olmur.recyclerviewtools.entities.MyEntity;
import com.olmur.rvtools.adapter.RvtAdapter;
import com.olmur.rvtools.utils.RvtUtils;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RvtAdapter<MyEntity, List<MyEntity>, MyViewHolder> {

    @NonNull
    @Override
    public List<MyEntity> initItemsCollection() {
        return new ArrayList<>();
    }

    @Override
    public MyEntity getItem(int position) {
        return adapterItems.get(position);
    }

    @Override
    public MyViewHolder createViewHolder(int viewType, ViewGroup parent) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_main_entity, parent, false));
    }

    public void addData(List<MyEntity> data) {
        adapterItems.addAll(data);
        notifyDataSetChanged();
    }

    public void changeItem(int position) {
        MyEntity item = getItem(position);
        item.setTitle(item.getTitle() + " - was changed");
        notifyItemChanged(position);
    }

    public void deleteItem(int position) {
        adapterItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        RvtUtils.Gestures.move(adapterItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }
}
