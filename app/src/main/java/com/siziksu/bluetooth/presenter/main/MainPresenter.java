package com.siziksu.bluetooth.presenter.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.siziksu.bluetooth.R;
import com.siziksu.bluetooth.common.Constants;
import com.siziksu.bluetooth.common.utils.DatesUtils;
import com.siziksu.bluetooth.domain.bluetooth.BluetoothDomainContract;
import com.siziksu.bluetooth.domain.bluetooth.MainBluetoothDomainContract;
import com.siziksu.bluetooth.domain.model.MacroDomainModel;
import com.siziksu.bluetooth.domain.preferences.MainPreferencesDomainContract;
import com.siziksu.bluetooth.domain.preferences.PreferencesDomainContract;
import com.siziksu.bluetooth.presenter.mapper.MacroMapper;
import com.siziksu.bluetooth.presenter.model.Macro;
import com.siziksu.bluetooth.ui.view.macro.MacroActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public final class MainPresenter implements MainPresenterContract<MainViewContract>, MainBluetoothDomainContract, MainPreferencesDomainContract {

    @Inject
    BluetoothDomainContract<MainBluetoothDomainContract> bluetoothDomain;
    @Inject
    PreferencesDomainContract<MainPreferencesDomainContract> preferencesDomain;

    private MainViewContract view;
    private List<Macro> list = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();

    public MainPresenter(BluetoothDomainContract<MainBluetoothDomainContract> bluetoothDomain,
                         PreferencesDomainContract<MainPreferencesDomainContract> preferencesDomain) {
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
    public void setButtons(Button[] macros) {
        this.buttons.clear();
        this.buttons.addAll(Arrays.asList(macros));
    }

    @Override
    public void updateButtonsText() {
        setButtonsText();
    }

    @Override
    public void start() {
        bluetoothDomain.start();
        preferencesDomain.getMacros();
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
        Macro macro = getMacro(resId);
        bluetoothDomain.sendCommand(macro != null ? macro.command : "");
    }

    @Override
    public void onMacroButtonLongClick(int resId) {
        editMacro(resId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.MACRO_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    if (extras.containsKey(Constants.MACRO_ID_EXTRA)
                        && extras.containsKey(Constants.MACRO_NAME_EXTRA)
                        && extras.containsKey(Constants.MACRO_COMMAND_EXTRA)) {
                        int index = extras.getInt(Constants.MACRO_ID_EXTRA);
                        buttons.get(index).setText(extras.getString(Constants.MACRO_NAME_EXTRA));
                        list.get(index).name = extras.getString(Constants.MACRO_NAME_EXTRA);
                        list.get(index).command = extras.getString(Constants.MACRO_COMMAND_EXTRA);
                        preferencesDomain.setMacros(new MacroMapper().unMap(list));
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
        this.list.clear();
        this.list.addAll(new MacroMapper().map(list));
        setButtonsText();
    }

    private void setButtonsText() {
        if (!list.isEmpty()) {
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setText(list.get(i).name);
            }
        }
    }

    private Macro getMacro(int resId) {
        switch (resId) {
            case R.id.m1:
                return list.get(0);
            case R.id.m2:
                return list.get(1);
            case R.id.m3:
                return list.get(2);
            case R.id.m4:
                return list.get(3);
            case R.id.m5:
                return list.get(4);
            case R.id.m6:
                return list.get(5);
            case R.id.m7:
                return list.get(6);
            case R.id.m8:
                return list.get(7);
            case R.id.m9:
                return list.get(8);
            case R.id.m10:
                return list.get(9);
            case R.id.m11:
                return list.get(10);
            case R.id.m12:
                return list.get(11);
            case R.id.m13:
                return list.get(12);
            case R.id.m14:
                return list.get(13);
            case R.id.m15:
                return list.get(14);
            case R.id.m16:
                return list.get(15);
            case R.id.m17:
                return list.get(16);
            case R.id.m18:
                return list.get(17);
            case R.id.m19:
                return list.get(18);
            case R.id.m20:
                return list.get(19);
            case R.id.m21:
                return list.get(20);
            case R.id.m22:
                return list.get(21);
            case R.id.m23:
                return list.get(22);
            case R.id.m24:
                return list.get(23);
            case R.id.m25:
                return list.get(24);
            case R.id.m26:
                return list.get(25);
            case R.id.m27:
                return list.get(26);
            case R.id.m28:
                return list.get(27);
            case R.id.m29:
                return list.get(28);
            case R.id.m30:
                return list.get(29);
            case R.id.m31:
                return list.get(30);
            case R.id.m32:
                return list.get(31);
            default:
                return null;
        }
    }

    private void editMacro(int resId) {
        Macro macro;
        switch (resId) {
            case R.id.m1:
                macro = list.get(0);
                break;
            case R.id.m2:
                macro = list.get(1);
                break;
            case R.id.m3:
                macro = list.get(2);
                break;
            case R.id.m4:
                macro = list.get(3);
                break;
            case R.id.m5:
                macro = list.get(4);
                break;
            case R.id.m6:
                macro = list.get(5);
                break;
            case R.id.m7:
                macro = list.get(6);
                break;
            case R.id.m8:
                macro = list.get(7);
                break;
            case R.id.m9:
                macro = list.get(8);
                break;
            case R.id.m10:
                macro = list.get(9);
                break;
            case R.id.m11:
                macro = list.get(10);
                break;
            case R.id.m12:
                macro = list.get(11);
                break;
            case R.id.m13:
                macro = list.get(12);
                break;
            case R.id.m14:
                macro = list.get(13);
                break;
            case R.id.m15:
                macro = list.get(14);
                break;
            case R.id.m16:
                macro = list.get(15);
                break;
            case R.id.m17:
                macro = list.get(16);
                break;
            case R.id.m18:
                macro = list.get(17);
                break;
            case R.id.m19:
                macro = list.get(18);
                break;
            case R.id.m20:
                macro = list.get(19);
                break;
            case R.id.m21:
                macro = list.get(20);
                break;
            case R.id.m22:
                macro = list.get(21);
                break;
            case R.id.m23:
                macro = list.get(22);
                break;
            case R.id.m24:
                macro = list.get(23);
                break;
            case R.id.m25:
                macro = list.get(24);
                break;
            case R.id.m26:
                macro = list.get(25);
                break;
            case R.id.m27:
                macro = list.get(26);
                break;
            case R.id.m28:
                macro = list.get(27);
                break;
            case R.id.m29:
                macro = list.get(28);
                break;
            case R.id.m30:
                macro = list.get(29);
                break;
            case R.id.m31:
                macro = list.get(30);
                break;
            case R.id.m32:
                macro = list.get(31);
                break;
            default:
                macro = list.get(0);
                break;
        }
        if (view != null) {
            Intent intent = new Intent(view.getAppCompatActivity(), MacroActivity.class);
            intent.putExtra(Constants.MACRO_EXTRA, macro);
            view.getAppCompatActivity().startActivityForResult(intent, Constants.MACRO_REQUEST_CODE);
        }
    }
}
