package com.siziksu.bluetooth.data.client;

import com.google.gson.Gson;
import com.siziksu.bluetooth.common.utils.FileUtils;
import com.siziksu.bluetooth.data.client.model.MacroClientModel;
import com.siziksu.bluetooth.data.client.model.MacrosClientModel;
import com.siziksu.bluetooth.data.client.service.PreferencesService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public final class PreferencesClient implements PreferencesClientContract {

    @Inject
    FileUtils fileUtils;
    @Inject
    PreferencesService service;

    private static final String DEFAULT_MACROS_JSON = "default_macros.json";
    private static final String PREFERENCES_MACROS_KEY = "macros";

    public PreferencesClient(FileUtils fileUtils, PreferencesService service) {
        this.fileUtils = fileUtils;
        this.service = service;
    }

    @Override
    public Single<List<MacroClientModel>> getMacros() {
        return Single.create(emitter -> {
            String defaultMacros = fileUtils.getFileContent(DEFAULT_MACROS_JSON);
            String json = service.useDefaultSharedPreferences().getValue(PREFERENCES_MACROS_KEY, defaultMacros);
            MacrosClientModel macrosClientModel = new Gson().fromJson(json, MacrosClientModel.class);
            emitter.onSuccess(macrosClientModel.list);
        });
    }

    @Override
    public void setMacros(List<MacroClientModel> list) {
        MacrosClientModel macrosClientModel = new MacrosClientModel();
        macrosClientModel.list.addAll(list);
        String json = new Gson().toJson(macrosClientModel);
        service.useDefaultSharedPreferences().applyValue(PREFERENCES_MACROS_KEY, json);
    }
}
