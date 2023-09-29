package com.theme.keyboardthemeapp.ModelClass;

public class StickerModel {
    public boolean isAvailable;
    public boolean isSelected;
    public String path;
    String total;
    public int type;

    public StickerModel(String str, boolean z, int i, String str2) {
        this.path = str;
        this.isAvailable = z;
        this.type = i;
        this.total = str2;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }
}
