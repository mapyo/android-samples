package com.mapyo.limitnumberimagepicker;

import android.net.Uri;

import java.util.List;

public class SelectedImageEvent {
    private List<Uri> pickImageUriList;

    public SelectedImageEvent(List<Uri> pickImageUriList) {
        this.pickImageUriList = pickImageUriList;
    }

    public List<Uri> getPickImageUriList() {
        return pickImageUriList;
    }
}
