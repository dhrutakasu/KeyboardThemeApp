package com.theme.keyboardthemeapp.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.theme.keyboardthemeapp.Constants;
import com.theme.keyboardthemeapp.Dialogs.MainExitDialog;
import com.theme.keyboardthemeapp.Dialogs.OverlayPermissionDialog;
import com.theme.keyboardthemeapp.Dialogs.SelectPhotoDialog;
import com.theme.keyboardthemeapp.MySharePref;
import com.theme.keyboardthemeapp.R;
import com.theme.keyboardthemeapp.UI.Adapters.ColorAdapter;
import com.theme.keyboardthemeapp.UI.Adapters.FontAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BackgroundActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final int CAMERA_RESULT = 92;
    private final int CAMERA_CODE = 95;
    private final int GALLERY_CODE = 98;
    private Context context;
    private ImageView ImgBack, ImgMore;
    private TextView TxtTitle;
    private ImageView IvBackgroundImg, IvBlackCover, IvMenuKeyboard, IvSearchKeyboard, IvEmojiKeyboard, IvClearText, IvShift, IvDelete, IvEnter, IvSpace;
    private TextView TxtQ, TxtW, TxtE, TxtR, TxtT, TxtY, TxtU, TxtI, TxtO, TxtP;
    private TextView TxtA, TxtS, TxtD, TxtF, TxtG, TxtH, TxtJ, TxtK, TxtL;
    private TextView TxtZ, TxtX, TxtC, TxtV, TxtB, TxtN, TxtM, TxtComma, TxtDot, Txt123;
    private ImageView IvClearBackground, IvBackground, IvOpacityView, IvChangeBg, IvFontChange, IvFontColor, IvChooseBackground;
    private RecyclerView RvFontColorList, RvFontList, RvBackgroundList;
    private TextView TxtTitleFont, TxtTitleSelectFont;
    private SeekBar SeekOpacityView;
    private TextView TxtPercentage, TxtTotalPercentage;
    private RelativeLayout RlOpacity, RlFontSelect, RlFontColor, RlBackgroundView, RlImage, RlOptions;
    private String file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        ImgMore = (ImageView) findViewById(R.id.ImgMore);
        TxtTitle = (TextView) findViewById(R.id.TxtTitle);
        IvBackgroundImg = (ImageView) findViewById(R.id.IvBackgroundImg);
        IvBlackCover = (ImageView) findViewById(R.id.IvBlackCover);
        IvMenuKeyboard = (ImageView) findViewById(R.id.IvMenuKeyboard);
        IvSearchKeyboard = (ImageView) findViewById(R.id.IvSearchKeyboard);
        IvEmojiKeyboard = (ImageView) findViewById(R.id.IvEmojiKeyboard);
        IvClearText = (ImageView) findViewById(R.id.IvClearText);
        TxtQ = (TextView) findViewById(R.id.TxtQ);
        TxtW = (TextView) findViewById(R.id.TxtW);
        TxtE = (TextView) findViewById(R.id.TxtE);
        TxtR = (TextView) findViewById(R.id.TxtR);
        TxtT = (TextView) findViewById(R.id.TxtT);
        TxtY = (TextView) findViewById(R.id.TxtY);
        TxtU = (TextView) findViewById(R.id.TxtU);
        TxtI = (TextView) findViewById(R.id.TxtI);
        TxtO = (TextView) findViewById(R.id.TxtO);
        TxtP = (TextView) findViewById(R.id.TxtP);
        TxtA = (TextView) findViewById(R.id.TxtA);
        TxtS = (TextView) findViewById(R.id.TxtS);
        TxtD = (TextView) findViewById(R.id.TxtD);
        TxtF = (TextView) findViewById(R.id.TxtF);
        TxtG = (TextView) findViewById(R.id.TxtG);
        TxtH = (TextView) findViewById(R.id.TxtH);
        TxtJ = (TextView) findViewById(R.id.TxtJ);
        TxtK = (TextView) findViewById(R.id.TxtK);
        TxtL = (TextView) findViewById(R.id.TxtL);
        TxtZ = (TextView) findViewById(R.id.TxtZ);
        TxtX = (TextView) findViewById(R.id.TxtX);
        TxtC = (TextView) findViewById(R.id.TxtC);
        TxtV = (TextView) findViewById(R.id.TxtV);
        TxtB = (TextView) findViewById(R.id.TxtB);
        TxtN = (TextView) findViewById(R.id.TxtN);
        TxtM = (TextView) findViewById(R.id.TxtM);
        Txt123 = (TextView) findViewById(R.id.Txt123);
        TxtDot = (TextView) findViewById(R.id.TxtDot);
        TxtComma = (TextView) findViewById(R.id.TxtComma);
        IvShift = (ImageView) findViewById(R.id.IvShift);
        IvDelete = (ImageView) findViewById(R.id.IvDelete);
        IvEnter = (ImageView) findViewById(R.id.IvEnter);
        IvSpace = (ImageView) findViewById(R.id.IvSpace);
        IvClearBackground = (ImageView) findViewById(R.id.IvClearBackground);
        IvOpacityView = (ImageView) findViewById(R.id.IvOpacityView);
        IvBackground = (ImageView) findViewById(R.id.IvBackground);
        RvFontColorList = (RecyclerView) findViewById(R.id.RvFontColorList);
        TxtTitleFont = (TextView) findViewById(R.id.TxtTitleFont);
        RvFontList = (RecyclerView) findViewById(R.id.RvFontList);
        TxtTitleSelectFont = (TextView) findViewById(R.id.TxtTitleSelectFont);
        IvChangeBg = (ImageView) findViewById(R.id.IvChangeBg);
        IvFontChange = (ImageView) findViewById(R.id.IvFontChange);
        IvFontColor = (ImageView) findViewById(R.id.IvFontColor);
        IvChooseBackground = (ImageView) findViewById(R.id.IvChooseBackground);
        RvBackgroundList = (RecyclerView) findViewById(R.id.RvBackgroundList);
        SeekOpacityView = (SeekBar) findViewById(R.id.SeekOpacityView);
        TxtPercentage = (TextView) findViewById(R.id.TxtPercentage);
        TxtTotalPercentage = (TextView) findViewById(R.id.TxtTotalPercentage);
        RlOpacity = (RelativeLayout) findViewById(R.id.RlOpacity);
        RlFontSelect = (RelativeLayout) findViewById(R.id.RlFontSelect);
        RlFontColor = (RelativeLayout) findViewById(R.id.RlFontColor);
        RlBackgroundView = (RelativeLayout) findViewById(R.id.RlBackgroundView);
        RlImage = (RelativeLayout) findViewById(R.id.RlImage);
        RlOptions = (RelativeLayout) findViewById(R.id.RlOptions);
    }

    private void initListeners() {
        ImgBack.setOnClickListener(this);
        ImgMore.setOnClickListener(this);
        IvChangeBg.setOnClickListener(this);
        IvFontChange.setOnClickListener(this);
        IvFontColor.setOnClickListener(this);
        IvOpacityView.setOnClickListener(this);
        IvBackground.setOnClickListener(this);
        IvClearBackground.setOnClickListener(this);
        IvChooseBackground.setOnClickListener(this);
        SeekOpacityView.setOnSeekBarChangeListener(this);
    }

    private void initActions() {
        ImgBack.setVisibility(View.VISIBLE);
        ImgMore.setVisibility(View.VISIBLE);
        TxtTitle.setText(R.string.app_name);
        IvBackground.setImageResource(R.drawable.backgroundchage_presed);
        IvOpacityView.setImageResource(R.drawable.opacity_unpresed);
        IvClearBackground.setImageResource(R.drawable.clear_unpresed);
        GotoFontColor(getResources().getColor(R.color.white));
        RlImage.setVisibility(View.VISIBLE);
        getVisibility(RlBackgroundView);

        TxtPercentage.setText(((new MySharePref(context).getPrefInt(MySharePref.TRANSPARENT, 0) * 100) / 255) + "%");
        IvBlackCover.setAlpha(((float) new MySharePref(context).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
        SeekOpacityView.setProgress(new MySharePref(context).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0));

        int color = new MySharePref(context).getPrefInt(MySharePref.TEXT_IS_COLOR_CODE, getResources().getColor(R.color.white));
        GotoFontColor(color);
        GotoFontTypeface();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        RvBackgroundList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        ColorAdapter adapter = new ColorAdapter(context, Constants.ColorsList, (position, ints) -> {
            TxtPercentage.setText(((new MySharePref(context).getPrefInt(MySharePref.TRANSPARENT, 0) * 100) / 255) + "%");
            IvBlackCover.setAlpha(((float) new MySharePref(context).getPrefInt(MySharePref.TRANSPARENT_BLACK_BG, 0)) / 255.0f);
            new MySharePref(context).putPrefBoolean(MySharePref.SAVE_IMAGE,true);
            if (position == 0) {
                ColorPickerDialogBuilder
                        .with(context)
                        .setTitle("Choose color")
                        .initialColor(getResources().getColor(R.color.white))
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(selectedColor -> {
                        })
                        .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
                            new MySharePref(context).putPrefInt(MySharePref.DEFAULT_BG_COLOR, selectedColor);
                            IvBackgroundImg.setImageBitmap(null);
                            IvBackgroundImg.setBackgroundColor(selectedColor);
                            if (IvClearBackground.getVisibility() == View.GONE) {
                                IvClearBackground.setVisibility(View.VISIBLE);
                            }
                            if (new File(getFilesDir().getAbsolutePath() + "/photo_save.jpeg").exists()) {
                                new File(getFilesDir().getAbsolutePath() + "/photo_save.jpeg").delete();
                            }
                        })
                        .setNegativeButton("cancel", (dialog, which) -> {
                        }).showColorEdit(true).setColorEditTextColor(getResources().getColor(R.color.purple_200)).build().show();

            } else {
                IvBackgroundImg.setImageBitmap(null);
                new MySharePref(context).putPrefInt(MySharePref.DEFAULT_BG_COLOR, ints[position]);
                IvBackgroundImg.setBackgroundColor(ints[position]);
                if (IvClearBackground.getVisibility() == View.GONE) {
                    IvClearBackground.setVisibility(View.VISIBLE);
                }
                if (new File(getFilesDir().getAbsolutePath() + "/photo_save.jpeg").exists()) {
                    new File(getFilesDir().getAbsolutePath() + "/photo_save.jpeg").delete();
                }
            }
        });
        RvBackgroundList.setAdapter(adapter);

        RvFontColorList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        ColorAdapter fontcoloradapter = new ColorAdapter(context, Constants.ColorsList, (position, ints) -> {
            new MySharePref(context).putPrefBoolean(MySharePref.ISCOLOR_CODE_CHANGE, true);

            if (position == 0) {
                ColorPickerDialogBuilder
                        .with(context)
                        .setTitle("Choose color")
                        .initialColor(getResources().getColor(R.color.white))
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(selectedColor -> {
                        })
                        .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
                            new MySharePref(context).putPrefInt(MySharePref.TEXT_IS_COLOR_CODE, selectedColor);
                            GotoFontColor(selectedColor);
                        })
                        .setNegativeButton("cancel", (dialog, which) -> {
                        }).showColorEdit(true).setColorEditTextColor(getResources().getColor(R.color.purple_200)).build().show();

            } else {
                new MySharePref(context).putPrefInt(MySharePref.TEXT_IS_COLOR_CODE, ints[position]);
                GotoFontColor(ints[position]);
            }
        });
        RvFontColorList.setAdapter(fontcoloradapter);

        RvFontList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        FontAdapter fontAdapter = new FontAdapter(context, Constants.FontList, new FontAdapter.FontListen() {
            @Override
            public void FontClick(int position) {
                new MySharePref(context).putPrefInt(MySharePref.BACKGROUND_FONT_STYLE, position);
                GotoFontTypeface();
            }
        });
        RvFontList.setAdapter(fontAdapter);
        if (new MySharePref(context).getPrefInt(MySharePref.DEFAULT_BG_COLOR, 0) != 0) {
            IvClearBackground.setVisibility(View.VISIBLE);
            IvBackgroundImg.setBackgroundColor(new MySharePref(context).getPrefInt(MySharePref.DEFAULT_BG_COLOR, 0));
        } else {
            IvClearBackground.setVisibility(View.GONE);
            if (new File(getFilesDir().getAbsolutePath() + "/photo_save.jpeg").exists()) {
                IvBackgroundImg.setImageBitmap(BitmapFactory.decodeFile(new File(getFilesDir().getAbsolutePath() + "/photo_save.jpeg").getAbsolutePath()));
            } else {
                IvBackgroundImg.setImageBitmap(BitmapFactory.decodeFile(Constants.getBackground(context, new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0)).getAbsolutePath()));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ImgBack:
                finish();
                break;
            case R.id.ImgMore:
                startActivity(new Intent(context, MoreSettingsActivity.class));
                break;
            case R.id.IvChangeBg:
                setPressButton(IvChangeBg, R.drawable.bgcolor_presed);
                RlOptions.setVisibility(View.VISIBLE);
                getVisibility(RlBackgroundView);
                break;
            case R.id.IvFontChange:
                setPressButton(IvFontChange, R.drawable.preview_font_presed);
                RlOptions.setVisibility(View.GONE);
                getVisibility(RlFontSelect);
                break;
            case R.id.IvFontColor:
                setPressButton(IvFontColor, R.drawable.preview_color_presed);
                RlOptions.setVisibility(View.GONE);
                getVisibility(RlFontColor);
                break;
            case R.id.IvOpacityView:
                setPressButton(IvChangeBg, R.drawable.bgcolor_presed);
                setPressSubButton(IvOpacityView, R.drawable.opacity_presed);
                IvOpacityView.setBackgroundResource(R.drawable.opacity_presed);
                IvBackground.setBackgroundResource(R.drawable.backgroundchage_unpresed);
                IvClearBackground.setBackgroundResource(R.drawable.clear_unpresed);
                RlImage.setVisibility(View.VISIBLE);
                getVisibility(RlOpacity);
                break;
            case R.id.IvBackground:
                setPressButton(IvChangeBg, R.drawable.bgcolor_presed);
                setPressSubButton(IvBackground, R.drawable.backgroundchage_presed);
                IvOpacityView.setBackgroundResource(R.drawable.opacity_unpresed);
                IvBackground.setBackgroundResource(R.drawable.backgroundchage_presed);
                IvClearBackground.setBackgroundResource(R.drawable.clear_unpresed);
                RlImage.setVisibility(View.VISIBLE);
                getVisibility(RlBackgroundView);
                break;
            case R.id.IvClearBackground:
                setPressButton(IvChangeBg, R.drawable.bgcolor_presed);
                setPressSubButton(IvClearBackground, R.drawable.clear_presed);
                if (new File(getFilesDir().getAbsolutePath() + "/photo_save.jpeg").exists()) {
                    new File(getFilesDir().getAbsolutePath() + "/photo_save.jpeg").delete();
                }
                new MySharePref(context).putPrefInt(MySharePref.DEFAULT_BG_COLOR, 0);
                IvBackgroundImg.setBackgroundColor(0);
                IvBackgroundImg.setImageBitmap(BitmapFactory.decodeFile(Constants.getBackground(context, new MySharePref(context).getPrefInt(MySharePref.DEFAULT_THEME, 0)).getAbsolutePath()));
                IvOpacityView.setBackgroundResource(R.drawable.opacity_unpresed);
                IvBackground.setBackgroundResource(R.drawable.backgroundchage_unpresed);
                IvClearBackground.setBackgroundResource(R.drawable.clear_presed);
                RlImage.setVisibility(View.VISIBLE);
                IvClearBackground.setVisibility(View.GONE);
                break;
            case R.id.IvChooseBackground:
                GotoChooseBg();
                break;
        }
    }

    private void setPressButton(ImageView ivChangeBg, int bgcolor_presed) {
        IvChangeBg.setImageResource(R.drawable.bgpressxml);
        IvFontChange.setImageResource(R.drawable.previewfontpressxml);
        IvFontColor.setImageResource(R.drawable.previewcolorpressxml);

        ivChangeBg.setImageResource(bgcolor_presed);
    }

    private void setPressSubButton(ImageView ivChangeBg, int bgcolor_presed) {
        IvOpacityView.setImageResource(R.drawable.opacity_unpresed);
        IvClearBackground.setImageResource(R.drawable.clear_xml);
        IvBackground.setImageResource(R.drawable.backgroundchage_unpresed);

        ivChangeBg.setImageResource(bgcolor_presed);
    }

    private void getVisibility(RelativeLayout rlBackgroundView) {
        RlBackgroundView.setVisibility(View.GONE);
        RlOpacity.setVisibility(View.GONE);
        RlFontColor.setVisibility(View.GONE);
        RlFontSelect.setVisibility(View.GONE);
        rlBackgroundView.setVisibility(View.VISIBLE);
    }

    private void GotoChooseBg() {
        final SelectPhotoDialog selectPhotoDialog = new SelectPhotoDialog(context, new SelectPhotoDialog.SelectPhotoListener() {
            @Override
            public void onLibrary(SelectPhotoDialog selectPhotoDialog) {
                Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent2.setType("image/*");
                startActivityForResult(intent2, GALLERY_CODE);
                selectPhotoDialog.dismiss();
            }

            @Override
            public void onTakePhoto(SelectPhotoDialog selectPhotoDialog) {
//                file = new File(getFilesDir().getAbsolutePath(), "photo_save.jpeg");
                File photoFile = createImageFile();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (photoFile != null) {
                    Uri photoURI = Uri.fromFile(photoFile);
                    Uri contentUri = FileProvider.getUriForFile(context, getPackageName() + ".provider", photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                }
                startActivityForResult(intent, CAMERA_CODE);
                selectPhotoDialog.dismiss();
            }
        });
        selectPhotoDialog.show();
        WindowManager.LayoutParams attributes = selectPhotoDialog.getWindow().getAttributes();
        Window DialogWindow = selectPhotoDialog.getWindow();
        attributes.copyFrom(DialogWindow.getAttributes());
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.gravity = Gravity.CENTER;
        DialogWindow.setAttributes(attributes);
    }

    private File createImageFile() {
        String imageFileName = "photo_save";
        File storageDir = getFilesDir();
        File image = new File(storageDir, imageFileName + ".jpeg");
        file = image.getAbsolutePath();
        return image;
    }

    private void GotoFontTypeface() {
        Typeface typeface;
        if (new MySharePref(context).getPrefInt(MySharePref.BACKGROUND_FONT_STYLE, 0) == 0) {
            try {
                typeface = Typeface.createFromAsset(getAssets(), "fontRob.ttf");
            } catch (Exception unused) {
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
            }
        } else {
            typeface = Typeface.createFromAsset(getAssets(), Constants.FontList[new MySharePref(context).getPrefInt(MySharePref.BACKGROUND_FONT_STYLE, 0)]);
        }

        TxtQ.setTypeface(typeface);
        TxtW.setTypeface(typeface);
        TxtE.setTypeface(typeface);
        TxtR.setTypeface(typeface);
        TxtT.setTypeface(typeface);
        TxtY.setTypeface(typeface);
        TxtU.setTypeface(typeface);
        TxtI.setTypeface(typeface);
        TxtO.setTypeface(typeface);
        TxtP.setTypeface(typeface);
        TxtA.setTypeface(typeface);
        TxtS.setTypeface(typeface);
        TxtD.setTypeface(typeface);
        TxtF.setTypeface(typeface);
        TxtG.setTypeface(typeface);
        TxtH.setTypeface(typeface);
        TxtJ.setTypeface(typeface);
        TxtK.setTypeface(typeface);
        TxtL.setTypeface(typeface);
        TxtZ.setTypeface(typeface);
        TxtX.setTypeface(typeface);
        TxtC.setTypeface(typeface);
        TxtV.setTypeface(typeface);
        TxtB.setTypeface(typeface);
        TxtN.setTypeface(typeface);
        TxtM.setTypeface(typeface);
        Txt123.setTypeface(typeface);
        TxtComma.setTypeface(typeface);
        TxtDot.setTypeface(typeface);
    }

    private void GotoFontColor(int color) {
        TxtQ.setTextColor(color);
        TxtW.setTextColor(color);
        TxtE.setTextColor(color);
        TxtR.setTextColor(color);
        TxtT.setTextColor(color);
        TxtY.setTextColor(color);
        TxtU.setTextColor(color);
        TxtI.setTextColor(color);
        TxtO.setTextColor(color);
        TxtP.setTextColor(color);
        TxtA.setTextColor(color);
        TxtS.setTextColor(color);
        TxtD.setTextColor(color);
        TxtF.setTextColor(color);
        TxtG.setTextColor(color);
        TxtH.setTextColor(color);
        TxtJ.setTextColor(color);
        TxtK.setTextColor(color);
        TxtL.setTextColor(color);
        TxtZ.setTextColor(color);
        TxtX.setTextColor(color);
        TxtC.setTextColor(color);
        TxtV.setTextColor(color);
        TxtB.setTextColor(color);
        TxtN.setTextColor(color);
        TxtM.setTextColor(color);
        Txt123.setTextColor(color);
        TxtComma.setTextColor(color);
        TxtDot.setTextColor(color);
        IvMenuKeyboard.setColorFilter(color);
        IvSearchKeyboard.setColorFilter(color);
        IvEmojiKeyboard.setColorFilter(color);
        IvClearText.setColorFilter(color);
        IvShift.setColorFilter(color);
        IvDelete.setColorFilter(color);
        IvClearText.setColorFilter(color);
        IvSpace.setColorFilter(color);
        IvEnter.setColorFilter(color);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        System.out.println("--- -- - -  progress :: " + i);
        new MySharePref(context).putPrefInt(MySharePref.TRANSPARENT, i);
        new MySharePref(context).putPrefInt(MySharePref.TRANSPARENT_BLACK_BG, i);
        TxtPercentage.setText(((new MySharePref(context).getPrefInt(MySharePref.TRANSPARENT, 0) * 100) / 255) + "%");
        if (new MySharePref(context).getPrefInt(MySharePref.TRANSPARENT, 0) > 127) {
            new MySharePref(context).putPrefInt(MySharePref.TRANSPARENT_TOP_BG, 255);
        } else if (new MySharePref(context).getPrefInt(MySharePref.TRANSPARENT, 0) > 40) {
            new MySharePref(context).putPrefInt(MySharePref.TRANSPARENT_TOP_BG, new MySharePref(context).getPrefInt(MySharePref.TRANSPARENT, 0) * 2);
        }
        IvBlackCover.setAlpha(((float) i) / 255.0f);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        try {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(file, options);

                            options.inSampleSize = Constants.calculateInSampleSize(options, Constants.width, Constants.height - 100);
                            options.inJustDecodeBounds = false;
                            Bitmap adjustImage = Constants.adjustImage(file, BitmapFactory.decodeFile(file, options));
                            FileOutputStream outputStream = new FileOutputStream(file);
                            adjustImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intent3 = new Intent(context, PhotoCropActivity.class);
                        intent3.putExtra(Constants.FILE_PATH, file);
                        startActivityForResult(intent3, CAMERA_RESULT);
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
            case GALLERY_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        Uri uri = data.getData();
                        String path = null;
                        try {
                            try {
                                path = Constants.getRealPathFromURI(getApplicationContext(), uri);
                            } catch (Exception unused) {
                                path = Constants.getPathFromUriLolipop(getApplicationContext(), uri);
                            }
                        } catch (Exception unused2) {
                            Toast.makeText(getApplicationContext(), "Load Image failed. Try again", Toast.LENGTH_LONG).show();
                        }
                        file = getFilesDir().getAbsolutePath() + "/photo_save.jpeg";
                        try {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(path, options);
                            options.inSampleSize = Constants.calculateInSampleSize(options, Constants.width, Constants.height - 100);
                            options.inJustDecodeBounds = false;
                            Bitmap adjustImageOrientation = Constants.adjustImage(path, BitmapFactory.decodeFile(path, options));
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            adjustImageOrientation.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (Exception unused3) {
                            unused3.printStackTrace();
                        }
                        Intent intent3 = new Intent(context, PhotoCropActivity.class);
                        intent3.putExtra(Constants.FILE_PATH, file);
                        startActivityForResult(intent3, CAMERA_RESULT);
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
            case CAMERA_RESULT:
                switch (resultCode) {
                    case RESULT_OK:
                        new MySharePref(context).putPrefInt(MySharePref.DEFAULT_BG_COLOR, 0);
                        Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/photo_save.jpeg");
                        IvBackgroundImg.setImageBitmap(bitmap);
                        if (IvClearBackground.getVisibility() == View.GONE) {
                            IvClearBackground.setVisibility(View.VISIBLE);
                        }
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
        }
    }


    @Override
    public void onBackPressed() {
        MainExitDialog mainExitDialog = new MainExitDialog(context, fancyTextDialog -> {
            finish();
        });
        mainExitDialog.show();
        WindowManager.LayoutParams attributes = mainExitDialog.getWindow().getAttributes();
        Window exitDialogWindow = mainExitDialog.getWindow();
        attributes.copyFrom(exitDialogWindow.getAttributes());
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.gravity = Gravity.CENTER;
        exitDialogWindow.setAttributes(attributes);
    }
}