package com.olmur.rvtools.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.olmur.rvtools.property.OnItemClickListener;
import com.olmur.rvtools.property.OnMoveAction;
import com.olmur.rvtools.property.OnViewHolderEvent;

import java.util.Collection;

public abstract class RvtAdapter<E, C extends Collection<E>, VH extends RvtViewHolder<E>> extends RecyclerView.Adapter<VH>
        implements OnMoveAction {

    private SparseArray<OnViewHolderEvent> viewHolderEventDelegates;

    private OnItemClickListener itemClickListener;

    protected C adapterItems;

    public RvtAdapter() {
        adapterItems = initItemsCollection();
        viewHolderEventDelegates = new SparseArray<>();
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
        final VH viewHolder = createViewHolder(viewType, parent);
        viewHolder.setRvtRecycleAdapter(this);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(viewHolder.getAdapterPosition());
                }
            }
        });

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

    public void registerEventDelegates(SparseArray<OnViewHolderEvent> eventDelegates) {
        for (int i = 0; i < eventDelegates.size(); i++) {
            viewHolderEventDelegates.put(eventDelegates.keyAt(i), eventDelegates.valueAt(i));
        }
    }

    public void unregisterEventDelegates() {
        viewHolderEventDelegates.clear();
    }

    public void delegateEvent(int event, int position) {
        OnViewHolderEvent eventDelegate = viewHolderEventDelegates.get(event);
        if (eventDelegate == null) {
            new IllegalArgumentException("No delegate found for event: " + event).printStackTrace();
            return;
        }
        eventDelegate.onEvent(position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
