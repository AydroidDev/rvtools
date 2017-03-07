package com.olmur.rvtools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<Model, VH extends BaseRecyclerAdapter.BaseViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<Model> mAdapterContent;
    protected Context mContext;

    public BaseRecyclerAdapter(@NonNull Context context) {
        mContext = context;
        mAdapterContent = new ArrayList<>();
    }

    public static abstract class BaseViewHolder<Model> extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View view) {
            super(view);
        }

        public abstract void bindView(Model element);
    }

    @Override
    public int getItemCount() {
        return mAdapterContent.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bindView(mAdapterContent.get(position));
    }
}
