package com.mapyo.limitnumberimagepicker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.mapyo.limitnumberimagepicker.databinding.ActivityImagePickBinding;

public class ImagePickActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LIMIT_NUMBER_IMAGE = "limit_number_image";
    private static final int LOADER_ID_FETCH_IMAGE_LIST = 1;

    private static final String ORDER_BY = MediaStore.Images.Media._ID + " DESC";
    private ActivityImagePickBinding binding;

    private int limitImageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_pick);

        // 画像一覧をとってくる
        // cursorを使う
        getSupportLoaderManager().initLoader(LOADER_ID_FETCH_IMAGE_LIST, null, this);
        limitImageNumber = getIntent().getExtras().getInt(LIMIT_NUMBER_IMAGE, 0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID_FETCH_IMAGE_LIST:
                return new CursorLoader(ImagePickActivity.this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, ORDER_BY);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_ID_FETCH_IMAGE_LIST:
                if (cursor != null) {
                    binding.pickImageListView.setUp(cursor, limitImageNumber);
                }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

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
