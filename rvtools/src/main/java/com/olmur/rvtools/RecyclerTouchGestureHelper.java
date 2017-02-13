package com.olmur.rvtools;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.olmur.rvtools.property.IBackgroundDrawer;
import com.olmur.rvtools.property.IDrawControlViewHolder;
import com.olmur.rvtools.property.IGestureSensitiveViewHolder;
import com.olmur.rvtools.property.IOnMoveAction;
import com.olmur.rvtools.property.IOnSwipeLeftAction;
import com.olmur.rvtools.property.IOnSwipeRightAction;
import com.olmur.rvtools.property.ISortableAdapter;


public class RecyclerTouchGestureHelper extends ItemTouchHelper.Callback {

    public static class Builder {

        private RecyclerTouchGestureHelper mGestureHelper;

        public Builder() {
            mGestureHelper = new RecyclerTouchGestureHelper();
        }

        public Builder withSwipeLeftListener(IOnSwipeLeftAction listener) {
            mGestureHelper.setSwipeLeft(true);
            mGestureHelper.setSwipeLeftAction(listener);
            return this;
        }

        public Builder withSwipeRightListener(IOnSwipeRightAction listener) {
            mGestureHelper.setSwipeRight(true);
            mGestureHelper.setSwipeRightAction(listener);
            return this;
        }

        public Builder withMoveListener(IOnMoveAction listener, int moveFlags) {
            mGestureHelper.setMove(true);
            mGestureHelper.setMoveAction(listener);
            mGestureHelper.setMoveFlags(moveFlags);
            return this;
        }

        public Builder withBackgroundDrawer(IBackgroundDrawer drawer) {
            mGestureHelper.setBackgroundDrawer(drawer);
            return this;
        }

        public RecyclerTouchGestureHelper build() {
            return mGestureHelper;
        }
    }

    private boolean mSwipeLeft;
    private boolean mSwipeRight;
    private boolean mMove;

    private int mMoveFlags;

    private IOnSwipeLeftAction mSwipeLeftAction;
    private IOnSwipeRightAction mSwipeRightAction;
    private IOnMoveAction mMoveAction;

    private IBackgroundDrawer mIBackgroundDrawer;

    private RecyclerTouchGestureHelper() {}


    // Setters

    private void setSwipeLeft(boolean swipeLeft) {
        mSwipeLeft = swipeLeft;
    }

    private void setSwipeRight(boolean swipeRight) {
        mSwipeRight = swipeRight;
    }

    private void setMove(boolean move) {
        mMove = move;
    }

    private void setMoveFlags(int moveFlags) {
        mMoveFlags = moveFlags;
    }

    private void setSwipeLeftAction(IOnSwipeLeftAction swipeLeftAction) {
        mSwipeLeftAction = swipeLeftAction;
    }

    private void setSwipeRightAction(IOnSwipeRightAction swipeRightAction) {
        mSwipeRightAction = swipeRightAction;
    }

    private void setMoveAction(IOnMoveAction moveAction) {
        mMoveAction = moveAction;
    }

    private void setBackgroundDrawer(IBackgroundDrawer IBackgroundDrawer) {
        mIBackgroundDrawer = IBackgroundDrawer;
    }

    // Setters


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = 0;
        if (mSwipeLeft) {
            swipeFlags |= ItemTouchHelper.LEFT;
        }
        if (mSwipeRight) {
            swipeFlags |= ItemTouchHelper.RIGHT;
        }
        int dragFlags = 0;
        if (mMove) {
            dragFlags = mMoveFlags;
        }

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return mMove;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return mSwipeLeft || mSwipeRight;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (mMoveAction != null) {
            mMoveAction.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (direction == ItemTouchHelper.LEFT) {
            if (mSwipeLeftAction != null) {
                mSwipeLeftAction.onSwipeLeftAction(viewHolder.getAdapterPosition());
            }
        } else {
            if (mSwipeRightAction != null) {
                mSwipeRightAction.onSwipeRightAction(viewHolder.getAdapterPosition());
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        // If we have no default background drawer -> try find one in view holder
        // Some lists have one background drawer for all item
        // Some have different drawer for each item
        if (viewHolder instanceof IDrawControlViewHolder) {
            mIBackgroundDrawer = ((IDrawControlViewHolder) viewHolder).getBackgroundDrawer();
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && mIBackgroundDrawer != null) {
            if (dX < 0) {
                mIBackgroundDrawer.drawSwipeLeft(c, viewHolder.itemView);
            } else {
                mIBackgroundDrawer.drawSwipeRight(c, viewHolder.itemView);
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder instanceof IGestureSensitiveViewHolder) {
            IGestureSensitiveViewHolder itemViewHolder = (IGestureSensitiveViewHolder) viewHolder;
            itemViewHolder.onItemSelected();
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        final int adapterPosition = viewHolder.getAdapterPosition();
        if (viewHolder instanceof IGestureSensitiveViewHolder && adapterPosition >= 0) {
            ((IGestureSensitiveViewHolder) viewHolder).onItemReleased();
        }
        if (recyclerView.getAdapter() instanceof ISortableAdapter) {
            ((ISortableAdapter) recyclerView.getAdapter()).saveSorting();
        }
    }
}
