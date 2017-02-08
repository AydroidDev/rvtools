package com.olmur.recyclerviewtools;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.View;

import com.olmur.rvtools.property.IBackgroundDrawer;

/**
 * Created by olexiimuraviov on 2/8/17.
 */

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
