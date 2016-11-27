package com.mapyo.limitnumberimagepicker;

import android.net.Uri;

public class PickImageEvent {
    private Uri imageUri;
    private boolean selected;

    public PickImageEvent(Uri imageUri, boolean selected) {
        this.imageUri = imageUri;
        this.selected = selected;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public boolean isSelected() {
        return selected;
    }
}
