package com.langchen.csdnread;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.langchen.csdnread.itemFactory.ArticleItemFactory;
import com.langchen.csdnread.presenter.ArticleRetrofitPagePresenter;
import com.langchen.xlib.ui.assemblyadapter.AssemblyRecyclerAdapter;
import com.langchen.xlib.ui.pagefragment.IPagePresenter;
import com.langchen.xlib.ui.pagefragment.RecyclePageFragment;

/**
 * Created by admin on 2016/8/24.
 */
public class ArticleFragment extends RecyclePageFragment {
    public IPagePresenter getPagePresenter(){
        return new ArticleRetrofitPagePresenter(this);
    }

    public RecyclerView.LayoutManager getLayoutManager(){
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public void addItemFactory(AssemblyRecyclerAdapter adapter) {
        adapter.addItemFactory(new ArticleItemFactory());
    }
}
