package com.olmur.recyclerviewtools;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.olmur.recyclerviewtools.adapter.MyAdapter;
import com.olmur.recyclerviewtools.adapter.MyViewHolder;
import com.olmur.recyclerviewtools.entities.MyEntity;
import com.olmur.rvtools.RvTools;
import com.olmur.rvtools.components.RvtSwipeContextMenu;
import com.olmur.rvtools.property.OnOrderChangedListener;
import com.olmur.rvtools.property.OnSwipeLeftAction;
import com.olmur.rvtools.property.OnSwipeRightAction;
import com.olmur.rvtools.property.ViewHolderClickDelegate;
import com.olmur.rvtools.recyclerview.RvtRecyclerView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements OnSwipeLeftAction, OnSwipeRightAction, OnOrderChangedListener {

    private MyAdapter myAdapter;

    private RvTools rvTools;

    private View root;
    private RvtRecyclerView recyclerView;

    private final MyEntity[] data = new MyEntity[]{
            new MyEntity("Swipe right to change item"),
            new MyEntity("Swipe left to delete item"),
            new MyEntity("Hold and moveList to reorder items"),
            new MyEntity("Change items appearance on select/release event"),
            new MyEntity("Provide swipe context menu for all items"),
            new MyEntity("Provide different swipe context menu for each item"),
            new MyEntity("Add empty view to list")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = findViewById(R.id.root);

        recyclerView = findViewById(R.id.empty_recycler_view);
        View emptyView = findViewById(R.id.empty_view);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);

        myAdapter = new MyAdapter();
        myAdapter.addData(Arrays.asList(data));

        recyclerView.setAdapter(myAdapter);

//      Add empty view and recycler view will handle its rendering
        recyclerView.setEmptyView(emptyView);

        rvTools = new RvTools.Builder()
                .withSwipeRightAction(this)
                .withSwipeLeftAction(this)
                .withMoveAction(this, RvTools.UP, RvTools.DOWN)
                .withSwipeContextMenuDrawer(new RvtSwipeContextMenu.Builder(this)
                        .withIconsRes(R.drawable.ic_edit, R.drawable.ic_delete)
                        .withIconsSizeDp(32)
                        .withIconsMarginFromListEdgesDp(16)
                        .withIconsColorInt(Color.WHITE)
                        .withBackgroundsColorsInt(Color.MAGENTA, Color.RED)
                        .build()
                )
                .withViewHolderClickDelegate(new ViewHolderClickDelegate() {
                    @Override
                    public void delegateClick(int position, int event) {
                        MyEntity entity = myAdapter.getItem(position);
                        if (event == MyViewHolder.ON_BUTTON_CLICKED)
                            // Do something with entity
                            Snackbar.make(root, "Delegate click from View Holder on position: " + position, Snackbar.LENGTH_SHORT).show();
                    }
                })
                .build();

        rvTools.bind(recyclerView);

        Snackbar.make(root, "RvTools binded", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bind: {
                rvTools.bind(recyclerView);
                Snackbar.make(root, "RvTools binded", Snackbar.LENGTH_SHORT).show();
                break;
            }
            case R.id.action_unbind: {
                rvTools.unbind();
                Snackbar.make(root, "RvTools unbinded", Snackbar.LENGTH_SHORT).show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSwipeLeft(int position) {
        myAdapter.deleteItem(position);
    }

    @Override
    public void onSwipeRight(int position) {
        myAdapter.changeItem(position);
    }

    @Override
    public void onOrderChanged() {
        Snackbar.make(root, "Order has been changed", Snackbar.LENGTH_SHORT).show();
    }
}
