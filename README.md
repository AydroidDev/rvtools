# RvTools
 
![alt tag](https://user-images.githubusercontent.com/16815979/31423289-936f3df8-ae5c-11e7-9cc8-56d1c930a05c.gif)


<b>Add lib to your project</b>
``` Gradle
compile ('com.olmur.rvtools:rvtools:0.0.5') {
        exclude group: 'com.android.support'
}
```

Extend <b>RvtRecyclerAdapter</b> class
``` Java
public class MyAdapter extends RvtListAdapter<MyEntity, MyViewHolder> {
  ...
}
```
Create <b>ViewHolder that extends RvtViewHolder</b> 
``` Java
public class MyViewHolder extends RvtRecyclerAdapter.RvtViewHolder<MyEntity> {

        private TextView titleTv;

        ViewHolder(@NonNull View view) {
            super(view);
            titleTv = (TextView) view.findViewById(R.id.title_tv);
        }

        @Override
        public void bindViewHolder(MyEntity element) {
            titleTv.setText(element.getTitle());
        }
}
```

Create <b>Gestures Listeners</b>
``` Java
public class MainActivity extends AppCompatActivity implements OnSwipeLeftAction, OnSwipeRightAction, OnOrderChangedListener {

    @Override
    public void onSwipeLeftAction(int position) {
        adapter.deletItem(position);
    }
    
    @Override
    public void onSwipeRightAction(int position) {
        adapter.changeItem(position);
    }
    
    @Override
    public void onOrderChanged() {
        Snackbar.make(root, "Order has been changed", Snackbar.LENGTH_SHORT).show();
    }
}
```

Create <b>Swipe Context Menu Drawer</b>:

You can use <b>RvtSwipeContextMenu</b>
``` Java
RvtSwipeContextMenu rvtSwipeContextMenu = new RvtSwipeContextMenu.Builder(context)
                        .withIconsRes(R.drawable.ic_edit, R.drawable.ic_delete)
                        .withIconsSizeDp(32)
                        .withIconsMarginFromListEdgesDp(16)
                        .withIconsColorInt(Color.WHITE)
                        .withBackgroundsColorsInt(Color.MAGENTA, Color.RED)
                        .build();
```

Or create your own swipe context menu by implementing <b>SwipeContextMenuDrawer</b> interface

To add different behaviour on item select/release override these methods in your ViewHolder
``` Java
 public class MyViewHolder extends RvtViewHolder<MyEntity> {
         ... 
        @Override
        public void onSelected() {
            super.onSelected();
            itemView.setBackgroundColor(Color.GRAY);
            titleTv.setTextColor(Color.WHITE);
        }

        @Override
        public void onReleased() {
            super.onReleased();
            itemView.setBackgroundColor(Color.WHITE);
            titleTv.setTextColor(Color.GRAY);
        }
        
        // Provide selected vh height for elevation increase/decrease when vh is being selected/released.
        // Works only API 21 and above only.
        @Override
        public int selectedVhHeightDp() {
            return RvtUtils.Resources.dpToPixels(itemView.getContext(), 8);
        }
 }
```

Create <b>RvtRecyclerView</b> and provide it with empty view
``` Java
RvtRecyclerView recyclerView = (RvtRecyclerView) findViewById(R.id.rvt_recycler_view);
recyclerView.setEmptyView(emptyView);
```

Create RvTools for your RecyclerView

``` Java
  RvTools rvtools = new RvTools
                         .Builder(recyclerView)
                         .withSwipeRightAction(this)
                         .withSwipeLeftAction(this)
                         .withMoveAction(this, RvTools.DOWN, RvTools.UP)
                         .withSwipeContextMenuDrawer(rvtSwipeContextMenu)
                         .build();
```

Than you can bind and unbind rvtools from your recycler view
 
 ``` Java
    rvtools.bind(recyclerView);
    
    rvtools.unbind();
 ```

Check out for more examples in sample app.