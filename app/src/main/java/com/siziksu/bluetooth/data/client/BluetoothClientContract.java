package com.siziksu.bluetooth.data.client;

import java.util.List;

import io.reactivex.Single;

public interface BluetoothClientContract {

    void initializeBluetoothAdapter();

    Single<List<String>> getPairedDevices();

    Single<Boolean> selectPairedDevice(String device);

    Single<Boolean> connectWithTheDevice();

    void disconnectFromTheDevice();

    void sendMessageToTheDevice(String message);
}
