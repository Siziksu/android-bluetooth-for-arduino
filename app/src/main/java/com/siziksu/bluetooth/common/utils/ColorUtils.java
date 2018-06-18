package com.siziksu.bluetooth.common.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.siziksu.bluetooth.R;

public class ColorUtils {

    private static final int COLOR_GREY = 0;
    private static final int COLOR_BLUE = 1;
    private static final int COLOR_GREEN = 2;
    private static final int COLOR_ORANGE = 3;
    private static final int COLOR_YELLOW = 4;
    private static final int COLOR_RED = 5;
    private static final int COLOR_PINK = 6;
    private static final int COLOR_LILA = 7;
    private static final int COLOR_SEA = 8;

    private ColorUtils() {}

    public static int getRadioButtonFromColor(int color) {
        switch (color) {
            case COLOR_GREY:
                return R.id.macroRadioGrey;
            case COLOR_BLUE:
                return R.id.macroRadioBlue;
            case COLOR_GREEN:
                return R.id.macroRadioGreen;
            case COLOR_ORANGE:
                return R.id.macroRadioOrange;
            case COLOR_YELLOW:
                return R.id.macroRadioYellow;
            case COLOR_RED:
                return R.id.macroRadioRed;
            case COLOR_PINK:
                return R.id.macroRadioPink;
            case COLOR_LILA:
                return R.id.macroRadioLila;
            case COLOR_SEA:
                return R.id.macroRadioSea;
            default:
                return R.id.macroRadioGrey;
        }
    }

    public static Drawable getBackgroundDrawableFromColor(Context context, int color) {
        switch (color) {
            case COLOR_GREY:
                return ContextCompat.getDrawable(context, R.drawable.button_macro_grey);
            case COLOR_BLUE:
                return ContextCompat.getDrawable(context, R.drawable.button_macro_blue);
            case COLOR_GREEN:
                return ContextCompat.getDrawable(context, R.drawable.button_macro_green);
            case COLOR_ORANGE:
                return ContextCompat.getDrawable(context, R.drawable.button_macro_orange);
            case COLOR_YELLOW:
                return ContextCompat.getDrawable(context, R.drawable.button_macro_yellow);
            case COLOR_RED:
                return ContextCompat.getDrawable(context, R.drawable.button_macro_red);
            case COLOR_PINK:
                return ContextCompat.getDrawable(context, R.drawable.button_macro_pink);
            case COLOR_LILA:
                return ContextCompat.getDrawable(context, R.drawable.button_macro_lila);
            case COLOR_SEA:
                return ContextCompat.getDrawable(context, R.drawable.button_macro_sea);
            default:
                return ContextCompat.getDrawable(context, R.drawable.button_macro_grey);
        }
    }

    public static int getMacroColorFromRadioButtonChecked(View view) {
        switch (view.getId()) {
            case R.id.macroRadioGrey:
                return COLOR_GREY;
            case R.id.macroRadioBlue:
                return COLOR_BLUE;
            case R.id.macroRadioGreen:
                return COLOR_GREEN;
            case R.id.macroRadioOrange:
                return COLOR_ORANGE;
            case R.id.macroRadioYellow:
                return COLOR_YELLOW;
            case R.id.macroRadioRed:
                return COLOR_RED;
            case R.id.macroRadioPink:
                return COLOR_PINK;
            case R.id.macroRadioLila:
                return COLOR_LILA;
            case R.id.macroRadioSea:
                return COLOR_SEA;
            default:
                return COLOR_GREY;
        }
    }
}
