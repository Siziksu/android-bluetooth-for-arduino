package com.siziksu.bluetooth.ui.view.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.siziksu.bluetooth.App;
import com.siziksu.bluetooth.R;
import com.siziksu.bluetooth.common.Constants;
import com.siziksu.bluetooth.common.utils.MetricsUtils;
import com.siziksu.bluetooth.presenter.main.MainPresenterContract;
import com.siziksu.bluetooth.presenter.main.MainViewContract;
import com.siziksu.bluetooth.ui.common.DialogFragmentHelper;
import com.siziksu.bluetooth.ui.common.ViewSlider;
import com.siziksu.bluetooth.ui.common.ViewSliderContract;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Optional;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements MainViewContract {

    @Inject
    MainPresenterContract<MainViewContract> presenter;
    @Inject
    ItemAdapterContract adapter;

    @BindView(R.id.toolbarConnection)
    Toolbar toolbarConnection;
    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.connectionView)
    View connectionView;
    @BindView(R.id.macrosViewContainer)
    View macrosViewContainer;
    @BindView(R.id.macrosView)
    View macrosView;
    @BindView(R.id.editMacrosButton)
    ImageButton editMacrosButton;
    @BindView(R.id.editKeepScreenOnButton)
    ImageButton editKeepScreenOnButton;
    @BindView(R.id.terminal)
    TextView terminal;
    @BindView(R.id.lastCommand)
    TextView lastCommand;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindViews({R.id.m1, R.id.m2, R.id.m3, R.id.m4, R.id.m5, R.id.m6, R.id.m7, R.id.m8,
                R.id.m9, R.id.m10, R.id.m11, R.id.m12, R.id.m13, R.id.m14, R.id.m15, R.id.m16,
                R.id.m17, R.id.m18, R.id.m19, R.id.m20, R.id.m21, R.id.m22, R.id.m23, R.id.m24,
                R.id.m25, R.id.m26, R.id.m27, R.id.m28, R.id.m29, R.id.m30, R.id.m31, R.id.m32})
    List<Button> buttons;

    private ViewSliderContract viewSlider;
    private boolean alreadyStarted;
    private MenuItem item;
    private boolean macrosByName = true;
    private boolean keepScreenOn = false;
    private Spanned last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.get().getApplicationComponent().inject(this);
        initializeViews();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.register(this);
        if (!alreadyStarted) {
            alreadyStarted = true;
            presenter.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unregister();
        viewSlider.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (viewSlider.onBackAvailable()) {
            bottomNavigation.setSelectedItemId(R.id.action_connection);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_connection, menu);
        item = menu.findItem(R.id.action_connect);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_connect:
                presenter.onConnectButtonClick();
                return true;
            case R.id.action_clear_terminal:
                terminal.setText("");
                return true;
            case R.id.action_refresh:
                presenter.refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showDeviceList(List<String> list) {
        adapter.showItems(list);
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return this;
    }

    @Override
    public void showLoadingDialog() {
        DialogFragmentHelper.showLoadingDialog(this);
    }

    @Override
    public void hideLoadingDialog() {
        DialogFragmentHelper.hideLoadingDialog(this);
    }

    @Override
    public void writeInTerminal(String message, boolean main) {
        if (main) {
            terminal.append(Html.fromHtml((terminal.length() == 0 ? "" : Constants.TERMINAL_CRLF) + message));
            terminal.requestFocus();
        }
        last = Html.fromHtml(message);
        lastCommand.setText(last);
    }

    @Override
    public void onConnectionUpdate(boolean connected) {
        item.setIcon(ContextCompat.getDrawable(this, connected ? R.drawable.plugged : R.drawable.unplugged));
        item.setTitle(getString(connected ? R.string.action_disconnect : R.string.action_connect));
    }

    @Optional
    @OnTouch({R.id.m1, R.id.m2, R.id.m3, R.id.m4, R.id.m5, R.id.m6, R.id.m7, R.id.m8,
              R.id.m9, R.id.m10, R.id.m11, R.id.m12, R.id.m13, R.id.m14, R.id.m15, R.id.m16,
              R.id.m17, R.id.m18, R.id.m19, R.id.m20, R.id.m21, R.id.m22, R.id.m23, R.id.m24,
              R.id.m25, R.id.m26, R.id.m27, R.id.m28, R.id.m29, R.id.m30, R.id.m31, R.id.m32})
    public boolean onMacroButtonTouch(View view, MotionEvent event) {
        if (macrosByName) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_UP:
                    presenter.onMacroButtonTouch(view.getId(), event.getAction());
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    @Optional
    @OnClick({R.id.m1, R.id.m2, R.id.m3, R.id.m4, R.id.m5, R.id.m6, R.id.m7, R.id.m8,
              R.id.m9, R.id.m10, R.id.m11, R.id.m12, R.id.m13, R.id.m14, R.id.m15, R.id.m16,
              R.id.m17, R.id.m18, R.id.m19, R.id.m20, R.id.m21, R.id.m22, R.id.m23, R.id.m24,
              R.id.m25, R.id.m26, R.id.m27, R.id.m28, R.id.m29, R.id.m30, R.id.m31, R.id.m32})
    public void onMacroButtonClick(View view) {
        if (!macrosByName) {
            presenter.onMacroEditButtonClick(view.getId());
        }
    }

    @OnClick({R.id.editKeepScreenOnButton, R.id.editMacrosButton})
    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.editKeepScreenOnButton:
                keepScreenOn = !keepScreenOn;
                updateKeepScreenOnButton();
                break;
            case R.id.editMacrosButton:
                macrosByName = !macrosByName;
                updateEditMacrosButtonAndButtonsText();
                break;
            default:
                break;
        }
    }

    private void initializeViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbarConnection);
        presenter.setButtons(buttons);
        viewSlider = new ViewSlider(connectionView, macrosView, new MetricsUtils(this));
        terminal.setText("");
        terminal.setMovementMethod(new ScrollingMovementMethod());
        adapter = new ItemAdapter(this, new ItemManager());
        adapter.init(
                (v, position) -> {
                    String device = adapter.getItem(position);
                    switch (v.getId()) {
                        case R.id.deviceCardView:
                            presenter.onDeviceClick(position, device);
                            break;
                        default:
                            break;
                    }
                }
        );
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter.getAdapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(adapter.getLayoutManager());
        DividerItemDecoration decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.divider);
        if (drawable != null) {
            decoration.setDrawable(drawable);
        }
        recyclerView.addItemDecoration(decoration);
        bottomNavigation.setOnNavigationItemSelectedListener(this::setViewPagerSelectedItem);
        bottomNavigation.setSelectedItemId(R.id.action_connection);
        updateKeepScreenOnButton();
        updateEditMacrosButtonAndButtonsText();
    }

    private boolean setViewPagerSelectedItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_connection:
                viewSlider.animateToLeft();
                return true;
            case R.id.action_macros:
                viewSlider.animateToRight();
                return true;
            default:
                return false;
        }
    }

    private void updateViews() {
        ((ViewGroup) macrosView).removeAllViews();
        ((ViewGroup) macrosView).addView(getLayoutInflater().inflate(R.layout.view_macros, ((ViewGroup) macrosViewContainer), false));
        ButterKnife.bind(this);
        viewSlider.onConfigurationChanged();
        presenter.setButtons(buttons);
        lastCommand.setText(last);
        updateKeepScreenOnButton();
        updateEditMacrosButtonAndButtonsText();
    }

    private void updateKeepScreenOnButton() {
        editKeepScreenOnButton.setBackground(ContextCompat.getDrawable(this, !keepScreenOn ? R.drawable.keep_on : R.drawable.keep_off));
        presenter.updateScreenOnState(keepScreenOn);
    }

    private void updateEditMacrosButtonAndButtonsText() {
        editMacrosButton.setBackground(ContextCompat.getDrawable(this, macrosByName ? R.drawable.macros_edit : R.drawable.macros));
        presenter.updateButtonsText(macrosByName);
    }
}
