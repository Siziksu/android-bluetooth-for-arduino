package com.siziksu.bluetooth.ui.common.dialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.siziksu.bluetooth.R;

class IndeterminateProgressDialog extends ProgressDialog {

    IndeterminateProgressDialog(Context context) {
        super(context, R.style.AppTheme_TransparentDialogStyle_NoTitle);
        setCanceledOnTouchOutside(false);
        setIndeterminate(true);
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.dialog_loading);
    }
}
