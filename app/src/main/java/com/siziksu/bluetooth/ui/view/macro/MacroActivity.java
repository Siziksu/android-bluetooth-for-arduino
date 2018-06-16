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
import android.widget.CheckBox;
import android.widget.EditText;

import com.siziksu.bluetooth.R;
import com.siziksu.bluetooth.common.Constants;
import com.siziksu.bluetooth.common.utils.MathUtils;
import com.siziksu.bluetooth.presenter.model.Macro;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MacroActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.macroName)
    EditText macroName;
    @BindView(R.id.macroCommand)
    EditText macroCommand;
    @BindView(R.id.macroConfirmationCheckBox)
    CheckBox macroConfirmationCheckBox;

    private Macro macro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macro);
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
                break;
        }
        return super.onOptionsItemSelected(item);
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
        }
    }
}
