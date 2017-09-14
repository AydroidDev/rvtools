package com.olmur.rvtools;

import android.graphics.Canvas;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchUIUtil;

import com.olmur.rvtools.adapter.RvtRecycleAdapter;
import com.olmur.rvtools.components.SwipeContextMenuDrawer;
import com.olmur.rvtools.property.OnItemClickListener;
import com.olmur.rvtools.property.OnOrderChangedListener;
import com.olmur.rvtools.property.OnSwipeLeftAction;
import com.olmur.rvtools.property.OnSwipeRightAction;
import com.olmur.rvtools.property.SwipeContextMenuProvider;
import com.olmur.rvtools.property.ViewHolderClickDelegate;
import com.olmur.rvtools.property.ViewHolderSelector;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public final class RvTools {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LEFT, RIGHT, UP, DOWN})
    public @interface Direction {
    }

    public static final int LEFT = ItemTouchHelper.LEFT;
    public static final int RIGHT = ItemTouchHelper.RIGHT;
    public static final int UP = ItemTouchHelper.UP;
    public static final int DOWN = ItemTouchHelper.DOWN;


    private RecyclerView recyclerView;

    private ItemTouchHelper itemTouchHelper;

    private ViewHolderClickDelegate viewHolderClickDelegate;

    private OnItemClickListener onItemClickListener;

    private RvTools() {
    }

    public void bind(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        itemTouchHelper.attachToRecyclerView(recyclerView);
        RvtRecycleAdapter rvtRecycleAdapter = (RvtRecycleAdapter) recyclerView.getAdapter();
        rvtRecycleAdapter.setViewHolderClickDelegate(viewHolderClickDelegate);
        rvtRecycleAdapter.setItemClickListener(onItemClickListener);
    }

    public void unbind() {
        itemTouchHelper.attachToRecyclerView(null);
        RvtRecycleAdapter rvtRecycleAdapter = (RvtRecycleAdapter) this.recyclerView.getAdapter();
        rvtRecycleAdapter.setViewHolderClickDelegate(null);
        rvtRecycleAdapter.setItemClickListener(null);
        this.recyclerView = null;
    }

    private void createItemTouchHelper(@NonNull ItemTouchHelperCallbacks itemTouchHelperCallbacks) {
        this.itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallbacks);
    }

    private void setViewHolderClickDelegate(ViewHolderClickDelegate viewHolderClickDelegate) {
        this.viewHolderClickDelegate = viewHolderClickDelegate;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class Builder {

        private ItemTouchHelperCallbacks itemTouchHelperCallback;

        private ViewHolderClickDelegate viewHolderClickDelegate;

        private OnItemClickListener onItemClickListener;

        public Builder() {
            itemTouchHelperCallback = new ItemTouchHelperCallbacks();
        }

        public Builder withSwipeLeftAction(@NonNull OnSwipeLeftAction action) {
            itemTouchHelperCallback.setSwipeLeftAction(action);
            return this;
        }

        public Builder withSwipeRightAction(@NonNull OnSwipeRightAction action) {
            itemTouchHelperCallback.setSwipeRightAction(action);
            return this;
        }

        public Builder withMoveAction(@NonNull OnOrderChangedListener listener, @Direction int... moveDirections) {
            itemTouchHelperCallback.setMoveGestureEnabled();
            itemTouchHelperCallback.setOnOrderChangedListener(listener);
            itemTouchHelperCallback.setMoveDirections(moveDirections);
            return this;
        }

        public Builder withSwipeContextMenuDrawer(@NonNull SwipeContextMenuDrawer drawer) {
            itemTouchHelperCallback.setSwipeContextMenuDrawer(drawer);
            return this;
        }

        public Builder withViewHolderClickDelegate(@NonNull ViewHolderClickDelegate clickDelegate) {
            this.viewHolderClickDelegate = clickDelegate;
            return this;
        }

        public Builder withOnItemClickListener(@NonNull OnItemClickListener itemClickListener) {
            this.onItemClickListener = itemClickListener;
            return this;
        }

        public RvTools build() {

            RvTools rvTools = new RvTools();

            rvTools.createItemTouchHelper(itemTouchHelperCallback);
            rvTools.setViewHolderClickDelegate(viewHolderClickDelegate);
            rvTools.setOnItemClickListener(onItemClickListener);

            return rvTools;
        }
    }

    private static class ItemTouchHelperCallbacks extends ItemTouchHelper.Callback {

        private boolean swipeLeftGestureEnabled;
        private boolean swipeRightGestureEnabled;
        private boolean moveGestureEnabled;

        private int moveDirections;

        private OnSwipeLeftAction swipeLeftAction;
        private OnSwipeRightAction swipeRightAction;

        private OnOrderChangedListener onOrderChangedListener;
        private boolean wasMoved;

        private SwipeContextMenuDrawer swipeContextMenuDrawer;

        private ItemTouchUIUtil itemTouchUIUtil;

        private ItemTouchHelperCallbacks() {
            itemTouchUIUtil = getDefaultUIUtil();
        }

        /**
         * Set directions the items could be moveList in.
         */
        private void setMoveDirections(@Direction int... moveDirections) {
            for (int direction : moveDirections) {
                this.moveDirections |= direction;
            }
        }

        void setMoveGestureEnabled() {
            this.moveGestureEnabled = true;
        }

        private void setSwipeLeftAction(OnSwipeLeftAction swipeLeftAction) {
            this.swipeLeftGestureEnabled = true;
            this.swipeLeftAction = swipeLeftAction;
        }

        private void setSwipeRightAction(OnSwipeRightAction swipeRightAction) {
            this.swipeRightGestureEnabled = true;
            this.swipeRightAction = swipeRightAction;
        }

        private void setSwipeContextMenuDrawer(SwipeContextMenuDrawer IBackgroundDrawer) {
            swipeContextMenuDrawer = IBackgroundDrawer;
        }

        private void setOnOrderChangedListener(OnOrderChangedListener onOrderChangedListener) {
            this.onOrderChangedListener = onOrderChangedListener;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int swipeFlags = 0;
            if (swipeLeftGestureEnabled) {
                swipeFlags |= ItemTouchHelper.LEFT;
            }
            if (swipeRightGestureEnabled) {
                swipeFlags |= ItemTouchHelper.RIGHT;
            }
            int dragFlags = moveGestureEnabled ? moveDirections : 0;

            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return moveGestureEnabled;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return swipeLeftGestureEnabled || swipeRightGestureEnabled;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            RvtRecycleAdapter rvtRecyclerAdapter;
            try {
                rvtRecyclerAdapter = (RvtRecycleAdapter) recyclerView.getAdapter();
            } catch (ClassCastException ex) {
                throw new RuntimeException("" +
                        "Only RvtRecyclerAdapter should be used with RvTools. " +
                        "Make sure your recycler view use adapter that extends from RvtRecyclerAdapter.");
            }

            rvtRecyclerAdapter.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT) {
                if (swipeLeftAction == null) return;
                swipeLeftAction.onSwipeLeft(viewHolder.getAdapterPosition());
            } else {
                if (swipeRightAction == null) return;
                swipeRightAction.onSwipeRight(viewHolder.getAdapterPosition());
            }
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            // If we have no default background drawer -> try find one in view holder
            // Some lists have one background drawer for all item
            // Some have different drawer for each item
            if (viewHolder instanceof SwipeContextMenuProvider) {
                swipeContextMenuDrawer = ((SwipeContextMenuProvider) viewHolder).getSwipeMenuDrawer();
            }

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && swipeContextMenuDrawer != null) {
                if (dX < 0) {
                    swipeContextMenuDrawer.drawRightSideMenu(c, viewHolder.itemView);
                } else {
                    swipeContextMenuDrawer.drawLeftSideMenu(c, viewHolder.itemView);
                }
            }

            itemTouchUIUtil.onDraw(c, recyclerView, viewHolder.itemView, dX, dY, actionState, isCurrentlyActive);
        }


        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder instanceof ViewHolderSelector) {
                ViewHolderSelector itemViewHolder = (ViewHolderSelector) viewHolder;
                itemViewHolder.onSelected();
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            final int adapterPosition = viewHolder.getAdapterPosition();

            if (viewHolder instanceof ViewHolderSelector && adapterPosition >= 0) {
                ((ViewHolderSelector) viewHolder).onReleased();
            }

            if (wasMoved && onOrderChangedListener != null) {
                wasMoved = false;
                onOrderChangedListener.onOrderChanged();
            }
        }

        @Override
        public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
            super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
            wasMoved = true;
        }

    }
}
