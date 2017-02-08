package com.olmur.rvtools;

import java.util.Collections;
import java.util.List;

public class GestureUtils {

    public static void move(List<?> source, int fromPos, int toPos) {
        if (fromPos < toPos) {
            for (int i = fromPos; i < toPos; i++) {
                Collections.swap(source, i, i + 1);
            }
        } else {
            for (int i = fromPos; i > toPos; i--) {
                Collections.swap(source, i, i - 1);
            }
        }
    }

}
