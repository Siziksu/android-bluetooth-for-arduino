package com.siziksu.bluetooth.dagger.module;

import com.siziksu.bluetooth.data.RepositoryContract;
import com.siziksu.bluetooth.domain.bluetooth.BluetoothDomain;
import com.siziksu.bluetooth.domain.bluetooth.BluetoothDomainContract;
import com.siziksu.bluetooth.domain.bluetooth.MainBluetoothDomainContract;
import com.siziksu.bluetooth.domain.preferences.MainPreferencesDomainContract;
import com.siziksu.bluetooth.domain.preferences.PreferencesDomain;
import com.siziksu.bluetooth.domain.preferences.PreferencesDomainContract;

import dagger.Module;
import dagger.Provides;

@Module
public final class DomainModule {

    @Provides
    BluetoothDomainContract<MainBluetoothDomainContract> providesBluetoothDomain(RepositoryContract repository) {
        return new BluetoothDomain(repository);
    }

    @Provides
    PreferencesDomainContract<MainPreferencesDomainContract> providesPreferencesDomain(RepositoryContract repository) {
        return new PreferencesDomain(repository);
    }
}
