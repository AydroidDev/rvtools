package com.olmur.rvtools.components;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.View;

public interface SwipeContextMenuDrawer {

    void drawRightSideMenu(@NonNull Canvas canvas, @NonNull View view);

    void drawLeftSideMenu(@NonNull Canvas canvas, @NonNull View view);
}
