package com.siziksu.bluetooth.domain.mapper;

import com.siziksu.bluetooth.common.mapper.Mapper;
import com.siziksu.bluetooth.data.model.MacroDataModel;
import com.siziksu.bluetooth.domain.model.MacroDomainModel;

public final class MacroMapper extends Mapper<MacroDataModel, MacroDomainModel> {

    @Override
    public MacroDomainModel map(MacroDataModel object) {
        MacroDomainModel macroDomainModel = new MacroDomainModel();
        macroDomainModel.id = object.id;
        macroDomainModel.name = object.name;
        macroDomainModel.command = object.command;
        return macroDomainModel;
    }

    @Override
    public MacroDataModel unMap(MacroDomainModel mapped) {
        MacroDataModel macroDataModel = new MacroDataModel();
        macroDataModel.id = mapped.id;
        macroDataModel.name = mapped.name;
        macroDataModel.command = mapped.command;
        return macroDataModel;
    }
}
