package com.mapyo.recyclerviewgridlayoutdrag;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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

        final SampleImageAdapter adapter = new SampleImageAdapter(this, imageResList);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recyclerView.setHasFixedSize(true);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();
                adapter.notifyItemMoved(fromPos, toPos);
                return true;
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.scale_up);
                    set.setTarget(viewHolder.itemView);
                    set.start();
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                // todo 移動が終わった時の処理。APIを叩いたり
                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.scale_down);
                set.setTarget(viewHolder.itemView);
                set.start();
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
            }


        });
        helper.attachToRecyclerView(binding.recyclerView);
        binding.recyclerView.addItemDecoration(helper);

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
