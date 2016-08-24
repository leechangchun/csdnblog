package com.langchen.csdnread;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.langchen.csdnread.itemFactory.ArticleItemFactory;
import com.langchen.csdnread.presenter.ArticleRetrofitPagePresenter;
import com.langchen.xlib.ui.maintab.MainTab;
import com.langchen.xlib.ui.maintab.SimpleFragmentAdapter;
import com.langchen.xlib.ui.maintab.TopTab;
import com.langchen.xlib.ui.pagefragment.RecyclePageFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TopTab mainTab = (TopTab) findViewById(R.id.mainTab);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ArticleFragment());
        fragments.add(new ArticleFragment());
        fragments.add(new ArticleFragment());
        fragments.add(new ArticleFragment());

        List<String> titles = new ArrayList<>();
        titles.add("首页");
        titles.add("发现");
        titles.add("更多");
        titles.add("设置");

        mainTab.set(getSupportFragmentManager(),fragments,titles);
    }
}


