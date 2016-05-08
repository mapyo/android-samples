package com.mapyo.appbarlayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.tab_layout) TabLayout mTabLayout;
    @BindView(R.id.viewpager) ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    private class MainPagerAdapter extends FragmentPagerAdapter {
        private static final int TAB_COUNT = 2;

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new MainFragment.Builder(position).build();
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "TAB" + position;
        }
    }

    public static class MainFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
        @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_top, container, false);
            ButterKnife.bind(this, view);

            final int position = getArguments().getInt(ARG_SECTION_NUMBER);

            setUpRecyclerView(position);

            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    setUpRecyclerView(position);
                }
            });
            return view;
        }

        private void setUpRecyclerView(int position) {
            mRecyclerView.setHasFixedSize(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(new RecyclerAdapter(getContext(), createData(position)));

            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 1秒
                        Thread.sleep(1000 * 1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mSwipeRefreshLayout.isRefreshing()) {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    });
                }
            }).start();
        }

        private List<String> createData(int position) {
            List<String> data = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                data.add("TAB" + position + ":" + String.valueOf(i));
            }
            return data;
        }


        public static class Builder {
            private Bundle mArgs;

            public Builder(int sectionNumber) {
                mArgs = new Bundle();
                mArgs.putInt(ARG_SECTION_NUMBER, sectionNumber);
            }

            public Fragment build() {
                MainFragment fragment = new MainFragment();
                fragment.setArguments(mArgs);
                return fragment;
            }
        }
    }
}
