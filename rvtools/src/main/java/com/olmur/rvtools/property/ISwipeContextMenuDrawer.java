package com.olmur.rvtools.property;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.View;

public interface ISwipeContextMenuDrawer {
    void drawRight(@NonNull Canvas canvas, @NonNull View view);

    void drawLeft(@NonNull Canvas canvas, @NonNull View view);
}
