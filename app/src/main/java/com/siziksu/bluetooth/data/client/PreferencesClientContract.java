package com.siziksu.bluetooth.data.client;

import com.siziksu.bluetooth.data.client.model.MacroClientModel;

import java.util.List;

import io.reactivex.Single;

public interface PreferencesClientContract {

    Single<List<MacroClientModel>> getMacros();

    void setMacros(List<MacroClientModel> list);
}
