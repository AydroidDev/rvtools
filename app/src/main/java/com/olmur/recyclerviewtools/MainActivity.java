package com.olmur.recyclerviewtools;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.olmur.rvtools.EmptyRecyclerView;
import com.olmur.rvtools.RecyclerTouchGestureHelper;
import com.olmur.rvtools.property.IOnMoveAction;
import com.olmur.rvtools.property.IOnSwipeLeftAction;
import com.olmur.rvtools.property.IOnSwipeRightAction;

public class MainActivity extends AppCompatActivity implements IOnSwipeLeftAction, IOnSwipeRightAction, IOnMoveAction {

    private MainAdapter mAdapter;

    private static final MainEntity[] sData = new MainEntity[] {
            new MainEntity("Swipe gestures"),
            new MainEntity("Control items background views"),
            new MainEntity("Provide default background for all items"),
            new MainEntity("Hold and move to reorder items"),
            new MainEntity("Define views behaviour on select/deselect")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmptyRecyclerView recyclerView = (EmptyRecyclerView) findViewById(R.id.empty_recycler_view);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new MainAdapter(this.getApplicationContext(), sData);
        recyclerView.setAdapter(mAdapter);

        // Define empty view and recycler view will handle its rendering
        // recyclerView.setEmptyView(YOUR_EMPTY_VIEW);

        ItemTouchHelper.Callback itemTouchCallBack = new RecyclerTouchGestureHelper.Builder()
                .withSwipeRightListener(this)
                .withSwipeLeftListener(this)
                .withMoveListener(this)
                // Provide default background drawer
                // or use different drawer per view holder
                .withBackgroundDrawer(new BackgroundDrawer1())
                .build();

        // Create helper and attach it recycler view
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        // Do something on move
        mAdapter.move(fromPosition, toPosition);
    }

    @Override
    public void onSwipeLeftAction(int position) {
        // Do something on swipe
        mAdapter.doSomething(position);
    }

    @Override
    public void onSwipeRightAction(int position) {
        // Do something on swipe
        mAdapter.doSomething(position);
    }
}
