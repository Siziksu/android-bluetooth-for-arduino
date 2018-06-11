package com.siziksu.bluetooth.dagger.module;

import android.content.Context;

import com.siziksu.bluetooth.common.utils.FileUtils;
import com.siziksu.bluetooth.data.Repository;
import com.siziksu.bluetooth.data.RepositoryContract;
import com.siziksu.bluetooth.data.client.BluetoothClient;
import com.siziksu.bluetooth.data.client.BluetoothClientContract;
import com.siziksu.bluetooth.data.client.PreferencesClient;
import com.siziksu.bluetooth.data.client.PreferencesClientContract;
import com.siziksu.bluetooth.data.client.service.PreferencesService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class DataModule {

    @Provides
    BluetoothClientContract providesBluetoothClient() {
        return new BluetoothClient();
    }

    @Singleton
    @Provides
    PreferencesService providesPreferencesService(Context context) {
        return new PreferencesService(context);
    }

    @Singleton
    @Provides
    PreferencesClientContract providesPreferencesClient(FileUtils fileUtils, PreferencesService service) {
        return new PreferencesClient(fileUtils, service);
    }

    @Provides
    RepositoryContract providesRepository(Context context, BluetoothClientContract bluetoothClient, PreferencesClientContract preferencesClient) {
        return new Repository(context, bluetoothClient, preferencesClient);
    }
}
