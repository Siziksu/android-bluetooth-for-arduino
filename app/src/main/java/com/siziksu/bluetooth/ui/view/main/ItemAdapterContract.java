package com.siziksu.bluetooth.ui.view.main;

import android.support.v7.widget.RecyclerView;

import com.siziksu.bluetooth.common.function.ClickListener;

import java.util.List;

public interface ItemAdapterContract {

    void init(ClickListener listener);

    String getItem(int position);

    RecyclerView.LayoutManager getLayoutManager();

    void showItems(List<String> list);

    RecyclerView.Adapter getAdapter();

    void notifyDataSetChanged();

    List<String> getItems();
}
