package com.mapyo.limitnumberimagepicker;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.UiThread;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class PickImageListView extends RecyclerView {
    static final int MAX_SPAN_COUNT = 3;

    private PickImageAdapter adapter;

    public PickImageListView(Context context) {
        this(context, null);
    }

    public PickImageListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PickImageListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        adapter = new PickImageAdapter(getContext());
        setAdapter(adapter);
        setLayoutManager(new GridLayoutManager(context, MAX_SPAN_COUNT));
        setHasFixedSize(true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        EventBus.getDefault().unregister(this);
        super.onDetachedFromWindow();
    }
    @Subscribe
    @UiThread
    public void onEvent(PickImageEvent event) {
        if (event.isSelected()) {
            adapter.addPickImageUriList(event.getImageUri());
        } else {
            adapter.removePickImageUriList(event.getImageUri());
        }

        adapter.refresh();
    }

    public void setUp(Cursor cursor, int limitImageNumber) {
        adapter.setCursor(cursor);
        adapter.setLimitImageNumber(limitImageNumber);
    }

    class PickImageAdapter extends RecyclerView.Adapter<PickImageAdapter.ViewHolder> {
        private Context context;
        private Cursor cursor;
        private int limitImageNumber;

        private List<Uri> pickImageUriList;

        private PickImageAdapter(Context context) {
            this.context = context;
            pickImageUriList = new ArrayList<>();
        }

        private void setCursor(Cursor cursor) {
            if (this.cursor == cursor) {
                return;
            }

            if (this.cursor != null) {
                this.cursor.close();
            }

            this.cursor = cursor;

            notifyDataSetChanged();

        }

        private Uri getItemUri(int position) {
            if (cursor == null || !cursor.moveToPosition(position)) {
                return null;
            }

            int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
            long id = cursor.getLong(fieldIndex);
            return ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
        }

        @Override
        public PickImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            PickImageView pickImageView = new PickImageView(context);
            int size = parent.getWidth()  / MAX_SPAN_COUNT;

            return new ViewHolder(pickImageView, size, limitImageNumber);
        }

        @Override
        public void onBindViewHolder(PickImageAdapter.ViewHolder holder, int position) {
            holder.setUp(getItemUri(position), pickImageUriList);
        }

        @Override
        public int getItemCount() {
            if (cursor != null) {
                return cursor.getCount();
            }

            return 0;
        }

        private void setLimitImageNumber(int limitImageNumber) {
            this.limitImageNumber = limitImageNumber;
        }

        private List<Uri> getPickImageUriList() {
            return pickImageUriList;
        }

        private void addPickImageUriList(Uri imageUri) {
            if (pickImageUriList.size() < limitImageNumber) {
                pickImageUriList.add(imageUri);
            }
        }

        private void removePickImageUriList(Uri imageUri) {
            pickImageUriList.remove(imageUri);
        }

        private void refresh() {
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final int limitImageNumber;
            private int size;

            ViewHolder(View view, int size, int limitImageNumber) {
                super(view);
                this.size = size;
                this.limitImageNumber = limitImageNumber;
            }

            public void setUp(Uri itemUri, List<Uri> pickImageUriList) {
                PickImageView pickImageView = (PickImageView) itemView;
                pickImageView.setUp(itemUri, size, limitImageNumber, pickImageUriList);
            }
        }
    }
}
