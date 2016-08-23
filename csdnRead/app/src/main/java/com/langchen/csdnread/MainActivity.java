package com.langchen.csdnread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.langchen.xlib.ui.pagefragment.PageFragment;
import com.langchen.csdnread.itemFactory.ArticleItemFactory;
import com.langchen.csdnread.presenter.ArticleRetrofitPagePresenter;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PageFragment fragment = (PageFragment) getFragmentManager().findFragmentById(R.id.fragment_page);
        fragment.setPagePresenter(new ArticleRetrofitPagePresenter(fragment));
        fragment.addItemFactory(new ArticleItemFactory());
        fragment.init();
    }
}


