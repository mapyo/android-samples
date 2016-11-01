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
import android.util.Log;

import com.mapyo.limitnumberimagepicker.databinding.ActivityImagePickBinding;
import com.squareup.picasso.Picasso;

public class ImagePickActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LIMIT_NUMBER_IMAGE = "limit_number_image";
    private static final int FETCH_IMAGE_LIST_LOADER_ID = 1;

    private static final String ORDER_BY = MediaStore.Images.Media._ID + " DESC";
    private ActivityImagePickBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_pick);

        // 画像一覧をとってくる
        // cursorを使う
        getSupportLoaderManager().initLoader(FETCH_IMAGE_LIST_LOADER_ID, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(ImagePickActivity.this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, ORDER_BY);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        do {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                long id = cursor.getLong(fieldIndex);
                Log.d(getClass().getSimpleName(), cursor.getColumnName(i) + " : " + cursor.getString(i));
                if (i==0) {
                    Uri uri = ContentUris.withAppendedId( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                    setImage(uri);
                }
            }
        } while (cursor.moveToNext());
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
