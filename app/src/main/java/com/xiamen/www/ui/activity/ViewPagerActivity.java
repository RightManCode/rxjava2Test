package com.xiamen.www.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.xiamen.www.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/2/6.
 */

public class ViewPagerActivity extends AppCompatActivity {
    ViewPager viewPager;
    List<ImageView> viewList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        viewPager = findViewById(R.id.view_pager);
        initViewData();
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);
    }

    private void initViewData() {
        viewList = new ArrayList<>();

        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ad));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        viewList.add(imageView);

        ImageView imageView1 = new ImageView(this);
        imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ad1));
        imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
        viewList.add(imageView1);

        ImageView imageView2 = new ImageView(this);
        imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ad3));
        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
        viewList.add(imageView2);
    }

}
