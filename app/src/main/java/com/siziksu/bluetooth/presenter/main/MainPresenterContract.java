package com.siziksu.bluetooth.presenter.main;

import android.content.Intent;
import android.widget.Button;

import com.siziksu.bluetooth.presenter.BasePresenterContract;
import com.siziksu.bluetooth.presenter.BaseViewContract;

import java.util.List;

public interface MainPresenterContract<V extends BaseViewContract> extends BasePresenterContract<V> {

    void setButtons(List<Button> buttons);

    void updateButtonsText(boolean macrosByName);

    void updateScreenOnState(boolean keepScreenOn);

    void start();

    void refresh();

    void onConnectButtonClick();

    void onDeviceClick(int position, String device);

    void onPotChange(int id, int value);

    void onMacroButtonTouch(int resId, int action);

    void onMacroEditButtonClick(int resId);

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
