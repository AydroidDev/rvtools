package com.olmur.rvtools.adapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.olmur.rvtools.property.ViewHolderClickDelegate;
import com.olmur.rvtools.property.ViewHolderLongClickDelegate;
import com.olmur.rvtools.property.ViewHolderSelector;
import com.olmur.rvtools.utils.RvtConstants;
import com.olmur.rvtools.utils.RvtUtils;

public abstract class RvtViewHolder<E> extends RecyclerView.ViewHolder
        implements ViewHolderSelector {

    private ViewHolderClickDelegate rvClickDelegate;
    private ViewHolderLongClickDelegate rvLongClickDelegate;

    public RvtViewHolder(@NonNull View view) {
        super(view);
    }

    /**
     * Binds your model to ViewHolder.
     */
    public abstract void bindViewHolder(E element);

    @Override
    @CallSuper
    public void onSelected() {
        if (shouldChangeVhElevation()) {
            RvtUtils.Views.changeElevation(itemView, selectedVhHeightDp(), animationDurationMillis());
        }
    }

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

    protected void delegateClick(int event) {
        if (rvClickDelegate == null) {
            throw new NullPointerException("ViewHolderClickDelegate is null. " +
                    "Make sure you provided it in withViewHolderClickDelegate(delegate) method in RvTools.Builder class");
        }
        rvClickDelegate.delegateClick(getAdapterPosition(), event);
    }

    protected void delegateLongClick(int event) {
        if (rvLongClickDelegate == null) {
            throw new NullPointerException("ViewHolderLongClickDelegate is null. " +
                    "Make sure you provided it in withViewHolderLongClickDelegate(delegate) method in RvTools.Builder class");
        }
        rvLongClickDelegate.delegateLongClick(getAdapterPosition(), event);
    }

    public void setRvClickDelegate(@Nullable ViewHolderClickDelegate rvClickDelegate) {
        this.rvClickDelegate = rvClickDelegate;
    }

    public void setRvLongClickDelegate(@Nullable ViewHolderLongClickDelegate rvLongClickDelegate) {
        this.rvLongClickDelegate = rvLongClickDelegate;
    }
}