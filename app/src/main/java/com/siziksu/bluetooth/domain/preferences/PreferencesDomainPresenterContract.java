package com.siziksu.bluetooth.domain.preferences;

import com.siziksu.bluetooth.domain.model.MacroDomainModel;

import java.util.List;

public interface PreferencesDomainPresenterContract {

    void onMacros(List<MacroDomainModel> list);
}
