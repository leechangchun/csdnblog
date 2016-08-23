package com.langchen.csdnread.api.service;

import com.langchen.csdnread.entity.Article;
import com.langchen.xlib.api.resp.PageResp;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 2016/8/18.
 */
public interface ArticleService{
    @GET("/blog/getnewarticlelist?client_id=1100333")
    Observable<PageResp<Article>> getArticleList(@Query("page")int page);
}
