package com.siziksu.bluetooth.presenter.main;

import android.content.Intent;
import android.widget.Button;

import com.siziksu.bluetooth.presenter.BasePresenterContract;
import com.siziksu.bluetooth.presenter.BaseViewContract;

public interface MainPresenterContract<V extends BaseViewContract> extends BasePresenterContract<V> {

    void setButtons(Button[] buttons);

    void updateButtonsText();

    void start();

    void refresh();

    void onConnectButtonClick();

    void onDeviceClick(int position, String device);

    void onMacroButtonClick(int resId);

    void onMacroButtonLongClick(int resId);

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
