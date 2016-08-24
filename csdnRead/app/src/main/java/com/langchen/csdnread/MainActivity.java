package com.langchen.csdnread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.langchen.csdnread.itemFactory.ArticleItemFactory;
import com.langchen.csdnread.presenter.ArticleRetrofitPagePresenter;
import com.langchen.xlib.ui.pagefragment.RecyclePageFragment;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclePageFragment fragment = (RecyclePageFragment) getFragmentManager().findFragmentById(R.id.fragment_page);
        fragment.setPagePresenter(new ArticleRetrofitPagePresenter(fragment));
        fragment.addItemFactory(new ArticleItemFactory());
        fragment.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        fragment.init();
    }
}


