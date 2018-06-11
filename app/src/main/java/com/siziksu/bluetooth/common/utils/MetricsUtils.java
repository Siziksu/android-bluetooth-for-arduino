package com.siziksu.bluetooth.common.utils;

import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;

/**
 * Class with some util methods for metrics.
 */
public final class MetricsUtils {

    private static final int VERTICAL = 0;
    private static final int HORIZONTAL = 1;

    private final DisplayMetrics metrics;
    private final Display display;
    private final float width;
    private final float height;

    public MetricsUtils(AppCompatActivity activity) {
        this.metrics = new DisplayMetrics();
        display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    public float getWidth() {
        return getRotation() == VERTICAL ? (width < height ? metrics.widthPixels : metrics.heightPixels) : (width < height ? metrics.heightPixels : metrics.widthPixels);
    }

    public int getRotation() {
        final int rotation = display.getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                return VERTICAL;
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
            default:
                return HORIZONTAL;
        }
    }
}
