package com.siziksu.bluetooth.data.client;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface BluetoothClientContract {

    void initializeBluetoothAdapter();

    Single<List<String>> getPairedDevices();

    Single<Boolean> selectPairedDevice(String device);

    Completable connectWithTheDevice();

    void disconnectFromTheDevice();

    Completable sendCommand(byte[] command);
}
