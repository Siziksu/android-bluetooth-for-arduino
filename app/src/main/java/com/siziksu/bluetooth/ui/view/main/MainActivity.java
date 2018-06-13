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
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.siziksu.bluetooth.App;
import com.siziksu.bluetooth.R;
import com.siziksu.bluetooth.common.Constants;
import com.siziksu.bluetooth.common.utils.MetricsUtils;
import com.siziksu.bluetooth.presenter.main.MainPresenterContract;
import com.siziksu.bluetooth.presenter.main.MainViewContract;
import com.siziksu.bluetooth.ui.common.AnimationHelper;
import com.siziksu.bluetooth.ui.common.DialogFragmentHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Optional;
import io.reactivex.annotations.Nullable;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements MainViewContract {

    @Inject
    MainPresenterContract<MainViewContract> presenter;
    @Inject
    ItemAdapterContract adapter;

    @BindView(R.id.toolbarConnection)
    Toolbar toolbarConnection;
    @Nullable
    @BindView(R.id.toolbarMacros)
    Toolbar toolbarMacros;
    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.connectionView)
    View connectionView;
    @BindView(R.id.macrosViewContainer)
    View macrosViewContainer;
    @BindView(R.id.macrosView)
    View macrosView;
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
    Button[] buttons;

    private AnimationHelper animationHelper;
    private boolean alreadyStarted;
    private MenuItem item;
    private boolean macrosByName = true;

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
        animationHelper.onDestroy();
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
                break;
        }
        return super.onOptionsItemSelected(item);
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
    public void writeInTerminal(String message) {
        terminal.append(Html.fromHtml((terminal.length() == 0 ? "" : Constants.CRLF) + message));
        terminal.requestFocus();
        lastCommand.setText(Html.fromHtml(message));
    }

    @Override
    public void onConnectionUpdate(boolean connected) {
        item.setIcon(ContextCompat.getDrawable(this, connected ? R.drawable.plugged : R.drawable.unplugged));
        item.setTitle(getString(connected ? R.string.action_disconnect : R.string.action_connect));
    }

    @Optional
    @OnClick({R.id.m1, R.id.m2, R.id.m3, R.id.m4, R.id.m5, R.id.m6, R.id.m7, R.id.m8,
              R.id.m9, R.id.m10, R.id.m11, R.id.m12, R.id.m13, R.id.m14, R.id.m15, R.id.m16,
              R.id.m17, R.id.m18, R.id.m19, R.id.m20, R.id.m21, R.id.m22, R.id.m23, R.id.m24,
              R.id.m25, R.id.m26, R.id.m27, R.id.m28, R.id.m29, R.id.m30, R.id.m31, R.id.m32})
    public void onMacroButtonClick(View view) {
        presenter.onMacroButtonClick(view.getId());
    }

    @Optional
    @OnLongClick({R.id.m1, R.id.m2, R.id.m3, R.id.m4, R.id.m5, R.id.m6, R.id.m7, R.id.m8,
                  R.id.m9, R.id.m10, R.id.m11, R.id.m12, R.id.m13, R.id.m14, R.id.m15, R.id.m16,
                  R.id.m17, R.id.m18, R.id.m19, R.id.m20, R.id.m21, R.id.m22, R.id.m23, R.id.m24,
                  R.id.m25, R.id.m26, R.id.m27, R.id.m28, R.id.m29, R.id.m30, R.id.m31, R.id.m32})
    public boolean onMacroButtonLongClick(View view) {
        presenter.onMacroButtonLongClick(view.getId());
        return true;
    }

    private void initializeViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbarConnection);
        initializeToolbarMacros();
        presenter.setButtons(buttons);
        animationHelper = new AnimationHelper(connectionView, macrosView, new MetricsUtils(this));
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
    }

    private boolean setViewPagerSelectedItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_connection:
                animationHelper.animateToLeft();
                return true;
            case R.id.action_macros:
                animationHelper.animateToRight();
                return true;
            default:
                return false;
        }
    }

    private void updateViews() {
        ((ViewGroup) macrosView).removeAllViews();
        ((ViewGroup) macrosView).addView(getLayoutInflater().inflate(R.layout.view_macros, ((ViewGroup) macrosViewContainer), false));
        ButterKnife.bind(this);
        initializeToolbarMacros();
        animationHelper.onConfigurationChanged();
        presenter.setButtons(buttons);
        presenter.updateButtonsText(macrosByName);
    }

    private void initializeToolbarMacros() {
        if (toolbarMacros != null) {
            toolbarMacros.inflateMenu(R.menu.menu_main_macro);
            updateMenuItem(toolbarMacros.getMenu().findItem(R.id.action_macros_by));
            toolbarMacros.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.action_macros_by:
                        macrosByName = !macrosByName;
                        updateMenuItem(item);
                        return true;
                }
                return false;
            });
        }
    }

    private void updateMenuItem(MenuItem item) {
        item.setIcon(ContextCompat.getDrawable(this, macrosByName ? R.drawable.commands : R.drawable.macros));
        item.setTitle(getString(macrosByName ? R.string.action_macros_by_command : R.string.action_macros_by_name));
        presenter.updateButtonsText(macrosByName);
    }
}
