package com.theme.keyboardthemeapp.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.Dialogs.KeyboardPermissionDialog;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;

public class SetDefaultKeyboardActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView IvEnableKeyboard, IvActivateKeyboard;
    private TextView TxtTitle, TxtEnableKeyboard, TxtActivateKeyboard, TxtMainBottom;
    private boolean isEnableKeyboard;
    private boolean isActivateKeyboard;
    private InputMethodManager imeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_default_keyborad);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvEnableKeyboard = (ImageView) findViewById(R.id.IvEnableKeyboard);
        IvActivateKeyboard = (ImageView) findViewById(R.id.IvActivateKeyboard);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        TxtEnableKeyboard = (TextView) findViewById(R.id.TxtEnableKeyboard);
        TxtActivateKeyboard = (TextView) findViewById(R.id.TxtActivateKeyboard);
        TxtMainBottom = (TextView) findViewById(R.id.TxtMainBottom);
    }

    private void initListeners() {
        IvEnableKeyboard.setOnClickListener(this);
        IvActivateKeyboard.setOnClickListener(this);
    }

    private void initActions() {
//        imchange = new InputMethodChangedReceiver(getApplicationContext(), true);
        Typeface createFromAsset = Typeface.createFromAsset(getAssets(), "FontSec.otf");
        Typeface createFromAsset2 = Typeface.createFromAsset(getAssets(), "Artheavy.otf");
        Typeface createFromAsset3 = Typeface.createFromAsset(getAssets(), "FontMedium.otf");
        TxtTitle.setTypeface(createFromAsset);
        TxtMainBottom.setTypeface(createFromAsset2);
        TxtEnableKeyboard.setTypeface(createFromAsset3);
        TxtActivateKeyboard.setTypeface(createFromAsset3);

        isEnableKeyboard = Constants.IsEnableKeyboard(context);
        isActivateKeyboard = Constants.IsActivateKeyboard(context);
        if (isEnableKeyboard) {
            TxtEnableKeyboard.setTextColor(Color.parseColor("#CCCCCC"));
            TxtActivateKeyboard.setTextColor(Color.parseColor("#363f46"));
            IvEnableKeyboard.setImageResource(R.drawable.enable_keyboard_press);
            IvActivateKeyboard.setImageResource(R.drawable.switch_keyboard_unpress);
        } else {
            TxtEnableKeyboard.setTextColor(Color.parseColor("#363f46"));
            TxtActivateKeyboard.setTextColor(Color.parseColor("#CCCCCC"));
            IvEnableKeyboard.setImageResource(R.drawable.enable_keyboard_unpress);
            IvActivateKeyboard.setImageResource(R.drawable.switch_keyboard_press);
        }
        IvEnableKeyboard.setEnabled(!isEnableKeyboard);
        boolean z = this.isEnableKeyboard;
        if (!z) {
            IvActivateKeyboard.setEnabled(isActivateKeyboard);
        } else {
            IvActivateKeyboard.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvEnableKeyboard:
                GotoEnableKeyboard();
                break;
            case R.id.IvActivateKeyboard:
                GotoActivateKeyboard();
                break;
        }
    }

    private void GotoEnableKeyboard() {
        if (new MySharePref(context).getPrefBoolean(MySharePref.ENABLE_KEYBOARD, true)) {
            Constants.enableKeyboard = true;
            KeyboardPermissionDialog keyboardPermissionDialog = new KeyboardPermissionDialog(SetDefaultKeyboardActivity.this, context, (KeyboardPermissionDialog permissionDialog) -> {
                permissionDialog.dismiss();
                new MySharePref(context).putPrefBoolean(MySharePref.ENABLE_KEYBOARD, false);
                Intent enableIntent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
                enableIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(enableIntent);
            });
            keyboardPermissionDialog.show();
            WindowManager.LayoutParams attributes = keyboardPermissionDialog.getWindow().getAttributes();
            Window exitDialogWindow = keyboardPermissionDialog.getWindow();
            attributes.copyFrom(exitDialogWindow.getAttributes());
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
            attributes.gravity = Gravity.BOTTOM;
            exitDialogWindow.setAttributes(attributes);
        } else {
            Intent enableIntent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
            enableIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(enableIntent);
//            startActivityForResult(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS), 7);
        }
//        HindiUtils.timer.purge();
//        HindiUtils.timer = null;
//        HindiUtils.timer = new Timer();
//        if (SetKeypadKeyBoardActivityGreen.this.imchange != null) {
//            SetKeypadKeyBoardActivityGreen.this.imchange.cancel();
//        }
//        SetKeypadKeyBoardActivityGreen.this.imchange = null;
//        SetKeypadKeyBoardActivityGreen.this.imchange = new InputMethodChangedReceiver(SetKeypadKeyBoardActivityGreen.this.getApplicationContext(), true);
//        HindiUtils.timer.scheduleAtFixedRate(SetKeypadKeyBoardActivityGreen.this.imchange, 500, 500);

    }

    private void GotoActivateKeyboard() {
        imeManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imeManager == null) {
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
        } else if (new MySharePref(context).getPrefBoolean(MySharePref.ACTIVATE_KEYBOARD, true)) {
            Constants.enableKeyboard = false;
            KeyboardPermissionDialog keyboardPermissionDialog = new KeyboardPermissionDialog(SetDefaultKeyboardActivity.this, context, (KeyboardPermissionDialog permissionDialog) -> {
                permissionDialog.dismiss();
                new MySharePref(context).putPrefBoolean(MySharePref.ACTIVATE_KEYBOARD, false);
                imeManager.showInputMethodPicker();
            });
            keyboardPermissionDialog.show();
            WindowManager.LayoutParams attributes = keyboardPermissionDialog.getWindow().getAttributes();
            Window exitDialogWindow = keyboardPermissionDialog.getWindow();
            attributes.copyFrom(exitDialogWindow.getAttributes());
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
            attributes.gravity = Gravity.BOTTOM;
            exitDialogWindow.setAttributes(attributes);
        } else {
            imeManager.showInputMethodPicker();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        boolean isEnabled = Constants.IsEnableKeyboard(context);
        isEnableKeyboard = isEnabled;
        if (isEnabled) {
//            try {
//                if (imchange != null) {
//                    imchange.cancel();
//                }
//            } catch (Exception unused) {
//            }
        }
        if (isEnableKeyboard) {
            TxtEnableKeyboard.setTextColor(Color.parseColor("#CCCCCC"));
            TxtActivateKeyboard.setTextColor(Color.parseColor("#363f46"));
            IvEnableKeyboard.setImageResource(R.drawable.enable_keyboard_press);
            IvActivateKeyboard.setImageResource(R.drawable.switch_keyboard_unpress);
        } else {
            TxtEnableKeyboard.setTextColor(Color.parseColor("#363f46"));
            TxtActivateKeyboard.setTextColor(Color.parseColor("#CCCCCC"));
            IvEnableKeyboard.setImageResource(R.drawable.enable_keyboard_unpress);
            IvActivateKeyboard.setImageResource(R.drawable.switch_keyboard_press);
        }
        boolean isSet = Constants.IsActivateKeyboard(context);
        isActivateKeyboard = isSet;
        boolean enableKeyboard = isEnableKeyboard;
        if (!enableKeyboard) {
            IvEnableKeyboard.setEnabled(true);
        } else if (!isSet) {
            IvEnableKeyboard.setEnabled(false);
            IvActivateKeyboard.setEnabled(!isActivateKeyboard);
        } else {
            setResult(-1);
            finish();
            super.onWindowFocusChanged(hasFocus);
        }
    }
}