package com.mapyo.limitnumberimagepicker;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mapyo.limitnumberimagepicker.databinding.ViewPickImageViewBinding;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class PickImageView extends RelativeLayout {
    private ViewPickImageViewBinding binding;

    private Uri imageUri;
    private int maxImageNumber;

    public PickImageView(Context context) {
        this(context, null);
    }

    public PickImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PickImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_pick_image_view, this, true);
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
    public void onEvent(PickImageRefreshEvent event) {
        refreshSelectedVisibility(imageUri, event.getPickImageUriList());
    }

    public void setUp(Uri imageUri, int size, int maxImageNumber, @NonNull List<Uri> selectedImageList) {
        this.imageUri = imageUri;
        this.maxImageNumber = maxImageNumber;

        ViewGroup.LayoutParams lp = binding.pickImage.getLayoutParams();
        lp.width = size;
        lp.height = size;

        Picasso.with(getContext())
                .load(imageUri)
                .fit()
                .centerCrop()
                .into(binding.pickImage);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = binding.selectedImageCount.getVisibility() == VISIBLE;
                selectImage(selected);
            }
        });

        refreshSelectedVisibility(imageUri, selectedImageList);
    }

    public void refreshSelectedVisibility(Uri imageUri, List<Uri> selectedImageList) {
        int selectedPosition = selectedImageList.indexOf(imageUri);

        if (selectedPosition == -1) {
            setUpUnSelectedImage(selectedImageList.size());
        } else {
            setUpSelectedImage(selectedPosition);
        }
    }

    private void selectImage(boolean selected) {
        PickImageEvent event = new PickImageEvent(imageUri, selected);
        EventBus.getDefault().post(event);
    }

    private void setUpSelectedImage(int selectedPosition) {
        binding.selectedImageCount.setVisibility(VISIBLE);
        binding.selectedImageCount.setText(selectedPosition);

        binding.pickImage.setAlpha(1f);
    }

    private void setUpUnSelectedImage(int selectedImageNumber) {
        binding.selectedImageCount.setVisibility(GONE);

        if (selectedImageNumber >= maxImageNumber) {
            binding.pickImage.setAlpha(0.3f);
        } else {
            binding.pickImage.setAlpha(1f);
        }
    }
}

