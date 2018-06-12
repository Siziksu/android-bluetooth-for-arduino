package com.siziksu.bluetooth.domain.bluetooth;

import com.siziksu.bluetooth.data.RepositoryContract;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class BluetoothDomain implements BluetoothDomainContract<MainBluetoothDomainContract> {

    @Inject
    RepositoryContract repository;

    private Disposable disposable;
    private MainBluetoothDomainContract domain;

    private int lastPosition = -1;
    private boolean deviceSelected;
    private boolean connected;

    public BluetoothDomain(RepositoryContract repository) {
        this.repository = repository;
    }

    @Override
    public void register(MainBluetoothDomainContract domain) {
        this.domain = domain;
    }

    @Override
    public void unregister() {
        clearDisposable();
        repository.disconnectFromTheDevice();
    }

    @Override
    public void start() {
        domain.showLoadingDialog();
        domain.write("Initializing bluetooth adapter...", false);
        repository.initializeBluetoothAdapter();
        domain.write("Initialized", false);
        domain.write("Getting list of paired devices...", false);
        getPairedDevices();
    }

    @Override
    public void refresh() {
        getPairedDevices();
    }

    @Override
    public void onDeviceClick(int position, String device) {
        if (lastPosition != position && !deviceSelected) {
            selectDevice(position, device);
        } else if (lastPosition != position) {
            disconnectFromDevice();
            selectDevice(position, device);
        } else if (!deviceSelected) {
            selectDevice(position, device);
        } else {
            deselectDevice(device);
        }
    }

    @Override
    public void onConnectButtonClick() {
        if (deviceSelected) {
            if (!connected) {
                domain.showLoadingDialog();
                domain.write("Connecting...", false);
                connectWithTheDevice();
            } else {
                disconnectFromDevice();
            }
        } else {
            domain.write("No device selected", true);
        }
    }

    @Override
    public void sendCommand(String command) {
        if (deviceSelected && connected) {
            if (!command.isEmpty()) {
                repository.sendMessageToTheDevice(command);
                domain.write("Sent: " + command + "", false);
            } else {
                domain.write("Command not defined", true);
            }
        } else {
            domain.write("Not connected", true);
        }
    }

    private void getPairedDevices() {
        clearDisposable();
        disposable = repository.getPairedDevices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            domain.showDeviceList(list);
                            domain.write("List of paired devices ready", false);
                            domain.hideLoadingDialog();
                        },
                        throwable -> {
                            domain.showDeviceList(new ArrayList<>());
                            domain.write("Error getting paired devices", true);
                            domain.hideLoadingDialog();
                        }
                );
    }

    private void selectDevice(int position, String device) {
        clearDisposable();
        disposable = repository.selectPairedDevice(device)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        value -> {
                            deviceSelected = value;
                            lastPosition = position;
                            domain.write(device + " device selected", false);
                            domain.hideLoadingDialog();
                        },
                        throwable -> {
                            domain.write("Error selecting the pared device" + device + "", true);
                            domain.hideLoadingDialog();
                        }
                );
    }

    private void deselectDevice(String device) {
        domain.write(device + " device deselected", false);
        deviceSelected = false;
        disconnectFromDevice();
    }

    private void connectWithTheDevice() {
        clearDisposable();
        disposable = repository.connectWithTheDevice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        value -> {
                            connected = value;
                            domain.write("Connected", false);
                            domain.hideLoadingDialog();
                            domain.onConnectionUpdate(true);
                        },
                        throwable -> {
                            domain.write("Error connecting", true);
                            domain.hideLoadingDialog();
                            domain.onConnectionUpdate(false);
                        }
                );
    }

    private void disconnectFromDevice() {
        if (connected) {
            domain.write("Disconnecting...", false);
            repository.disconnectFromTheDevice();
            connected = false;
            domain.write("Disconnected", false);
            domain.onConnectionUpdate(false);
        }
    }

    private void clearDisposable() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
