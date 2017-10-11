package com.olmur.rvtools.adapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.olmur.rvtools.property.ViewHolderSelector;
import com.olmur.rvtools.utils.RvtConstants;
import com.olmur.rvtools.utils.RvtUtils;

public abstract class RvtViewHolder<E> extends RecyclerView.ViewHolder
        implements ViewHolderSelector {

    private RvtAdapter rvtRecycleAdapter;

    public RvtViewHolder(@NonNull View view) {
        super(view);
    }

    /**
     * Binds your model to ViewHolder.
     */
    public abstract void bindViewHolder(E element);

    /**
     * Method is being called when user is dragging or moving current view holder
     */
    @Override
    @CallSuper
    public void onSelected() {
        if (shouldChangeVhElevation()) {
            RvtUtils.Views.changeElevation(itemView, selectedVhHeightDp(), animationDurationMillis());
        }
    }

    /**
     * Method is being called when user stopped interacting with current view holder
     */
    @Override
    @CallSuper
    public void onReleased() {
        if (shouldChangeVhElevation()) {
            RvtUtils.Views.changeElevation(itemView, 0, animationDurationMillis());
        }
    }

    private boolean shouldChangeVhElevation() {
        return RvtUtils.Platform.isApi21AndAbove() && selectedVhHeightDp() != 0;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public int selectedVhHeightDp() {
        return RvtConstants.Default.DEFAULT_SELECTED_VH_HEIGHT;
    }

    public long animationDurationMillis() {
        return RvtConstants.Default.STANDARD_ANIMATION_DURATION;
    }

    /**
     * Use this method to delegate click from inner view holder's views
     */
    protected void delegateEvent(int event) {
        if (rvtRecycleAdapter != null) {
            rvtRecycleAdapter.delegateEvent(event, getAdapterPosition());
        }
    }

    public void setRvtRecycleAdapter(RvtAdapter rvtRecycleAdapter) {
        this.rvtRecycleAdapter = rvtRecycleAdapter;
    }
}