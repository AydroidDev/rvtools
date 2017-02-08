# RvTools
<strong>Tools for controlling recycler view.</strong> 
<p>Define <b>swipe</b> and <b>move</b> gestures, change items <b>appearance</b> on select/deselect, <b>reorder</b> items, 
define <b>background drawer</b> for whole list and for individual items, define <b>empty view</b>.</p>



Extend <b>BaseRecyclerAdapter</b> class
``` Java
public class MainAdapter extends BaseRecyclerAdapter<MainEntity, MainAdapter.ViewHolder> {
  ...
}
```
Create ViewHolder wich implements <b>IBinderViewHolder</b> interface
``` Java
public class ViewHolder extends RecyclerView.ViewHolder implements IBinderViewHolder<MainEntity> {
        private TextView mTitleTv;

        ViewHolder(@NonNull View view) {
            super(view);
            mTitleTv = (TextView) view.findViewById(R.id.title_tv);
        }

        @Override
        public void bindView(MainEntity element) {
            mTitleTv.setText(element.getTitle());
        }
}
```

Create <b>EmptyRecyclerView</b> and provide it with empty view
``` Java
EmptyRecyclerView recyclerView = (EmptyRecyclerView) findViewById(R.id.empty_recycler_view);
recyclerView.setEmptyView(emptyView);
```

Create <b>Gestures Listener</b>
``` Java
public class MainActivity extends AppCompatActivity implements IOnSwipeLeftAction, IOnSwipeRightAction, IOnMoveAction {
   @Override
    public void onMove(int fromPosition, int toPosition) {
        mAdapter.move(fromPosition, toPosition);
    }

    @Override
    public void onSwipeLeftAction(int position) {
        mAdapter.doSomething(position);
    }

    @Override
    public void onSwipeRightAction(int position) {
        mAdapter.doSomething(position);
    }
}
```

Create <b>background drawer</b>
``` Java
public class BackgroundDrawer1 implements IBackgroundDrawer {

    private final Paint mLeftPaint;
    private final Paint mRightPaint;

    public BackgroundDrawer1() {
        mLeftPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLeftPaint.setColor(Color.GREEN);
        mRightPaint.setColor(Color.CYAN);
    }

    @Override
    public void drawSwipeRight(@NonNull Canvas canvas, @NonNull View view) {
        canvas.drawRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), mLeftPaint);
    }

    @Override
    public void drawSwipeLeft(@NonNull Canvas canvas, @NonNull View view) {
        canvas.drawRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), mRightPaint);
    }
}
```

Build <b>ItemTouchHelper.Callback</b>
``` Java
ItemTouchHelper.Callback itemTouchCallBack = new RecyclerTouchGestureHelper.Builder()
                .withSwipeRightListener(this)
                .withSwipeLeftListener(this)
                .withMoveListener(this)
                // Default for whole recycler view
                .withBackgroundDrawer(new BackgroundDrawer1())
                .build();
```
Create <b>ItemTouchHelper</b> and <b>attach</b> it to recycler view
``` Java
 final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallBack);
 itemTouchHelper.attachToRecyclerView(recyclerView);
```

To add different behaviour on select/deselct implement <b>IGestureSensitiveViewHolder</b> interface in ViewHolder
``` Java
 public class ViewHolder extends RecyclerView.ViewHolder implements IBinderViewHolder<MainEntity>, IGestureSensitiveViewHolder {
         ... 
        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.BLUE);
            mTitleTv.setTextColor(Color.WHITE);
        }

        @Override
        public void onItemReleased() {
            itemView.setBackgroundColor(Color.WHITE);
            mTitleTv.setTextColor(Color.GRAY);
        }
 }
```

To add custom background drawers for each item implement <b>IDrawControlViewHolder</b>
``` Java
 public class ViewHolder extends RecyclerView.ViewHolder implements IBinderViewHolder<MainEntity>, IGestureSensitiveViewHolder, IDrawControlViewHolder {
         ...
        @Override
        public IBackgroundDrawer getBackgroundDrawer() {
            return mAdapterContent.get(getAdapterPosition()).isSwipeFlag() ? mDrawer1 : mDrawer2;
        }
}
```

To <b>save items order</b> implement <b>ISortableAdapter</b> interface in Adapter
``` Java
public class MainAdapter extends BaseRecyclerAdapter<MainEntity, MainAdapter.ViewHolder> implements ISortableAdapter {
       ...
       @Override
        public void saveSorting() {
        // Save items order
        }
}
```
