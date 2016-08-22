package com.langchen.xlib.mvp.presenter;

import android.util.Log;

import com.langchen.xlib.api.resp.PageResp;
import com.langchen.xlib.api.util.ApiFunc1;
import com.langchen.xlib.api.util.ApiObserver;
import com.langchen.xlib.mvp.view.PageView;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2016/8/18.
 */
public abstract class PagePresenter<D> {
    private PageView pageView;
    private int page;
    private ArrayList<D> list = new ArrayList<>();

    public PagePresenter(PageView pageView) {
        this.pageView = pageView;
        loadCache();
    }

    public ArrayList<D> getList() {
        return list;
    }

    public abstract Observable<PageResp<D>> getPageObservable(int page);

    /*return ApiUtils.getInstance().getRetrofit().create(ColumnService.class)
                .getArticleList(page);
     */

    public void getArticleList(final int page) {
        getPageObservable(page)
                .flatMap(new ApiFunc1<PageResp>())
                .flatMap(new Func1<PageResp, Observable<PageResp>>() {
                    @Override
                    public Observable<PageResp> call(final PageResp pageResp) {
                        return setDatas(pageResp);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ApiObserver<PageResp>() {
                    @Override
                    public void onCompleted() {
                        pageView.loadComplete();
                        PagePresenter.this.page = page;
                        saveCache();
                    }

                    @Override
                    public void onNext(PageResp pageResp) {
                        if (pageResp.getPage() == 1) {
                            pageView.loadFirstPageComplete();
                        } else {
                            if (list.size() >= pageResp.getCount()) {
                                pageView.loadMoreSucc(false);
                            } else {
                                pageView.loadMoreSucc(true);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (page != 1) {
                            pageView.loadMoreErr();
                        }
                        pageView.loadComplete();
                    }
                });
    }

    private Observable<PageResp> setDatas(final PageResp<D> pageResp) {
        return Observable.create(new Observable.OnSubscribe<PageResp>() {
            @Override
            public void call(Subscriber<? super PageResp> subscriber) {
                if (pageResp.getPage() == 0 || pageResp.getList() == null) {
                    subscriber.onError(new Throwable());
                } else if (pageResp.getPage() == 1) {
                    list.clear();
                    list.addAll(pageResp.getList());
                    subscriber.onNext(pageResp);
                } else {
                    list.addAll(pageResp.getList());
                    subscriber.onNext(pageResp);
                }
                subscriber.onCompleted();
            }
        });
    }

    public void loadMore() {
        getArticleList(this.page + 1);
    }

    public void loadFirstPage() {
        getArticleList(1);
    }

    /*
    加载缓存*/
    public void loadCache(){

    }

    /*
    缓存数据
    */
    protected void saveCache() {

    }
}
