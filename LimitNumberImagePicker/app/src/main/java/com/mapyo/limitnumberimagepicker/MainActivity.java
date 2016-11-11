package com.mapyo.limitnumberimagepicker;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mapyo.limitnumberimagepicker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
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
    }
}
