package com.siziksu.bluetooth.ui.view.custom;

import com.siziksu.bluetooth.common.function.Func;

public interface RoundedViewContract {

    int getPotId();

    void setListener(Func.Consumer<Void> listener);

    void setValue(int value);

    int getValue();
}
