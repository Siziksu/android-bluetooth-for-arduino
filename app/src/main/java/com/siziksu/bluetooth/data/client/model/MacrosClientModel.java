package com.siziksu.bluetooth.data.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MacrosClientModel {

    @SerializedName("macros")
    @Expose
    public List<MacroClientModel> list = new ArrayList<>();
}
