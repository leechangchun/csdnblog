package com.langchen.xlib.ui.pagefragment;

import android.view.View;
import android.view.ViewGroup;

import com.langchen.xlib.R;
import com.langchen.xlib.ui.assemblyadapter.AssemblyLoadMoreRecyclerItemFactory;
import com.langchen.xlib.ui.assemblyadapter.OnRecyclerLoadMoreListener;

/**
 * Created by admin on 2016/8/19.
 */
public class RecycleLoadMoreItemFactory extends AssemblyLoadMoreRecyclerItemFactory {
    public RecycleLoadMoreItemFactory(OnRecyclerLoadMoreListener eventListener) {
        super(eventListener);
    }

    @Override
    public AssemblyLoadMoreRecyclerItem createAssemblyItem(ViewGroup parent) {
        return new LoadMoreItem(R.layout.item_load_more, parent);
    }

    public class LoadMoreItem extends AssemblyLoadMoreRecyclerItem {
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
