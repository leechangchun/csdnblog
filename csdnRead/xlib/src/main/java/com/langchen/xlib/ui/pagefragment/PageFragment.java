package com.langchen.xlib.ui.pagefragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.langchen.xlib.R;
import com.langchen.xlib.ui.assemblyadapter.AssemblyAdapter;
import com.langchen.xlib.ui.assemblyadapter.AssemblyItemFactory;
import com.langchen.xlib.ui.assemblyadapter.OnLoadMoreListener;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by admin on 2016/8/23.
 * 显示列表的fragment
 * 包含  分页 上拉加载  下拉刷新
 */
public class PageFragment extends Fragment implements IPageView{
    //数据加载器
    private IPagePresenter pagePresenter;
    //通用adaper
    private AssemblyAdapter adapter;
    //下拉刷新组件
    private PtrClassicFrameLayout mPtrFrame;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        return rootView;
    }

    /**
     * 以下三个方法必须按顺序调用
     * 1.设置presenter
     */
    public void setPagePresenter(IPagePresenter pagePresenter) {
        this.pagePresenter = pagePresenter;
        List list = pagePresenter.getList();
        adapter = new AssemblyAdapter(list);
    }

    /**
     * 2.添加itemFactory
     */
    public void addItemFactory(AssemblyItemFactory itemFactory){
        adapter.addItemFactory(itemFactory);
    }

    /**
     * 3.初始化
     */
    public void init() {
        adapter.setLoadMoreItem(new LoadMoreItemFactory(new OnLoadMoreListener(){
            @Override
            public void onLoadMore(AssemblyAdapter adapter) {
                // 访问网络加载数据
                pagePresenter.loadNextPage();
            }
        }));

        listView.setAdapter(adapter);

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
    public void loadSucc(boolean hasNextPage) {
        //上拉结束
        adapter.notifyDataSetChanged();
        adapter.setDisableLoadMore(false);

        //下拉结束
        mPtrFrame.refreshComplete();
    }

    @Override
    public void loadErr() {
        //上拉结束
        adapter.loadMoreFailed();
        adapter.setDisableLoadMore(true);

        //下拉结束
        mPtrFrame.refreshComplete();
    }
}
