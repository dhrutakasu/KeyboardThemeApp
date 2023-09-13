package com.theme.keyboardthemeapp.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Activities.SetDefaultKeyboardActivity;

public class KeyboardPermissionDialog extends Dialog {
    private final SetDefaultKeyboardActivity activity;
    public PermissionListener permissionListener;

    public interface PermissionListener {

        void onPermission(KeyboardPermissionDialog keyboardPermissionDialog);
    }

    public KeyboardPermissionDialog(SetDefaultKeyboardActivity activity, Context context, PermissionListener permissionListener) {
        super(context);
        this.activity = activity;
        this.permissionListener = permissionListener;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_permission);
        ImageView IvKeyboardScreen = (ImageView) findViewById(R.id.IvKeyboardScreen);
        ImageView IvGotIt = (ImageView) findViewById(R.id.IvGotIt);

        if (Constants.enableKeyboard){
            Glide.with(activity).load(Integer.valueOf(R.drawable.enable_keyboard_permission)).into(IvKeyboardScreen);
        }else {
            IvKeyboardScreen.setImageResource(R.drawable.change_keyboard_permission);
        }
        IvGotIt.setOnClickListener(view -> {
            permissionListener.onPermission(this);
        });
    }
}
