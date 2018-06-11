package com.siziksu.bluetooth.ui.common;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.siziksu.bluetooth.ui.common.dialog.IndeterminateProgressFragment;

/**
 * Helper class to generate the dialogs of the application.
 */
public class DialogFragmentHelper {

    private static final String INDETERMINATE_TAG = "indeterminate_dialog";

    private DialogFragmentHelper() {}

    public static void showLoadingDialog(AppCompatActivity activity) {
        IndeterminateProgressFragment fragment = getIndeterminateProgressFragment(activity);
        if (fragment == null) {
            fragment = new IndeterminateProgressFragment();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragment.setCancelable(false);
            fragment.show(fragmentManager, INDETERMINATE_TAG);
        }
    }

    public static void hideLoadingDialog(AppCompatActivity activity) {
        IndeterminateProgressFragment fragment = getIndeterminateProgressFragment(activity);
        if (fragment != null) {
            fragment.dismiss();
        }
    }

    private static IndeterminateProgressFragment getIndeterminateProgressFragment(AppCompatActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        return (IndeterminateProgressFragment) fragmentManager.findFragmentByTag(INDETERMINATE_TAG);
    }
}
