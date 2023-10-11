package com.theme.keyboardthemeapp.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdSize;
import com.theme.keyboardthemeapp.AdsClass;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.R;

public class MainExitDialog extends Dialog {
    private final Activity activity;
    public ExitListener exitListener;

    public interface ExitListener {

        void onTryMore(MainExitDialog fancyTextDialog);
    }

    public MainExitDialog(Activity activity,Context context, ExitListener exitListener) {
        super(context);
        this.activity = activity;
        this.exitListener = exitListener;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_main_exit);
        ImageView IvNotNow = (ImageView) findViewById(R.id.IvNotNow);
        ImageView IvExit = (ImageView) findViewById(R.id.IvExit);
        ImageView IvTryMore = (ImageView) findViewById(R.id.IvTryMore);
        if (AdsClass.isInternetOn(getContext())) {
            AdsClass.showBanner(activity, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlBannerAdView), (RelativeLayout) findViewById(R.id.RlBannerAd), Constants.BannerAd,Constants.Show);
        }
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
