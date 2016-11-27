package com.mapyo.limitnumberimagepicker;

import android.net.Uri;

import java.util.List;

public class PickedImageEvent {
    private List<Uri> pickImageUriList;

    public PickedImageEvent(List<Uri> pickImageUriList) {
        this.pickImageUriList = pickImageUriList;
    }

    public List<Uri> getPickImageUriList() {
        return pickImageUriList;
    }
}
