package com.siziksu.bluetooth.ui.view.main;

import java.util.ArrayList;
import java.util.List;

public final class ItemManager implements ItemManagerContract {

    private List<String> items = new ArrayList<>();

    public ItemManager() {}

    @Override
    public void showItems(ItemAdapterContract adapter, List<String> list) {
        items.clear();
        items.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public List<String> getItems() {
        return items;
    }
}
