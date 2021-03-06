package com.langchen.csdnread.presenter;

import com.langchen.csdnread.api.service.ColumnService;
import com.langchen.csdnread.entity.Column;
import com.langchen.xlib.api.resp.PageResp;
import com.langchen.xlib.api.util.ApiUtils;
import com.langchen.xlib.mvp.presenter.PagePresenter;
import com.langchen.xlib.mvp.view.PageView;

import rx.Observable;

/**
 * Created by admin on 2016/8/19.
 */
public class ColumnPagePresenter extends PagePresenter<Column> {
    public ColumnPagePresenter(PageView pageView) {
        super(pageView);
    }

    @Override
    public Observable<PageResp<Column>> getPageObservable(int page) {
        return ApiUtils.getInstance().getRetrofit().create(ColumnService.class)
                .getArticleList(page);
    }
}
