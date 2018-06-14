package com.siziksu.bluetooth.domain.bluetooth;

import com.siziksu.bluetooth.data.RepositoryContract;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class BluetoothDomain implements BluetoothDomainContract<BluetoothDomainPresenterContract> {

    private static final String START_MARKER = "¡";
    private static final String END_MARKER = "!";

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
        presenter.write("Initializing bluetooth adapter...", false);
        repository.initializeBluetoothAdapter();
        presenter.write("Initialized", false);
        presenter.write("Getting list of paired devices...", false);
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
                presenter.write("Connecting...", false);
                connectWithTheDevice();
            } else {
                disconnectFromDevice();
            }
        } else {
            presenter.write("No device selected", true);
        }
    }

    @Override
    public void sendCommand(String command) {
        if (deviceSelected && connected) {
            if (!command.isEmpty()) {
                repository.sendMessageToTheDevice(START_MARKER + command + END_MARKER);
                presenter.write("Sent: " + command + "", false);
            } else {
                presenter.write("Command not defined", true);
            }
        } else {
            presenter.write("Not connected", true);
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
                            presenter.write("List of paired devices ready", false);
                            presenter.hideLoadingDialog();
                        },
                        throwable -> {
                            presenter.showDeviceList(new ArrayList<>());
                            presenter.write("Error getting paired devices", true);
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
                            presenter.write(device + " device selected", false);
                            presenter.hideLoadingDialog();
                        },
                        throwable -> {
                            presenter.write("Error selecting the pared device" + device + "", true);
                            presenter.hideLoadingDialog();
                        }
                );
    }

    private void deselectDevice(String device) {
        presenter.write(device + " device deselected", false);
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
                            presenter.write("Connected", false);
                            presenter.hideLoadingDialog();
                            presenter.onConnectionUpdate(true);
                        },
                        throwable -> {
                            presenter.write("Error connecting", true);
                            presenter.hideLoadingDialog();
                            presenter.onConnectionUpdate(false);
                        }
                );
    }

    private void disconnectFromDevice() {
        if (connected) {
            presenter.write("Disconnecting...", false);
            repository.disconnectFromTheDevice();
            connected = false;
            presenter.write("Disconnected", false);
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
