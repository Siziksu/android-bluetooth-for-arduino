package com.siziksu.bluetooth.domain.preferences;

import com.siziksu.bluetooth.domain.BaseDomainContract;
import com.siziksu.bluetooth.domain.model.MacroDomainModel;

import java.util.List;

public interface PreferencesDomainContract<D> extends BaseDomainContract<D> {

    void getMacros();

    void setMacros(List<MacroDomainModel> list);
}
