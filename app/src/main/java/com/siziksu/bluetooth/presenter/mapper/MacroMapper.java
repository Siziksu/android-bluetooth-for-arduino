package com.siziksu.bluetooth.presenter.mapper;

import com.siziksu.bluetooth.common.mapper.Mapper;
import com.siziksu.bluetooth.domain.model.MacroDomainModel;
import com.siziksu.bluetooth.presenter.model.Macro;

public final class MacroMapper extends Mapper<MacroDomainModel, Macro> {

    @Override
    public Macro map(MacroDomainModel object) {
        Macro macro = new Macro();
        macro.id = object.id;
        macro.name = object.name;
        macro.command = object.command;
        macro.confirmation = object.confirmation;
        return macro;
    }

    @Override
    public MacroDomainModel unMap(Macro mapped) {
        MacroDomainModel macroDomainModel = new MacroDomainModel();
        macroDomainModel.id = mapped.id;
        macroDomainModel.name = mapped.name;
        macroDomainModel.command = mapped.command;
        macroDomainModel.confirmation = mapped.confirmation;
        return macroDomainModel;
    }
}
