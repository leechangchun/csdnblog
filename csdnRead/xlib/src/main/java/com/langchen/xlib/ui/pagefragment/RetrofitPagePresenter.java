package com.langchen.xlib.ui.pagefragment;

import com.langchen.xlib.api.PageResp;
import com.langchen.xlib.api.ApiFunc1;
import com.langchen.xlib.api.ApiObserver;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2016/8/18.
 */
public abstract class RetrofitPagePresenter<D> extends IPagePresenter<D> {

    public RetrofitPagePresenter(IPageView pageView) {
        super(pageView);
    }

    public abstract Observable<PageResp<D>> getPageObservable(int page);

    @Override
    public void loadDatas(final int page) {
        getPageObservable(page)
                .flatMap(new ApiFunc1<PageResp>())
                .flatMap(new Func1<PageResp, Observable<PageResp>>() {
                    @Override
                    public Observable<PageResp> call(final PageResp pageResp) {
                        return Observable.create(new Observable.OnSubscribe<PageResp>() {
                            @Override
                            public void call(Subscriber<? super PageResp> subscriber) {
                                //在io线程处理数据
                                if (pageResp.getPage() == 0 || pageResp.getList() == null) {
                                    subscriber.onError(new Throwable());
                                }else {
                                    setDatas(pageResp.getPage(),pageResp.getList());
                                    subscriber.onNext(pageResp);
                                }
                                subscriber.onCompleted();
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ApiObserver<PageResp>() {
                    @Override
                    public void onNext(PageResp pageResp) {
                        //在主线程处理界面显示
                        loadSucc(pageResp.getCount());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        loadErr();
                    }
                });
    }


}
