package com.langchen.xlib.ui.pagefragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/8/23.
 * 顶层分页接口
 */
public abstract class IPagePresenter<T> {
    private int page;
    private IPageView iPageView;
    private ArrayList<T> list = new ArrayList<>();

    public IPagePresenter(IPageView iPageView) {
        this.iPageView = iPageView;
    }

    /**
     * 获取列表所有数据
     * */
    public List<T> getList() {
        return list;
    }

    /**
     * 加载第一页
     * */
    public void loadFirstPage(){
        loadDatas(1);
    }

    /**
     * 加载下一页
     * */
    public void loadNextPage(){
        loadDatas(page+1);
    }

    /**加载数据*/
    abstract void loadDatas(int page);

    /**
     * 给列表设置数据
     * */
    public void setDatas(int page,List<T> datas) {
        this.page = page;
        if (page==1) {
            list.clear();
        }
        list.addAll(datas);
    }

    /**
     * 加载数据失败
     * */
    public void loadErr(){
        iPageView.loadErr();
    }

    /**
     * 加载下一页成功
     * @param totalCount 后台数据总数
     * */
    public void loadSucc(int totalCount){
        iPageView.loadSucc(list.size()<totalCount);
    }

}
