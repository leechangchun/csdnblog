package com.langchen.csdnread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.langchen.csdnread.entity.Article;
import com.langchen.csdnread.itemFactory.ArticleItemFactory;
import com.langchen.csdnread.itemFactory.LoadMoreItemFactory;
import com.langchen.csdnread.presenter.ArticlePagePresenter;
import com.langchen.xlib.mvp.presenter.PagePresenter;
import com.langchen.xlib.mvp.view.PageView;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import com.langchen.xlib.ui.assemblyadapter.AssemblyAdapter;
import com.langchen.xlib.ui.assemblyadapter.OnLoadMoreListener;

public class MainActivity extends AppCompatActivity implements PageView {

    private ListView listView;
    PagePresenter pagePresenter;
    AssemblyAdapter adapter;
    PtrClassicFrameLayout mPtrFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        pagePresenter = new ArticlePagePresenter(this);
        ArrayList<Article> list = pagePresenter.getList();
        adapter = new AssemblyAdapter(list);
        adapter.addItemFactory(new ArticleItemFactory());
        /*pagePresenter = new ColumnPagePresenter(this);
        ArrayList<Column> list = pagePresenter.getList();
        adapter = new AssemblyAdapter(list);
        adapter.addItemFactory(new ColumnItemFactory());*/
        adapter.setLoadMoreItem(new LoadMoreItemFactory(new OnLoadMoreListener(){
            @Override
            public void onLoadMore(AssemblyAdapter adapter) {
                // 访问网络加载数据
                pagePresenter.loadMore();
            }
        }));

        listView.setAdapter(adapter);

        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pagePresenter.loadFirstPage();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
    }

    @Override
    public void loadComplete() {
        adapter.notifyDataSetChanged();
        mPtrFrame.refreshComplete();
    }

    @Override
    public void loadFirstPageComplete() {
        adapter.setDisableLoadMore(false);
    }

    @Override
    public void loadMoreSucc(boolean hasNextPage) {
        adapter.setLoadMoreEnd(!hasNextPage);
    }

    @Override
    public void loadMoreErr() {
        adapter.loadMoreFailed();
        adapter.setDisableLoadMore(true);
    }
}
