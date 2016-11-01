package com.mapyo.limitnumberimagepicker;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.mapyo.limitnumberimagepicker.databinding.ActivityImagePickBinding;
import com.squareup.picasso.Picasso;

public class ImagePickActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LIMIT_NUMBER_IMAGE = "limit_number_image";
    private static final int LOADER_ID_FETCH_IMAGE_LIST = 1;

    private static final String ORDER_BY = MediaStore.Images.Media._ID + " DESC";
    private ActivityImagePickBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_pick);

        // 画像一覧をとってくる
        // cursorを使う
        getSupportLoaderManager().initLoader(LOADER_ID_FETCH_IMAGE_LIST, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
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
                showImages(cursor);
        }
    }

    private void showImages(Cursor cursor) {
        int i = 0;
        cursor.moveToFirst();
        do {
            int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
            long id = cursor.getLong(fieldIndex);
            Uri uri = ContentUris.withAppendedId( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            L.d(uri.toString());
            if (i == 1) {
                setImage(uri);
            }

            i++;
        } while (cursor.moveToNext());

        cursor.close();
    }

    private void setImage(Uri uri) {
        Picasso.with(this)
                .load(uri)
                .into(binding.image);
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
