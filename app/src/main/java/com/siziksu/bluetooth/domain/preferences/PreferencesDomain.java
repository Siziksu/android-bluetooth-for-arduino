package com.siziksu.bluetooth.domain.preferences;

import android.util.Log;

import com.siziksu.bluetooth.common.Constants;
import com.siziksu.bluetooth.data.RepositoryContract;
import com.siziksu.bluetooth.domain.mapper.MacroMapper;
import com.siziksu.bluetooth.domain.model.MacroDomainModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class PreferencesDomain implements PreferencesDomainContract<PreferencesDomainPresenterContract> {

    @Inject
    RepositoryContract repository;

    private Disposable disposable;
    private PreferencesDomainPresenterContract domain;

    public PreferencesDomain(RepositoryContract repository) {
        this.repository = repository;
    }

    @Override
    public void register(PreferencesDomainPresenterContract domain) {
        this.domain = domain;
    }

    @Override
    public void unregister() {
        clearDisposable();
    }

    @Override
    public void getMacros() {
        disposable = repository.getMacros()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> domain.onMacros(new MacroMapper().map(list)),
                        throwable -> Log.e(Constants.TAG, throwable.getMessage(), throwable)
                );
    }

    @Override
    public void setMacros(List<MacroDomainModel> list) {
        repository.setMacros(new MacroMapper().unMap(list));
    }

    private void clearDisposable() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
