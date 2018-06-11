package com.siziksu.bluetooth.ui.view.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.siziksu.bluetooth.R;
import com.siziksu.bluetooth.common.function.ClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

final class ItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.deviceCardView)
    View deviceCardView;
    @BindView(R.id.deviceImage)
    ImageView deviceImage;
    @BindView(R.id.deviceName)
    TextView deviceName;

    private ClickListener selected;

    ItemViewHolder(View view, ClickListener listener) {
        super(view);
        ButterKnife.bind(this, view);
        deviceCardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(v, getAdapterPosition());
            }
            if (selected != null) {
                selected.onClick(v, getAdapterPosition());
            }
        });
    }

    public void setSelectedListener(ClickListener listener) {
        selected = listener;
    }
}
