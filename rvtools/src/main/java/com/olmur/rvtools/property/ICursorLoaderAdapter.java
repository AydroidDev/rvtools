package com.olmur.rvtools.property;

import android.database.Cursor;
import android.support.annotation.Nullable;


public interface ICursorLoaderAdapter {

    void swapCursor(@Nullable Cursor cursor);
}
