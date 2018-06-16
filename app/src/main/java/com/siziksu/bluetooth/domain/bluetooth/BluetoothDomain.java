package com.siziksu.bluetooth.domain.bluetooth;

import com.siziksu.bluetooth.data.RepositoryContract;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class BluetoothDomain implements BluetoothDomainContract<BluetoothDomainPresenterContract> {

    @Inject
    RepositoryContract repository;

    private Disposable disposable;
    private BluetoothDomainPresenterContract presenter;

    private int lastPosition = -1;
    private boolean deviceSelected;
    private boolean connected;

    public BluetoothDomain(RepositoryContract repository) {
        this.repository = repository;
    }

    @Override
    public void register(BluetoothDomainPresenterContract presenter) {
        this.presenter = presenter;
    }

    @Override
    public void unregister() {
        clearDisposable();
        repository.disconnectFromTheDevice();
        presenter = null;
    }

    @Override
    public void start() {
        presenter.showLoadingDialog();
        presenter.write("Initializing bluetooth adapter...", false, true);
        repository.initializeBluetoothAdapter();
        presenter.write("Initialized", false, true);
        presenter.write("Getting list of paired devices...", false, true);
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
                presenter.showLoadingDialog();
                presenter.write("Connecting...", false, true);
                connectWithTheDevice();
            } else {
                disconnectFromDevice();
            }
        } else {
            presenter.write("No device selected", true, true);
        }
    }

    @Override
    public void sendCommand(byte[] command) {
        if (deviceSelected && connected) {
            disposable = repository.sendCommandViaBluetooth(command)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> presenter.write("[" + (command[1] & 0xff) + ", " + (command[2] & 0xff) + "]", false, false),
                            throwable -> {
                                connected = false;
                                presenter.write("Device not connected", true, true);
                            }
                    );
        } else {
            presenter.write("Not connected", true, true);
        }
    }

    private void getPairedDevices() {
        clearDisposable();
        disposable = repository.getPairedDevices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            presenter.showDeviceList(list);
                            presenter.write("List of paired devices ready", false, true);
                            presenter.hideLoadingDialog();
                        },
                        throwable -> {
                            presenter.showDeviceList(new ArrayList<>());
                            presenter.write("Error getting paired devices", true, true);
                            presenter.hideLoadingDialog();
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
                            presenter.write(device + " device selected", false, true);
                            presenter.hideLoadingDialog();
                        },
                        throwable -> {
                            presenter.write("Error selecting the pared device" + device + "", true, true);
                            presenter.hideLoadingDialog();
                        }
                );
    }

    private void deselectDevice(String device) {
        presenter.write(device + " device deselected", false, true);
        deviceSelected = false;
        disconnectFromDevice();
    }

    private void connectWithTheDevice() {
        clearDisposable();
        disposable = repository.connectWithTheDevice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            connected = true;
                            presenter.write("Connected", false, true);
                            presenter.hideLoadingDialog();
                            presenter.onConnectionUpdate(true);
                        },
                        throwable -> {
                            connected = false;
                            presenter.write("Error connecting", true, true);
                            presenter.hideLoadingDialog();
                            presenter.onConnectionUpdate(false);
                        }
                );
    }

    private void disconnectFromDevice() {
        if (connected) {
            presenter.write("Disconnecting...", false, true);
            repository.disconnectFromTheDevice();
            connected = false;
            presenter.write("Disconnected", false, true);
            presenter.onConnectionUpdate(false);
        }
    }

    private void clearDisposable() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
