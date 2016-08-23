package com.langchen.xlib.ui.pagefragment;

import android.view.View;
import android.view.ViewGroup;

import com.langchen.xlib.R;
import com.langchen.xlib.ui.assemblyadapter.AssemblyLoadMoreItemFactory;
import com.langchen.xlib.ui.assemblyadapter.OnLoadMoreListener;

/**
 * Created by admin on 2016/8/19.
 */
public class LoadMoreItemFactory extends AssemblyLoadMoreItemFactory {
    public LoadMoreItemFactory(OnLoadMoreListener eventListener) {
        super(eventListener);
    }

    @Override
    public AssemblyLoadMoreItem createAssemblyItem(ViewGroup parent) {
        return new LoadMoreItem(R.layout.item_load_more, parent);
    }

    public class LoadMoreItem extends AssemblyLoadMoreItem {
        private View loadingView;
        private View errorView;
        private View endView;

        public LoadMoreItem(int itemLayoutId, ViewGroup parent) {
            super(itemLayoutId, parent);
        }

        @Override
        protected void onFindViews() {
            loadingView = findViewById(R.id.tv_loading);
            errorView = findViewById(R.id.tv_error);
            endView = findViewById(R.id.tv_end);
        }

        @Override
        public View getErrorRetryView() {
            return errorView;
        }

        @Override
        public void showLoading() {
            loadingView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.INVISIBLE);
            endView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void showErrorRetry() {
            loadingView.setVisibility(View.INVISIBLE);
            errorView.setVisibility(View.VISIBLE);
            endView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void showEnd() {
            loadingView.setVisibility(View.INVISIBLE);
            errorView.setVisibility(View.INVISIBLE);
            endView.setVisibility(View.VISIBLE);
        }
    }
}
