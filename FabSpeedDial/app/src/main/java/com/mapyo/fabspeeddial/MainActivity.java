package com.mapyo.fabspeeddial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab_main)
    View fabMain;

    @BindView(R.id.background)
    View background;

    @BindView(R.id.fab1)
    View fab1;

    @BindView(R.id.fab2)
    View fab2;

    private Animation rotateForward, rotateBackward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        rotateForward = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_forward_animation);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_backward_animation);

        closeFab();
    }

    @OnClick(R.id.fab_main)
    void clickFab() {
        if (background.getVisibility() == View.VISIBLE) {
            closeFab();
        } else {
            openFab();
        }
    }

    void openFab() {
        background.setVisibility(View.VISIBLE);
        fab1.setVisibility(View.VISIBLE);
        fab2.setVisibility(View.VISIBLE);
        fabMain.startAnimation(rotateForward);
    }

    void closeFab() {
        background.setVisibility(View.GONE);
        fab1.setVisibility(View.GONE);
        fab2.setVisibility(View.GONE);
        fabMain.startAnimation(rotateBackward);
    }
}
