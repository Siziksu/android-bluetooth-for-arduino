package com.siziksu.bluetooth.ui.view.main;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siziksu.bluetooth.R;
import com.siziksu.bluetooth.common.Constants;
import com.siziksu.bluetooth.common.function.ClickListener;

import java.util.List;

import javax.inject.Inject;

public final class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemAdapterContract {

    @Inject
    Context context;
    @Inject
    ItemManagerContract itemsManager;

    private ClickListener listener;
    private LinearLayoutManager layoutManager;
    private int selected = -1;

    public ItemAdapter(Context context, ItemManagerContract manager) {
        this.context = context;
        this.itemsManager = manager;
    }

    public void init(ClickListener listener) {
        this.listener = listener;
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public void refresh() {
        selected = -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_device, parent, false);
        return new ItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder localHolder = (ItemViewHolder) holder;
            String item = itemsManager.getItems().get(position);
            localHolder.deviceName.setText(item);
            if (selected == position) {
                localHolder.deviceName.setTextColor(Color.parseColor(Constants.DEVICE_SELECTED_COLOR));
                localHolder.deviceImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.device_selected));
            } else {
                localHolder.deviceName.setTextColor(Color.parseColor(Constants.DEVICE_COLOR));
                localHolder.deviceImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.device));
            }
            localHolder.setSelectedListener((view, pos) -> {
                selected = selected == pos ? -1 : pos;
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemsManager.getItems().size();
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public String getItem(int position) {
        return itemsManager.getItem(position);
    }

    @Override
    public void showItems(List<String> list) {
        itemsManager.showItems(this, list);
    }
}
