package com.langchen.csdnread.itemFactory;

import android.content.Context;
import android.view.ViewGroup;

import com.langchen.csdnread.R;
import com.langchen.csdnread.entity.Article;
import com.langchen.xlib.ui.assemblyadapter.AssemblyRecyclerItem;
import com.langchen.xlib.ui.assemblyadapter.AssemblyRecyclerItemFactory;

/**
 * Created by admin on 2016/8/19.
 */
public class ArticleItemFactory extends AssemblyRecyclerItemFactory<ArticleItemFactory.ArticleListItem> {
    @Override
    public boolean isTarget(Object itemObject) {
        return itemObject instanceof Article;
    }

    @Override
    public ArticleListItem createAssemblyItem(ViewGroup parent) {
        return new ArticleListItem(R.layout.item_article,parent);
    }

    public class ArticleListItem extends AssemblyRecyclerItem<Article> {
        public ArticleListItem(int itemLayoutId, ViewGroup parent) {
            super(itemLayoutId, parent);
        }

        @Override
        protected void onFindViews() {

        }

        @Override
        protected void onConfigViews(Context context) {

        }

        @Override
        protected void onSetData(int position, Article article) {
            getSetter().setText(R.id.tv_desc,article.getTitle());
        }
    }
}
