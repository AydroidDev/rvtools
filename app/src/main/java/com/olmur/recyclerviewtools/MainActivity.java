package com.olmur.recyclerviewtools;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.olmur.rvtools.EmptyRecyclerView;
import com.olmur.rvtools.RvTools;
import com.olmur.rvtools.property.IOnMoveAction;
import com.olmur.rvtools.property.IOnSwipeLeftAction;
import com.olmur.rvtools.property.IOnSwipeRightAction;

public class MainActivity extends AppCompatActivity implements IOnSwipeLeftAction, IOnSwipeRightAction, IOnMoveAction {

    private MainAdapter mAdapter;

    private static final MainEntity[] sData = new MainEntity[]{
            new MainEntity("Swipe right to change item"),
            new MainEntity("Swipe left to delete item"),
            new MainEntity("Hold and move to reorder items"),
            new MainEntity("Change items appearance on select/release event"),
            new MainEntity("Provide swipe context menu for all items"),
            new MainEntity("Provide different swipe context menu for each item"),
            new MainEntity("Add empty view to list")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmptyRecyclerView recyclerView = (EmptyRecyclerView) findViewById(R.id.empty_recycler_view);
        View emptyView = findViewById(R.id.empty_view);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new MainAdapter(this.getApplicationContext(), sData);
        recyclerView.setAdapter(mAdapter);

//      Add empty view and recycler view will handle its rendering
        recyclerView.setEmptyView(emptyView);

        new RvTools.Builder(recyclerView)
                .withSwipeRightAction(this)
                .withSwipeLeftAction(this)
                .withMoveAction(this, mAdapter, ItemTouchHelper.DOWN | ItemTouchHelper.UP)
                .withSwipeContextMenuDrawer(new SwipeMenuDrawer())
                .buildAndApplyToRecyclerView();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        mAdapter.move(fromPosition, toPosition);
    }

    @Override
    public void onSwipeLeft(int position) {
        mAdapter.deleteItem(position);
    }

    @Override
    public void onSwipeRight(int position) {
        mAdapter.changeItem(position);
    }
}
