package com.langchen.xlib.ui.pagefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.langchen.xlib.R;
import com.langchen.xlib.ui.assemblyadapter.AssemblyRecyclerAdapter;
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
 *
 * 该类不能直接使用  必须重新这些方法
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
        pagePresenter = getPagePresenter();
        if (pagePresenter==null) {
            throw new RuntimeException("RecyclePageFragment不能直接使用");
        }
        List list = pagePresenter.getList();
        adapter = new AssemblyRecyclerAdapter(list);
        addItemFactory(adapter);
        setLayoutManager(getLayoutManager());
        init();
        initData();
        return rootView;
    }

    public IPagePresenter getPagePresenter(){
        return null;
    }

    public RecyclerView.LayoutManager getLayoutManager(){
        return null;
    }

    public void addItemFactory(AssemblyRecyclerAdapter adapter) {

    }

    /**
     * 3.设置布局管理器
     */
    public void setLayoutManager(final RecyclerView.LayoutManager lm){
        //让foot占据整行
        if (lm instanceof GridLayoutManager){
            ((GridLayoutManager) lm).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    Object item = adapter.getItem(position);
                    if (item == null) {//当item为加载更多时 getItem会返回null
                        return ((GridLayoutManager) lm).getSpanCount();
                    }
                    return 1;
                }
            });
        }

        if ( lm instanceof StaggeredGridLayoutManager) {
            //解决滑动过程中item交换位置的问题
            final StaggeredGridLayoutManager lm1 = (StaggeredGridLayoutManager) lm;
            lm1.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

            //防止第一行到顶部有空白区域
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    lm1.invalidateSpanAssignments();
                }
            });
        }

        recyclerView.setLayoutManager(lm);
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

    protected boolean init = false;
    /**
     * 在这里实现Fragment数据的缓加载.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        initData();
    }

    /**初始化数据*/
    public void initData(){
        if(getUserVisibleHint() && mPtrFrame!=null && !init) {
            mPtrFrame.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPtrFrame.autoRefresh();
                }
            }, 100);
            init = true;
        }
    }
}
