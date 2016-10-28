package com.mapyo.limitnumberimagepicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ImagePickActivity extends AppCompatActivity {
    private static final String LIMIT_NUMBER_IMAGE = "limit_number_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pick);
    }

    public static class IntentBuilder {
        private Intent mIntent;

        public IntentBuilder(Context context, int limitNumberImage) {
            mIntent = new Intent(context, ImagePickActivity.class);
            mIntent.putExtra(LIMIT_NUMBER_IMAGE, limitNumberImage);
        }

        public Intent build() {
            return mIntent;
        }
    }
}
