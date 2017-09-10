package com.olmur.rvtools.components;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.olmur.rvtools.utils.RvtConstants;
import com.olmur.rvtools.utils.RvtUtils;

public final class RvtSwipeContextMenu implements SwipeContextMenuDrawer {
    private static final String TAG = "RvtSwipeContextMenu";

    public static class Builder {

        private RvtSwipeContextMenu rvtSwipeContextMenu;

        private Context context;

        private int leftIconRes = RvtConstants.Empty.INT;
        private int rightIconRes = RvtConstants.Empty.INT;

        private int iconSizePx = RvtConstants.Empty.INT;

        public Builder(Context context) {
            this.context = context;
            rvtSwipeContextMenu = new RvtSwipeContextMenu(context);
        }

        public Builder withIconsRes(@DrawableRes int leftIconRes, @DrawableRes int rightIconRes) {
            this.leftIconRes = leftIconRes;
            this.rightIconRes = rightIconRes;
            return this;
        }

        public Builder withIconsSizeDp(int widthHeightDp) {
            return withIconsSizePx(RvtUtils.Resources.dpToPixels(context, widthHeightDp));
        }

        public Builder withIconsSizePx(@Px int widthHeightPx) {
            this.iconSizePx = widthHeightPx;
            return this;
        }

        public Builder withIconsMarginFromListEdgesDp(@Px int marginDp) {
            rvtSwipeContextMenu.setIconMarginPx(RvtUtils.Resources.dpToPixels(context, marginDp));
            return this;
        }

        public Builder withIconsColorRes(@ColorRes int colorRes) {
            return withIconsColorInt(ContextCompat.getColor(context, colorRes));
        }

        public Builder withIconsColorInt(@ColorInt int colorInt) {
            rvtSwipeContextMenu.setIconsColorInt(colorInt);
            return this;
        }

        public Builder withBackgroundsColorsRes(@ColorRes int leftBackgroundColoRes, @ColorRes int rightBackgroundColorRes) {
            return withBackgroundsColorsInt(
                    ContextCompat.getColor(context, leftBackgroundColoRes),
                    ContextCompat.getColor(context, rightBackgroundColorRes)
            );
        }

        public Builder withBackgroundsColorsInt(@ColorInt int leftBackgroundColoInt, @ColorInt int rightBackgroundColorInt) {
            rvtSwipeContextMenu.setLeftBackgroundColorInt(leftBackgroundColoInt);
            rvtSwipeContextMenu.setRightBackgroundColorInt(rightBackgroundColorInt);
            return this;
        }

        public RvtSwipeContextMenu build() {

            if (leftIconRes == RvtConstants.Empty.INT || rightIconRes == RvtConstants.Empty.INT)
                return rvtSwipeContextMenu;

            if (iconSizePx == RvtConstants.Empty.INT) {
                iconSizePx = RvtUtils.Resources.dpToPixels(context, 32);
            }

            Bitmap leftIcon = RvtUtils.Resources.getBitmap(context, leftIconRes, iconSizePx, iconSizePx);
            Bitmap rightIcon = RvtUtils.Resources.getBitmap(context, rightIconRes, iconSizePx, iconSizePx);


            rvtSwipeContextMenu.setLeftIcon(leftIcon);
            rvtSwipeContextMenu.setRightIcon(rightIcon);

            return rvtSwipeContextMenu;
        }
    }

    private final Paint leftPaint;
    private final Paint rightPaint;

    private final Paint iconPaint;

    private Bitmap rightIcon = null;
    private Bitmap leftIcon = null;

    private int iconMarginPx;

    private RvtSwipeContextMenu(Context context) {

        leftPaint = new Paint();
        leftPaint.setColor(Color.TRANSPARENT);

        rightPaint = new Paint();
        rightPaint.setColor(Color.TRANSPARENT);

        iconPaint = new Paint();
        iconPaint.setColorFilter(new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN));

        iconMarginPx = RvtUtils.Resources.dpToPixels(context, 16);
    }

    @Override
    public void drawRightSideMenu(@NonNull Canvas canvas, @NonNull View view) {
        canvas.drawRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), rightPaint);
        if (rightIcon != null)
            canvas.drawBitmap(rightIcon, view.getRight() - rightIcon.getWidth() - iconMarginPx, view.getBottom() + view.getTop() - rightIcon.getHeight() >> 1, iconPaint);
    }

    @Override
    public void drawLeftSideMenu(@NonNull Canvas canvas, @NonNull View view) {
        canvas.drawRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), leftPaint);
        if (leftIcon != null)
            canvas.drawBitmap(leftIcon, view.getLeft() + iconMarginPx, view.getBottom() + view.getTop() - leftIcon.getHeight() >> 1, iconPaint);
    }

    void setRightIcon(Bitmap rightIcon) {
        this.rightIcon = rightIcon;
    }

    void setLeftIcon(Bitmap leftIcon) {
        this.leftIcon = leftIcon;
    }

    void setRightBackgroundColorInt(int rightBackgroundColorInt) {
        rightPaint.setColor(rightBackgroundColorInt);
    }

    void setLeftBackgroundColorInt(int leftBackgroundColorInt) {
        leftPaint.setColor(leftBackgroundColorInt);
    }

    void setIconMarginPx(int iconMarginPx) {
        this.iconMarginPx = iconMarginPx;
    }

    void setIconsColorInt(int iconsColorInt) {
        iconPaint.setColorFilter(new PorterDuffColorFilter(iconsColorInt, PorterDuff.Mode.SRC_IN));
    }
}
