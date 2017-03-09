package com.olmur.rvtools;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.View;

public abstract class SwipeContextMenuDrawer {
    public abstract void drawRight(@NonNull Canvas canvas, @NonNull View view);

    public abstract void drawLeft(@NonNull Canvas canvas, @NonNull View view);
}
