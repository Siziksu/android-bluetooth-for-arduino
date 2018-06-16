package com.siziksu.bluetooth.data;

import android.content.Context;

import com.siziksu.bluetooth.data.client.BluetoothClientContract;
import com.siziksu.bluetooth.data.client.PreferencesClientContract;
import com.siziksu.bluetooth.data.mapper.client.MacroMapper;
import com.siziksu.bluetooth.data.model.MacroDataModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public final class Repository implements RepositoryContract {

    @Inject
    Context context;
    @Inject
    PreferencesClientContract preferencesClient;
    @Inject
    BluetoothClientContract bluetoothClient;

    public Repository(Context context, BluetoothClientContract bluetoothClient, PreferencesClientContract preferencesClient) {
        this.context = context;
        this.bluetoothClient = bluetoothClient;
        this.preferencesClient = preferencesClient;
    }

    @Override
    public void initializeBluetoothAdapter() {
        bluetoothClient.initializeBluetoothAdapter();
    }

    @Override
    public Single<List<String>> getPairedDevices() {
        return bluetoothClient.getPairedDevices();
    }

    @Override
    public Single<Boolean> selectPairedDevice(String device) {
        return bluetoothClient.selectPairedDevice(device);
    }

    @Override
    public Single<Boolean> connectWithTheDevice() {
        return bluetoothClient.connectWithTheDevice();
    }

    @Override
    public void disconnectFromTheDevice() {
        bluetoothClient.disconnectFromTheDevice();
    }

    @Override
    public void sendCommandViaBluetooth(byte[] command) {
        bluetoothClient.sendCommand(command);
    }

    @Override
    public Single<List<MacroDataModel>> getMacros() {
        return preferencesClient.getMacros().map(list -> new MacroMapper().map(list));
    }

    @Override
    public void setMacros(List<MacroDataModel> list) {
        preferencesClient.setMacros(new MacroMapper().unMap(list));
    }
}
