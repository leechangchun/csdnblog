package com.langchen.csdnread.presenter;

import com.langchen.csdnread.api.service.ArticleService;
import com.langchen.csdnread.entity.Article;
import com.langchen.xlib.api.resp.PageResp;
import com.langchen.xlib.api.util.ApiUtils;
import com.langchen.xlib.mvp.presenter.PagePresenter;
import com.langchen.xlib.mvp.view.PageView;

import rx.Observable;

/**
 * Created by admin on 2016/8/19.
 */
public class ArticlePagePresenter extends PagePresenter<Article> {
    public ArticlePagePresenter(PageView pageView) {
        super(pageView);
    }

    @Override
    public Observable<PageResp<Article>> getPageObservable(int page) {
        return ApiUtils.getInstance().getRetrofit().create(ArticleService.class)
                .getArticleList(page);
    }
}
