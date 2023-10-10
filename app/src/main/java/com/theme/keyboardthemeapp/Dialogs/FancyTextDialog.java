package com.theme.keyboardthemeapp.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.theme.keyboardthemeapp.AdsClass;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Activities.MainActivity;

public class FancyTextDialog extends Dialog {
    public FancyTextListener fancyTextListener;

    public interface FancyTextListener {

        void onCopy(FancyTextDialog fancyTextDialog);
        void onShare(FancyTextDialog fancyTextDialog);
        void onWhastapp(FancyTextDialog fancyTextDialog);
    }

    public FancyTextDialog(Context context, FancyTextListener fancyTextListener) {
        super(context);
        this.fancyTextListener = fancyTextListener;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_fancytext_share);
        ImageView IvFancyTextCancel = (ImageView) findViewById(R.id.IvFancyTextCancel);
        ImageView IvFancyTextCopy = (ImageView) findViewById(R.id.IvFancyTextCopy);
        ImageView IvFancyTextShare = (ImageView) findViewById(R.id.IvFancyTextShare);
        ImageView IvFancyTextWhatsapp = (ImageView) findViewById(R.id.IvFancyTextWhatsapp);
        if (AdsClass.isInternetOn(getContext())) {
            AdsClass.showNative250(getOwnerActivity(), Constants.NativaAds, findViewById(R.id.FlNative), Constants.Show);
        }
        IvFancyTextCopy.setOnClickListener(view -> {
            fancyTextListener.onCopy(this);
        });
        IvFancyTextShare.setOnClickListener(view -> {
            fancyTextListener.onShare(this);
        });
        IvFancyTextWhatsapp.setOnClickListener(view -> {
            fancyTextListener.onWhastapp(this);
        });
        IvFancyTextCancel.setOnClickListener(view -> {
            dismiss();
        });
    }
}
