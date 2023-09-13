package com.theme.keyboardthemeapp.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Activities.MainActivity;
import com.theme.keyboardthemeapp.UI.Activities.SetDefaultKeyboardActivity;

public class OverlayPermissionDialog extends Dialog {
    private final MainActivity activity;
    public PermissionListener permissionListener;

    public interface PermissionListener {

        void onPermission(OverlayPermissionDialog keyboardPermissionDialog);
    }

    public OverlayPermissionDialog(MainActivity activity, Context context, PermissionListener permissionListener) {
        super(context);
        this.activity = activity;
        this.permissionListener = permissionListener;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_overlay_permission);
        ImageView IvPermissionCancel = (ImageView) findViewById(R.id.IvPermissionCancel);
        ImageView IvPermissionOk = (ImageView) findViewById(R.id.IvPermissionOk);

        IvPermissionCancel.setOnClickListener(view -> {
            permissionListener.onPermission(this);
        });
        IvPermissionCancel.setOnClickListener(view -> {
            dismiss();
        });
    }
}
