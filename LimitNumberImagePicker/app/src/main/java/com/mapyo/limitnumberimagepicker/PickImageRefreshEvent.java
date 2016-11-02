package com.mapyo.limitnumberimagepicker;

import android.net.Uri;

import java.util.List;

public class PickImageRefreshEvent {
    private List<Uri> pickImageUriList;

    public PickImageRefreshEvent(List<Uri> pickImageUriList) {
        this.pickImageUriList = pickImageUriList;
    }

    public List<Uri> getPickImageUriList() {
        return pickImageUriList;
    }
}
