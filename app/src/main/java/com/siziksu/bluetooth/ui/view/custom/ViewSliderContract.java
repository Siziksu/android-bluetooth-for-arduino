package com.siziksu.bluetooth.ui.view.custom;

public interface ViewSliderContract {

    void showRightView();

    void showCenterView();

    void showLeftView();

    void onConfigurationChanged();

    boolean onBackAvailable();

    void onDestroy();
}
