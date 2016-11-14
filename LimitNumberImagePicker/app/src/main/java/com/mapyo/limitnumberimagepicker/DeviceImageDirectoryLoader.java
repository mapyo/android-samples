package com.mapyo.limitnumberimagepicker;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

public class DeviceImageDirectoryLoader extends CursorLoader {
    private static final String[] PROJECTION = { MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media._ID };
    private static final String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";
    private static final String BUCKET_ORDER_BY = "MAX(datetaken) DESC";

    public DeviceImageDirectoryLoader(Context context) {
        super(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, PROJECTION,  BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);
    }
}
