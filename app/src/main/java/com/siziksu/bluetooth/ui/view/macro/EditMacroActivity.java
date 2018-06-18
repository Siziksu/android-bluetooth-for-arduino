package com.siziksu.bluetooth.ui.view.macro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.siziksu.bluetooth.R;
import com.siziksu.bluetooth.common.Constants;
import com.siziksu.bluetooth.common.function.Func;
import com.siziksu.bluetooth.common.utils.ColorUtils;
import com.siziksu.bluetooth.common.utils.MathUtils;
import com.siziksu.bluetooth.presenter.model.Macro;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditMacroActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.macroName)
    EditText macroName;
    @BindView(R.id.macroCommand)
    EditText macroCommand;
    @BindView(R.id.macroConfirmationCheckBox)
    CheckBox macroConfirmationCheckBox;
    @BindViews({R.id.macroRadioGrey, R.id.macroRadioBlue, R.id.macroRadioGreen,
                R.id.macroRadioOrange, R.id.macroRadioYellow, R.id.macroRadioRed,
                R.id.macroRadioPink, R.id.macroRadioLila, R.id.macroRadioSea})
    List<RadioButton> radioButtons;

    private Macro macro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_macro);
        checkIntentExtras();
        initializeViews();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_macro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_apply:
                Intent intent = new Intent();
                macro.name = macroName.getText().toString();
                macro.command = MathUtils.getByteFromUnsignedStringNumber(macroCommand.getText().toString());
                macro.confirmation = macroConfirmationCheckBox.isChecked();
                intent.putExtra(Constants.MACRO_EXTRA, macro);
                setResult(Activity.RESULT_OK, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Optional
    @OnClick({R.id.macroRadioGrey, R.id.macroRadioBlue, R.id.macroRadioGreen,
              R.id.macroRadioOrange, R.id.macroRadioYellow, R.id.macroRadioRed,
              R.id.macroRadioPink, R.id.macroRadioLila, R.id.macroRadioSea})
    public void onMacroRadioButtonClick(View view) {
        if (macro != null) {
            macro.color = ColorUtils.getMacroColorFromRadioButtonChecked(view);
        }
        Func.apply(radioButtons, button -> button.getId() != view.getId(), button -> button.setChecked(false));
    }

    private void checkIntentExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(Constants.MACRO_EXTRA)) {
            macro = bundle.getParcelable(Constants.MACRO_EXTRA);
        }
    }

    private void initializeViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        if (macro != null) {
            macroName.setText(macro.name);
            macroCommand.setText(String.valueOf(macro.command & 0xff));
            macroConfirmationCheckBox.setChecked(macro.confirmation);
            Func.apply(radioButtons, button -> button.getId() == ColorUtils.getRadioButtonFromColor(macro.color), button -> button.setChecked(true));
        }
    }
}
