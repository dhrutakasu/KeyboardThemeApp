package com.theme.keyboardthemeapp.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.theme.keyboardthemeapp.R;

public class SelectPhotoDialog extends Dialog {
    public SelectPhotoListener selectPhotoListener;

    public interface SelectPhotoListener {

        void onLibrary(SelectPhotoDialog selectPhotoDialog);
        void onTakePhoto(SelectPhotoDialog selectPhotoDialog);
    }

    public SelectPhotoDialog(Context context, SelectPhotoListener selectPhotoListener) {
        super(context);
        this.selectPhotoListener = selectPhotoListener;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_select_photo);
        TextView TxtTakePhoto = (TextView) findViewById(R.id.TxtTakePhoto);
        TextView TxtLibrary = (TextView) findViewById(R.id.TxtLibrary);
        TextView TxtCancel = (TextView) findViewById(R.id.TxtCancel);

        TxtCancel.setOnClickListener(view -> {
         dismiss();
        });
        TxtTakePhoto.setOnClickListener(view -> {
            selectPhotoListener.onTakePhoto(this);
        });
        TxtLibrary.setOnClickListener(view -> {
            selectPhotoListener.onLibrary(this);
        });
    }
}
