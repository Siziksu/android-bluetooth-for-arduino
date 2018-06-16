package com.siziksu.bluetooth.data;

import com.siziksu.bluetooth.data.model.MacroDataModel;

import java.util.List;

import io.reactivex.Single;

public interface RepositoryContract {

    void initializeBluetoothAdapter();

    Single<List<String>> getPairedDevices();

    Single<Boolean> selectPairedDevice(String device);

    Single<Boolean> connectWithTheDevice();

    void disconnectFromTheDevice();

    void sendCommandViaBluetooth(byte[] command);

    Single<List<MacroDataModel>> getMacros();

    void setMacros(List<MacroDataModel> list);
}
