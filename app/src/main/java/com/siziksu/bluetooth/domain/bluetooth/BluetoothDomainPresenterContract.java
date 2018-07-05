package com.siziksu.bluetooth.domain.bluetooth;

import java.util.List;

public interface BluetoothDomainPresenterContract {

    void write(String message, boolean error, boolean both);

    void showLoadingDialog();

    void hideLoadingDialog();

    void showDeviceList(List<String> list);

    void onConnectionUpdate(boolean connected);
}
