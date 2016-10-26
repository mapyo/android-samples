package com.mapyo.recyclerviewgridlayoutdrag;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mapyo.recyclerviewgridlayoutdrag.databinding.ActivityMainBinding;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        List<Integer> imageResList = Arrays.asList(
                R.drawable.sample1,
                R.drawable.sample2,
                R.drawable.sample3,
                R.drawable.sample4,
                R.drawable.sample5);

        SampleImageAdapter adapter = new SampleImageAdapter(this, imageResList);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recyclerView.setHasFixedSize(true);
    }

    class SampleImageAdapter extends RecyclerView.Adapter<SampleImageAdapter.ViewHolder> {
        private Context context;
        private List<Integer> imageResList;

        private SampleImageAdapter(Context context, List<Integer> imageResList) {
            this.context = context;
            this.imageResList = imageResList;
        }

        @Override
        public SampleImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            return new ViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(SampleImageAdapter.ViewHolder holder, int position) {
            holder.imageView.setImageResource(imageResList.get(position));
        }

        @Override
        public int getItemCount() {
            return imageResList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            ViewHolder(View view) {
                super(view);
                imageView = (ImageView) view;
            }
        }
    }
}
