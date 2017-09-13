package com.olmur.rvtools.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.olmur.rvtools.property.OnMoveAction;
import com.olmur.rvtools.property.ViewHolderClickDelegate;
import com.olmur.rvtools.property.ViewHolderLongClickDelegate;

import java.util.Collection;

public abstract class RvtRecycleAdapter<E, C extends Collection<E>, VH extends RvtViewHolder<E>> extends RecyclerView.Adapter<VH>
        implements OnMoveAction {

    private ViewHolderClickDelegate viewHolderClickDelegate;

    private ViewHolderLongClickDelegate viewHolderLongClickDelegate;

    protected C adapterItems;

    public RvtRecycleAdapter() {
        adapterItems = initItemsCollection();
    }

    @Override
    public int getItemCount() {
        return adapterItems.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bindViewHolder(getItem(position));
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH viewHolder = createViewHolder(viewType, parent);
        viewHolder.setRvClickDelegate(viewHolderClickDelegate);
        viewHolder.setRvLongClickDelegate(viewHolderLongClickDelegate);
        return viewHolder;
    }

    public abstract VH createViewHolder(int viewType, ViewGroup parent);

    /**
     * Provide initial items collection for adapter. Could be empty.
     */
    @NonNull
    public abstract C initItemsCollection();

    /**
     * Get item from adapter collection.
     */
    public abstract E getItem(int position);

    @Override
    public void onMove(int fromPosition, int toPosition) {
        // No operation by default
    }

    public void setViewHolderClickDelegate(ViewHolderClickDelegate viewHolderClickDelegate) {
        this.viewHolderClickDelegate = viewHolderClickDelegate;
    }

    public void setViewHolderLongClickDelegate(ViewHolderLongClickDelegate viewHolderLongClickDelegate) {
        this.viewHolderLongClickDelegate = viewHolderLongClickDelegate;
    }
}
