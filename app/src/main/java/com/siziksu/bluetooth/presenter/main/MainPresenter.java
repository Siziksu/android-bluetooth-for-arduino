package com.siziksu.bluetooth.presenter.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.siziksu.bluetooth.R;
import com.siziksu.bluetooth.common.Constants;
import com.siziksu.bluetooth.common.function.Functions;
import com.siziksu.bluetooth.common.utils.DatesUtils;
import com.siziksu.bluetooth.domain.bluetooth.BluetoothDomainContract;
import com.siziksu.bluetooth.domain.bluetooth.BluetoothDomainPresenterContract;
import com.siziksu.bluetooth.domain.model.MacroDomainModel;
import com.siziksu.bluetooth.domain.preferences.PreferencesDomainContract;
import com.siziksu.bluetooth.domain.preferences.PreferencesDomainPresenterContract;
import com.siziksu.bluetooth.presenter.mapper.MacroMapper;
import com.siziksu.bluetooth.presenter.model.Macro;
import com.siziksu.bluetooth.ui.common.DialogFragmentHelper;
import com.siziksu.bluetooth.ui.view.macro.MacroActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public final class MainPresenter implements MainPresenterContract<MainViewContract>, BluetoothDomainPresenterContract, PreferencesDomainPresenterContract {

    @Inject
    BluetoothDomainContract<BluetoothDomainPresenterContract> bluetoothDomain;
    @Inject
    PreferencesDomainContract<PreferencesDomainPresenterContract> preferencesDomain;

    private MainViewContract view;
    private List<Macro> macros = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();
    private boolean macrosByName;

    public MainPresenter(BluetoothDomainContract<BluetoothDomainPresenterContract> bluetoothDomain,
                         PreferencesDomainContract<PreferencesDomainPresenterContract> preferencesDomain) {
        this.bluetoothDomain = bluetoothDomain;
        this.preferencesDomain = preferencesDomain;
    }

    @Override
    public void register(MainViewContract view) {
        this.view = view;
        bluetoothDomain.register(this);
        preferencesDomain.register(this);
    }

    @Override
    public void unregister() {
        view = null;
        bluetoothDomain.unregister();
        preferencesDomain.unregister();
    }

    @Override
    public void setButtons(Button[] buttons) {
        this.buttons.clear();
        this.buttons.addAll(Arrays.asList(buttons));
    }

    @Override
    public void updateButtonsText(boolean macrosByName) {
        this.macrosByName = macrosByName;
        updateMacros();
    }

    @Override
    public void start() {
        bluetoothDomain.start();
        preferencesDomain.getMacros();
    }

    @Override
    public void refresh() {
        bluetoothDomain.refresh();
    }

    @Override
    public void onConnectButtonClick() {
        bluetoothDomain.onConnectButtonClick();
    }

    @Override
    public void onDeviceClick(int position, String device) {
        bluetoothDomain.onDeviceClick(position, device);
    }

    @Override
    public void onMacroButtonClick(int resId) {
        Functions.apply(buttons, button -> button.getId() == resId,
                        button -> {
                            Macro macro = macros.get(buttons.indexOf(button));
                            if (macro.confirmation) {
                                DialogFragmentHelper.showConfirmationDialog(
                                        view.getAppCompatActivity(),
                                        R.string.main_confirmation_dialog_message,
                                        R.string.main_confirmation_dialog_accept,
                                        aVoid -> sendCommand(macro),
                                        R.string.main_confirmation_dialog_cancel,
                                        aVoid -> {}
                                );
                            } else {
                                sendCommand(macro);
                            }
                        });
    }

    private void sendCommand(Macro macro) {
        bluetoothDomain.sendCommand(macro != null ? macro.command : "");
    }

    @Override
    public void onMacroButtonLongClick(int resId) {
        editMacro(resId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.MACRO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                if (extras.containsKey(Constants.MACRO_EXTRA)) {
                    Macro macro = extras.getParcelable(Constants.MACRO_EXTRA);
                    if (macro != null) {
                        updateMacro(macro);
                    }
                }
            }
        }
    }

    @Override
    public void write(String message, boolean error) {
        if (view != null) {
            String currentDate = DatesUtils.getTimeString();
            String terminalDateColor = view.getAppCompatActivity().getString(R.string.terminal_date);
            String terminalEntryColor = view.getAppCompatActivity().getString(R.string.terminal_entry);
            String terminalWarningColor = view.getAppCompatActivity().getString(R.string.terminal_warning);
            String date = String.format(Constants.TERMINAL_DATE, terminalDateColor, currentDate);
            String entry = String.format(Constants.TERMINAL_ENTRY, (!error ? terminalEntryColor : terminalWarningColor), message);
            view.writeInTerminal(date + entry);
        }
    }

    @Override
    public void showLoadingDialog() {
        if (view != null) {
            view.showLoadingDialog();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (view != null) {
            view.hideLoadingDialog();
        }
    }

    @Override
    public void showDeviceList(List<String> list) {
        if (view != null) {
            view.showDeviceList(list);
        }
    }

    @Override
    public void onConnectionUpdate(boolean connected) {
        if (view != null) {
            view.onConnectionUpdate(connected);
        }
    }

    @Override
    public void onMacros(List<MacroDomainModel> list) {
        this.macros.clear();
        this.macros.addAll(new MacroMapper().map(list));
        updateMacros();
    }

    private void updateMacros() {
        if (!macros.isEmpty()) {
            for (int i = 0; i < buttons.size(); i++) {
                updateButtonData(i);
            }
        }
    }

    private void updateMacro(Macro macro) {
        macros.get(macro.id).name = macro.name;
        macros.get(macro.id).command = macro.command;
        macros.get(macro.id).confirmation = macro.confirmation;
        updateButtonData(macro.id);
        preferencesDomain.setMacros(new MacroMapper().unMap(macros));
    }

    private void updateButtonData(int index) {
        buttons.get(index).setText(macrosByName ? macros.get(index).name : macros.get(index).command);
        buttons.get(index).setSelected(!macros.get(index).command.isEmpty());
    }

    private void editMacro(int resId) {
        Functions.apply(buttons, button -> button.getId() == resId,
                        button -> {
                            Macro macro = macros.get(buttons.indexOf(button));
                            if (view != null) {
                                Intent intent = new Intent(view.getAppCompatActivity(), MacroActivity.class);
                                intent.putExtra(Constants.MACRO_EXTRA, macro);
                                view.getAppCompatActivity().startActivityForResult(intent, Constants.MACRO_REQUEST_CODE);
                            }
                        });
    }
}
