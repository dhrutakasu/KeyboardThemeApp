package com.theme.keyboardthemeapp.Dialogs;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.theme.keyboardthemeapp.R;

public class MainExitDialog extends Dialog {
    public ExitListener exitListener;

    public interface ExitListener {

        void onTryMore(MainExitDialog fancyTextDialog);
    }

    public MainExitDialog(Context context, ExitListener exitListener) {
        super(context);
        this.exitListener = exitListener;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_main_exit);
        ImageView IvNotNow = (ImageView) findViewById(R.id.IvNotNow);
        ImageView IvExit = (ImageView) findViewById(R.id.IvExit);
        ImageView IvTryMore = (ImageView) findViewById(R.id.IvTryMore);

        IvNotNow.setOnClickListener(view -> {
            dismiss();
        });
        IvExit.setOnClickListener(view -> {
            exitListener.onTryMore(this);
        });
        IvTryMore.setOnClickListener(view -> {
            dismiss();
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=my+keyboard+app"));
            intent.addFlags(1208483840);
            try {
                getContext().startActivity(intent);
            } catch (ActivityNotFoundException unused) {
                getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=my+keyboard+app")));
            }
        });
    }
}
