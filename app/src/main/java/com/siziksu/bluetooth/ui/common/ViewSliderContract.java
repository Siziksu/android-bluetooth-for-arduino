package com.siziksu.bluetooth.ui.common;

public interface ViewSliderContract {

    void showRightView();

    void showCenterView();

    void showLeftView();

    void onConfigurationChanged();

    boolean onBackAvailable();

    void onDestroy();
}
