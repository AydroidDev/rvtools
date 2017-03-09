# RvTools
<strong>Define swipe gestures, move list items and change their appearance.</strong> 

![alt tag](https://github.com/olmur/rvtools/blob/master/snapshot.png)


Extend <b>BaseRecyclerAdapter</b> class
``` Java
public class MainAdapter extends BaseRecyclerAdapter<MainEntity, MainAdapter.ViewHolder> {
  ...
}
```
Create ViewHolder that extends BaseViewHolder 
``` Java
public class ViewHolder extends BaseRecyclerAdapter.BaseViewHolder<MainEntity> {
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

Create <b>Gestures Listeners</b>
``` Java
public class MainActivity extends AppCompatActivity implements IOnSwipeLeftAction, IOnSwipeRightAction, IOnMoveAction {
   @Override
    public void onMove(int fromPosition, int toPosition) {
        mAdapter.move(fromPosition, toPosition);
    }

    @Override
    public void onSwipeLeftAction(int position) {
        mAdapter.deletItem(position);
    }

    @Override
    public void onSwipeRightAction(int position) {
        mAdapter.changeItem(position);
    }
}
```

Create <b>Swipe Context Menu Drawer</b>
``` Java
public class SwipeMenuDrawer extends SwipeContextMenuDrawer {

    private final Paint mLeftPaint;
    private final Paint mRightPaint;

    public SwipeMenuDrawer() {
        mLeftPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLeftPaint.setColor(Color.GREEN);
        mRightPaint.setColor(Color.RED);
    }

    @Override
    public void drawRight(@NonNull Canvas canvas, @NonNull View view) {
        canvas.drawRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), mLeftPaint);
    }

    @Override
    public void drawLeft(@NonNull Canvas canvas, @NonNull View view) {
        canvas.drawRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), mRightPaint);
    }
}
```

To add different behaviour on item select/release implement <b>IViewHolderSelector</b> interface in ViewHolder
``` Java
 public class ViewHolder extends BaseRecyclerAdapter.BaseViewHolder<MainEntity> implements IViewHolderSelector {
         ... 
        @Override
        public void onSelected() {
            itemView.setBackgroundColor(Color.GRAY);
            mTitleTv.setTextColor(Color.WHITE);
        }

        @Override
        public void onReleased() {
            itemView.setBackgroundColor(Color.WHITE);
            mTitleTv.setTextColor(Color.GRAY);
        }
 }
```

Create <b>EmptyRecyclerView</b> and provide it with empty view
``` Java
EmptyRecyclerView recyclerView = (EmptyRecyclerView) findViewById(R.id.empty_recycler_view);
recyclerView.setEmptyView(emptyView);
```

Create RvTools for your RecyclerView

``` Java
  new RvTools.Builder(recyclerView)
                 .withSwipeRightAction(this)
                 .withSwipeLeftAction(this)
                 .withMoveAction(this, mAdapter, ItemTouchHelper.DOWN | ItemTouchHelper.UP)
                 .withSwipeContextMenuDrawer(new SwipeMenuDrawer())
                 .buildAndApplyToRecyclerView();
```

That's it :)