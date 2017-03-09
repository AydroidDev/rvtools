package com.olmur.rvtools;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.olmur.rvtools.property.IOnMoveAction;
import com.olmur.rvtools.property.IOnOrderChangedListener;
import com.olmur.rvtools.property.IOnSwipeLeftAction;
import com.olmur.rvtools.property.IOnSwipeRightAction;
import com.olmur.rvtools.property.ISwipeContextMenuProvider;
import com.olmur.rvtools.property.IViewHolderSelector;


public class RvTools {

    public static class Builder {

        private ItemTouchHelperCallbacks mItemTouchHelperCallback;

        private RecyclerView mRecyclerView;

        public Builder(@NonNull RecyclerView recyclerView) {
            mItemTouchHelperCallback = new ItemTouchHelperCallbacks();
            mRecyclerView = recyclerView;
        }

        public Builder withSwipeLeftAction(@NonNull IOnSwipeLeftAction action) {
            mItemTouchHelperCallback.setSwipeLeft(true);
            mItemTouchHelperCallback.setSwipeLeftAction(action);
            return this;
        }

        public Builder withSwipeRightAction(@NonNull IOnSwipeRightAction action) {
            mItemTouchHelperCallback.setSwipeRight(true);
            mItemTouchHelperCallback.setSwipeRightAction(action);
            return this;
        }

        public Builder withMoveAction(@NonNull IOnMoveAction action, @NonNull IOnOrderChangedListener listener, int moveFlags) {
            mItemTouchHelperCallback.setMove(true);
            mItemTouchHelperCallback.setMoveAction(action);
            mItemTouchHelperCallback.setOnOrderChangedListener(listener);
            mItemTouchHelperCallback.setMoveFlags(moveFlags);
            return this;
        }

        public Builder withSwipeContextMenuDrawer(@NonNull SwipeContextMenuDrawer drawer) {
            mItemTouchHelperCallback.setBackgroundDrawer(drawer);
            return this;
        }

        public ItemTouchHelperCallbacks buildItemTouchHelperCallbacks() {
            return mItemTouchHelperCallback;
        }

        public ItemTouchHelper buildItemTouchHelper() {
            return new ItemTouchHelper(mItemTouchHelperCallback);
        }


        public void buildAndApplyToRecyclerView() {
            if (mItemTouchHelperCallback != null) {
                buildItemTouchHelper().attachToRecyclerView(mRecyclerView);
            }
        }

        private void checkRecyclerView() {
            if (mRecyclerView == null) {
                throw new NullPointerException("Call \'withRecyclerView(recyclerView)\' first!");
            }
        }
    }

    private static class ItemTouchHelperCallbacks extends ItemTouchHelper.Callback {

        private boolean mSwipeLeft;
        private boolean mSwipeRight;
        private boolean mMove;

        private int mMoveFlags;

        private IOnSwipeLeftAction mSwipeLeftAction;
        private IOnSwipeRightAction mSwipeRightAction;
        private IOnMoveAction mMoveAction;

        private IOnOrderChangedListener mOnOrderChangedListener;
        private boolean mWasMoved;

        private SwipeContextMenuDrawer mIBackgroundDrawer;

        private ItemTouchHelperCallbacks() {
        }

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

        private void setBackgroundDrawer(SwipeContextMenuDrawer IBackgroundDrawer) {
            mIBackgroundDrawer = IBackgroundDrawer;
        }

        public void setOnOrderChangedListener(IOnOrderChangedListener onOrderChangedListener) {
            mOnOrderChangedListener = onOrderChangedListener;
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
            int dragFlags = mMove ? mMoveFlags : 0;

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
                    mSwipeLeftAction.onSwipeLeft(viewHolder.getAdapterPosition());
                }
            } else {
                if (mSwipeRightAction != null) {
                    mSwipeRightAction.onSwipeRight(viewHolder.getAdapterPosition());
                }
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            // If we have no default background drawer -> try find one in view holder
            // Some lists have one background drawer for all item
            // Some have different drawer for each item
            if (viewHolder instanceof ISwipeContextMenuProvider) {
                mIBackgroundDrawer = ((ISwipeContextMenuProvider) viewHolder).getSwipeMenuDrawer();
            }

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && mIBackgroundDrawer != null) {
                if (dX < 0) {
                    mIBackgroundDrawer.drawLeft(c, viewHolder.itemView);
                } else {
                    mIBackgroundDrawer.drawRight(c, viewHolder.itemView);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            } else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder instanceof IViewHolderSelector) {
                IViewHolderSelector itemViewHolder = (IViewHolderSelector) viewHolder;
                itemViewHolder.onSelected();
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            final int adapterPosition = viewHolder.getAdapterPosition();
            if (viewHolder instanceof IViewHolderSelector && adapterPosition >= 0) {
                ((IViewHolderSelector) viewHolder).onReleased();
            }

            if (mWasMoved && mOnOrderChangedListener != null) {
                mWasMoved = false;
                mOnOrderChangedListener.onOrderChanged();
            }
        }

        @Override
        public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
            super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
            mWasMoved = true;
        }

    }
}
