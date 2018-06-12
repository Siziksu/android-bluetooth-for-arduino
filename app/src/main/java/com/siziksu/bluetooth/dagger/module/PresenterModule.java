package com.siziksu.bluetooth.dagger.module;

import com.siziksu.bluetooth.domain.bluetooth.BluetoothDomainContract;
import com.siziksu.bluetooth.domain.bluetooth.BluetoothDomainPresenterContract;
import com.siziksu.bluetooth.domain.preferences.PreferencesDomainPresenterContract;
import com.siziksu.bluetooth.domain.preferences.PreferencesDomainContract;
import com.siziksu.bluetooth.presenter.main.MainPresenter;
import com.siziksu.bluetooth.presenter.main.MainPresenterContract;
import com.siziksu.bluetooth.presenter.main.MainViewContract;

import dagger.Module;
import dagger.Provides;

@Module
public final class PresenterModule {

    @Provides
    MainPresenterContract<MainViewContract> providesMainPresenter(BluetoothDomainContract<BluetoothDomainPresenterContract> bluetoothDomain,
                                                                  PreferencesDomainContract<PreferencesDomainPresenterContract> preferencesDomain) {
        return new MainPresenter(bluetoothDomain, preferencesDomain);
    }
}
