package com.siziksu.bluetooth.common.function;

import android.view.View;

@FunctionalInterface
public interface ClickListener {

    void onClick(View view, int position);
}
