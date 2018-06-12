package com.siziksu.bluetooth.ui.view.main;

import android.support.v7.widget.RecyclerView;

import com.siziksu.bluetooth.common.function.ClickListener;

import java.util.List;

public interface ItemAdapterContract {

    void init(ClickListener listener);

    void refresh();

    void notifyDataSetChanged();

    RecyclerView.LayoutManager getLayoutManager();

    RecyclerView.Adapter getAdapter();

    String getItem(int position);

    void showItems(List<String> list);
}
