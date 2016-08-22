package com.langchen.xlib.mvp.view;

/**
 * Created by admin on 2016/8/18.
 */
public interface PageView {

    void loadComplete();

    void loadFirstPageComplete();

    void loadMoreSucc(boolean hasNextPage);

    void loadMoreErr();
}
