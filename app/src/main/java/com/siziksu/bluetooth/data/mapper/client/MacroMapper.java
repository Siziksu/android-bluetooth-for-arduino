package com.siziksu.bluetooth.data.mapper.client;

import com.siziksu.bluetooth.common.mapper.Mapper;
import com.siziksu.bluetooth.data.client.model.MacroClientModel;
import com.siziksu.bluetooth.data.model.MacroDataModel;

public final class MacroMapper extends Mapper<MacroClientModel, MacroDataModel> {

    @Override
    public MacroDataModel map(MacroClientModel object) {
        MacroDataModel macroDataModel = new MacroDataModel();
        macroDataModel.id = object.id;
        macroDataModel.name = object.name;
        macroDataModel.command = object.command;
        return macroDataModel;
    }

    @Override
    public MacroClientModel unMap(MacroDataModel mapped) {
        MacroClientModel macroClientModel = new MacroClientModel();
        macroClientModel.id = mapped.id;
        macroClientModel.name = mapped.name;
        macroClientModel.command = mapped.command;
        return macroClientModel;
    }
}
