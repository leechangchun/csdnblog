package com.langchen.xlib.ui.pagefragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.langchen.xlib.R;
import com.langchen.xlib.ui.assemblyadapter.AssemblyAdapter;
import com.langchen.xlib.ui.assemblyadapter.AssemblyItemFactory;
import com.langchen.xlib.ui.assemblyadapter.AssemblyLoadMoreRecyclerItemFactory;
import com.langchen.xlib.ui.assemblyadapter.AssemblyRecyclerAdapter;
import com.langchen.xlib.ui.assemblyadapter.AssemblyRecyclerItem;
import com.langchen.xlib.ui.assemblyadapter.AssemblyRecyclerItemFactory;
import com.langchen.xlib.ui.assemblyadapter.OnLoadMoreListener;
import com.langchen.xlib.ui.assemblyadapter.OnRecyclerLoadMoreListener;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by admin on 2016/8/23.
 * 显示列表的fragment 用RecyclerView
 * 包含  分页 上拉加载  下拉刷新
 */
public class RecyclePageFragment extends Fragment implements IPageView{
    //数据加载器
    private IPagePresenter pagePresenter;
    //通用adaper
    private AssemblyRecyclerAdapter adapter;
    //下拉刷新组件
    private PtrClassicFrameLayout mPtrFrame;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycle_fragment_page, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
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
        adapter = new AssemblyRecyclerAdapter(list);
    }

    /**
     * 2.添加itemFactory
     */
    public void addItemFactory(AssemblyRecyclerItemFactory itemFactory){
        adapter.addItemFactory(itemFactory);
    }

    /**
     * 3.设置布局管理器
     */
    public void setLayoutManager(RecyclerView.LayoutManager layout){
        recyclerView.setLayoutManager(layout);
    }
    /**
     * 3.初始化
     */
    public void init() {
        adapter.setLoadMoreItem(new RecycleLoadMoreItemFactory(new OnRecyclerLoadMoreListener() {
            @Override
            public void onLoadMore(AssemblyRecyclerAdapter adapter) {
                pagePresenter.loadNextPage();
            }
        }));

        recyclerView.setAdapter(adapter);

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
