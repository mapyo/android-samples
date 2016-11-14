package com.mapyo.limitnumberimagepicker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.mapyo.limitnumberimagepicker.databinding.ActivityImagePickBinding;

public class ImagePickerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LIMIT_NUMBER_IMAGE = "limit_number_image";
    private static final int LOADER_ID_FETCH_IMAGE_LIST = 1;

    private ActivityImagePickBinding binding;

    private int limitImageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_pick);

        getSupportLoaderManager().restartLoader(LOADER_ID_FETCH_IMAGE_LIST, null, this);

        limitImageNumber = getIntent().getExtras().getInt(LIMIT_NUMBER_IMAGE, 0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID_FETCH_IMAGE_LIST:
               return new DeviceImageLoader(this, null);
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
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static class IntentBuilder {
        private Intent intent;

        IntentBuilder(Context context, int limitNumberImage) {
            intent = new Intent(context, ImagePickerActivity.class);
            intent.putExtra(LIMIT_NUMBER_IMAGE, limitNumberImage);
        }

        Intent build() {
            return intent;
        }
    }
}
