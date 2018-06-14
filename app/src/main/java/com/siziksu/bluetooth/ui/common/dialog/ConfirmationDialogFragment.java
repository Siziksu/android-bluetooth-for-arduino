package com.siziksu.bluetooth.ui.common.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.siziksu.bluetooth.R;
import com.siziksu.bluetooth.common.function.Functions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmationDialogFragment extends DialogFragment {

    public static final String MESSAGE_KEY = "message";
    public static final String BUTTON_POSITIVE_KEY = "positive";
    public static final String BUTTON_NEGATIVE_KEY = "negative";

    @BindView(R.id.dialogConfirmationMessage)
    TextView message;
    @BindView(R.id.dialogConfirmationPositive)
    Button buttonPositive;
    @BindView(R.id.dialogConfirmationNegative)
    Button buttonCancel;

    private Functions.Consumer<Void> positive;
    private Functions.Consumer<Void> negative;
    private String messageString;
    private String positiveString;
    private String negativeString;

    public ConfirmationDialogFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (arguments.containsKey(MESSAGE_KEY)) {
                messageString = getArguments().getString(MESSAGE_KEY);
            }
            if (arguments.containsKey(BUTTON_POSITIVE_KEY)) {
                positiveString = getArguments().getString(BUTTON_POSITIVE_KEY);
            }
            if (arguments.containsKey(BUTTON_NEGATIVE_KEY)) {
                negativeString = getArguments().getString(BUTTON_NEGATIVE_KEY);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.dialog_confirmation, container);
        ButterKnife.bind(this, inflatedView);
        if (messageString != null) {
            message.setText(messageString);
        }
        if (positiveString != null) {
            buttonPositive.setText(positiveString);
        }
        if (negativeString != null) {
            buttonCancel.setText(negativeString);
        }
        return inflatedView;
    }

    @Override
    public int getTheme() {
        return R.style.AppTheme_AppCompatDialogStyle_NoTitle;
    }

    @Override
    public void onDestroyView() {
        Dialog dialog = getDialog();
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @OnClick(R.id.dialogConfirmationPositive)
    public void onPositiveButtonClick() {
        if (positive != null) {
            positive.accept(null);
        }
        dismiss();
    }

    @OnClick(R.id.dialogConfirmationNegative)
    public void onNegativeButtonClick() {
        if (negative != null) {
            negative.accept(null);
        }
        dismiss();
    }

    public void setCallback(Functions.Consumer<Void> positive, Functions.Consumer<Void> negative) {
        this.positive = positive;
        this.negative = negative;
    }
}
