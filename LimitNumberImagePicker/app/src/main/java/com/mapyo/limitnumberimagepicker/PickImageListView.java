package com.mapyo.limitnumberimagepicker;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

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
        setHasFixedSize(false);
    }

    public void setCursor(Cursor cursor) {
        adapter.setCursor(cursor);
    }

    class PickImageAdapter extends RecyclerView.Adapter<PickImageAdapter.ViewHolder> {
        // todo 不要になったら消す
        private Context context;
        private Cursor cursor;

        private PickImageAdapter(Context context) {
            this.context = context;
        }

        private void setCursor(Cursor cursor) {
            // todo cursorをクローズする処理を書く
            // ref. https://gist.github.com/skyfishjy/443b7448f59be978bc59#file-cursorrecyclerviewadapter-java-L104
            this.cursor = cursor;
            notifyItemInserted(cursor.getCount() - 1);
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
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            int size = parent.getWidth()  / MAX_SPAN_COUNT;
            return new ViewHolder(imageView, size);
        }

        @Override
        public void onBindViewHolder(PickImageAdapter.ViewHolder holder, int position) {
            holder.setUp(getItemUri(position));
        }

        @Override
        public int getItemCount() {
            if (cursor != null) {
                return cursor.getCount();
            }

            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private int size;

            ViewHolder(View view, int size) {
                super(view);
                imageView = (ImageView) view;
                this.size = size;
            }

            public void setUp(Uri itemUri) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

                lp.width = size;
                lp.height = size;
                imageView.setLayoutParams(lp);

                Picasso.with(getContext())
                        .load(itemUri)
                        .fit()
                        .centerCrop()
                        .into(imageView);
            }
        }
    }
}
