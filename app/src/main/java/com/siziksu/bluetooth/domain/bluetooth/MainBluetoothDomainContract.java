package com.siziksu.bluetooth.domain.bluetooth;

import java.util.List;

public interface MainBluetoothDomainContract {

    void write(String message, boolean error);

    void showLoadingDialog();

    void hideLoadingDialog();

    void showDeviceList(List<String> list);

    void onConnectionUpdate(boolean connected);
}
