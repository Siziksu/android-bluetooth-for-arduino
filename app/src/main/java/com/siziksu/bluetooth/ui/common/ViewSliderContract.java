package com.siziksu.bluetooth.ui.common;

public interface ViewSliderContract {

    void animateToRight();

    void animateToLeft();

    void onConfigurationChanged();

    boolean onBackAvailable();

    void onDestroy();
}
