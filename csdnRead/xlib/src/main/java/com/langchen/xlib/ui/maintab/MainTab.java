package com.langchen.xlib.ui.maintab;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.langchen.xlib.R;

import java.util.List;

/**
 * Created by admin on 2016/8/24.
 * 用于主页底部tab
 */
public class MainTab extends RelativeLayout {
    private ViewPager viewPager;
    private TabLayout tableLayout;
    public MainTab(Context context) {
        super(context);
        initView();
    }

    public MainTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView(){
        View.inflate(getContext(), R.layout.main_tab, this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tableLayout = (TabLayout) findViewById(R.id.tabLayout);
    }

    public void set(FragmentManager fm, List<Fragment> fragments, List<String> titles){

        SimpleFragmentAdapter adapter = new SimpleFragmentAdapter(fm, getContext(), fragments, titles);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.size());
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
        for (String title : titles) {
            tableLayout.addTab(tableLayout.newTab().setText(title));
        }
        tableLayout.setupWithViewPager(viewPager);
    }
}
