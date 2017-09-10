package com.olmur.rvtools.adapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.olmur.rvtools.property.OnMoveAction;
import com.olmur.rvtools.property.ViewHolderSelector;
import com.olmur.rvtools.utils.RvtConstants;
import com.olmur.rvtools.utils.RvtUtils;

import java.util.Collection;

public abstract class RvtRecycleAdapter<E, C extends Collection<E>, VH extends RvtRecycleAdapter.RvtViewHolder<E>> extends RecyclerView.Adapter<VH>
        implements OnMoveAction {

    protected C adapterItems;

    public RvtRecycleAdapter() {
        adapterItems = initItemsCollection();
    }

    public static abstract class RvtViewHolder<E> extends RecyclerView.ViewHolder
            implements ViewHolderSelector {

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
    }

    @Override
    public int getItemCount() {
        return adapterItems.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bindViewHolder(getItem(position));
    }

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
}
