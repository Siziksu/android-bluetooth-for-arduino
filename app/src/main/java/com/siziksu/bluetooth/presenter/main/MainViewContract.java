package com.siziksu.bluetooth.presenter.main;

import com.siziksu.bluetooth.presenter.BaseViewContract;

import java.util.List;

public interface MainViewContract extends BaseViewContract {

    void showDeviceList(List<String> list);

    void showLoadingDialog();

    void hideLoadingDialog();

    void writeInTerminal(String message, boolean both);

    void onConnectionUpdate(boolean connected);
}
