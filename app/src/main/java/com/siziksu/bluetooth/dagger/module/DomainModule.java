package com.siziksu.bluetooth.dagger.module;

import com.siziksu.bluetooth.data.RepositoryContract;
import com.siziksu.bluetooth.domain.bluetooth.BluetoothDomain;
import com.siziksu.bluetooth.domain.bluetooth.BluetoothDomainContract;
import com.siziksu.bluetooth.domain.bluetooth.BluetoothDomainPresenterContract;
import com.siziksu.bluetooth.domain.preferences.PreferencesDomainPresenterContract;
import com.siziksu.bluetooth.domain.preferences.PreferencesDomain;
import com.siziksu.bluetooth.domain.preferences.PreferencesDomainContract;

import dagger.Module;
import dagger.Provides;

@Module
public final class DomainModule {

    @Provides
    BluetoothDomainContract<BluetoothDomainPresenterContract> providesBluetoothDomain(RepositoryContract repository) {
        return new BluetoothDomain(repository);
    }

    @Provides
    PreferencesDomainContract<PreferencesDomainPresenterContract> providesPreferencesDomain(RepositoryContract repository) {
        return new PreferencesDomain(repository);
    }
}
