package com.siziksu.bluetooth.ui.view.main;

import java.util.List;

public interface ItemManagerContract {

    void showItems(ItemAdapterContract adapter, List<String> list);

    String getItem(int position);

    List<String> getItems();
}
