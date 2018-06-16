package com.siziksu.bluetooth.data.client;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.siziksu.bluetooth.common.Constants;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;

public final class BluetoothClient implements BluetoothClientContract {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;
    private OutputStream outputStream;

    private boolean deviceFound;

    @Override
    public void initializeBluetoothAdapter() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public Single<List<String>> getPairedDevices() {
        return Single.create(emitter -> {
            List<String> devices = new ArrayList<>();
            if (bluetoothAdapter.isEnabled()) {
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                for (BluetoothDevice device : pairedDevices) {
                    devices.add(device.getName());
                }
                emitter.onSuccess(devices);
            } else {
                emitter.onError(new Throwable("Bluetooth is disabled"));
            }
        });
    }

    @Override
    public Single<Boolean> selectPairedDevice(String device) {
        return Single.create(emitter -> {
            if (bluetoothAdapter.isEnabled()) {
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                for (BluetoothDevice pairedDevice : pairedDevices) {
                    if (pairedDevice.getName().equals(device)) {
                        bluetoothDevice = pairedDevice;
                        deviceFound = true;
                        break;
                    }
                }
                if (deviceFound) {
                    emitter.onSuccess(true);
                } else {
                    emitter.onError(new Throwable(device + " device not found"));
                }
            } else {
                emitter.onError(new Throwable("Bluetooth is disabled"));
            }
        });
    }

    @Override
    public Completable connectWithTheDevice() {
        return Completable.create(emitter -> {
            if (deviceFound) {
                UUID uuid = UUID.fromString(Constants.SERIAL_PORT_SERVICE_CLASS_UUID);
                try {
                    if (bluetoothSocket == null || !bluetoothSocket.isConnected()) {
                        bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
                        bluetoothSocket.connect();
                        outputStream = bluetoothSocket.getOutputStream();
                        emitter.onComplete();
                    } else {
                        emitter.onComplete();
                    }
                } catch (IOException e) {
                    emitter.onError(e);
                }
            } else {
                emitter.onError(new Throwable("Device not found"));
            }
        });
    }

    @Override
    public void disconnectFromTheDevice() {
        try {
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            Log.e("Bluetooth", e.getMessage(), e);
        }
    }

    @Override
    public Completable sendCommand(byte[] command) {
        return Completable.create(emitter -> {
            try {
                if (bluetoothSocket != null && bluetoothSocket.isConnected()) {
                    if (outputStream != null) {
                        outputStream.write(command);
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Throwable());
                    }
                } else {
                    emitter.onError(new Throwable());
                }
            } catch (IOException e) {
                emitter.onError(e);
            }
        });
    }
}
