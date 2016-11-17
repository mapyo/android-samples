package com.mapyo.limitnumberimagepicker;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mapyo.limitnumberimagepicker.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MAX_SPAN_COUNT = 3;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.numberOfImage.setText("3");

        binding.pickStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int limitNumberImage = Integer.valueOf(binding.numberOfImage.getText().toString());
                Intent intent = new ImagePickerActivity.IntentBuilder(MainActivity.this, limitNumberImage).build();
                startActivity(intent);
            }
        });

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    @UiThread
    public void onEvent(PickedImageEvent event) {
        setUpPickedImageList(event.getPickImageUriList());
    }

    private void setUpPickedImageList(List<Uri> pickedImageUriList) {

    }

    private class PickedImageAdapter extends RecyclerView.Adapter<PickedImageAdapter.ViewHolder> {
        private Context context;
        private List<Uri> pickedImageUriList;

        public PickedImageAdapter(Context context) {
            this.context = context;
            pickedImageUriList = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(context);
            int size = parent.getWidth() / MAX_SPAN_COUNT;

            return new ViewHolder(imageView, size);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setUp(pickedImageUriList.get(position));
        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private int size;

            public ViewHolder(View itemView, int size) {
                super(itemView);
                this.size = size;
            }

            public void setUp(Uri imageUri) {
                ImageView imageView = (ImageView) itemView;
                ViewGroup.LayoutParams lp = imageView.getLayoutParams();
                lp.width = size;
                lp.height = size;

                Picasso.with(getApplicationContext())
                        .load(imageUri)
                        .fit()
                        .centerCrop()
                        .into(imageView);

            }
        }

    }

}
