package com.olmur.recyclerviewtools;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.View;

import com.olmur.rvtools.SwipeContextMenuDrawer;

class SwipeMenuDrawer extends SwipeContextMenuDrawer {

    private final Paint mLeftPaint;
    private final Paint mRightPaint;

    SwipeMenuDrawer() {
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
