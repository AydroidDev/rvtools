package com.olmur.rvtools.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Collections;
import java.util.List;

public class RvtUtils {

    public static class Gestures {

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

    public static class Resources {


        public static Bitmap getBitmap(Context context, @DrawableRes int drawableRes, @Px int widthPx, @Px int heightPx) {
            Drawable drawable = ContextCompat.getDrawable(context, drawableRes);

            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }

            Bitmap bitmap = Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }

        public static int dpToPixels(Context context, int dp) {
            android.content.res.Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        }


        public static int pixelsToDp(Context context, int px) {
            android.content.res.Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            return px / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        }

    }

    public static class Views {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public static void changeElevation(@NonNull View target, int toDp, long durationMillis) {
            target.animate().translationZ(toDp).setDuration(durationMillis).start();
        }

    }

    public static class Platform {

        public static boolean isApi21AndAbove() {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        }

    }

}
