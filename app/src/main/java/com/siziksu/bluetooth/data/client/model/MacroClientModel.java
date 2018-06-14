package com.siziksu.bluetooth.data.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MacroClientModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("command")
    @Expose
    public String command;
    @SerializedName("confirmation")
    @Expose
    public boolean confirmation;
}
