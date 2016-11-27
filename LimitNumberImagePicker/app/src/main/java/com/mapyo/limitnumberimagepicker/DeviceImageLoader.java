package com.mapyo.limitnumberimagepicker;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

public class DeviceImageLoader extends CursorLoader {
    private static final String ORDER_BY = MediaStore.Images.Media._ID + " DESC";

    public DeviceImageLoader(Context context, String bucketId) {
        super(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                bucketId == null ? null : MediaStore.Images.Media.BUCKET_ID + " = ?", // selection
                bucketId == null ? null : new String[]{bucketId}, // selectionArgs
                ORDER_BY);
    }
}
