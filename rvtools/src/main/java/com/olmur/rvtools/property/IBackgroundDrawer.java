package com.olmur.rvtools.property;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.View;

public interface IBackgroundDrawer {
    void drawSwipeRight(@NonNull Canvas canvas, @NonNull View view);

    void drawSwipeLeft(@NonNull Canvas canvas, @NonNull View view);
}
