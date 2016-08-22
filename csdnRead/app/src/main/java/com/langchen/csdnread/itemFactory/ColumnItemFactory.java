package com.langchen.csdnread.itemFactory;

import android.content.Context;
import android.view.ViewGroup;

import com.langchen.csdnread.R;
import com.langchen.csdnread.entity.Column;

import com.langchen.xlib.ui.assemblyadapter.AssemblyItem;
import com.langchen.xlib.ui.assemblyadapter.AssemblyItemFactory;

/**
 * Created by admin on 2016/8/19.
 */
public class ColumnItemFactory extends AssemblyItemFactory<ColumnItemFactory.ColumnListItem> {
    @Override
    public boolean isTarget(Object itemObject) {
        return itemObject instanceof Column;
    }

    @Override
    public ColumnListItem createAssemblyItem(ViewGroup parent) {
        return new ColumnListItem(R.layout.item_article,parent);
    }

    public class ColumnListItem extends AssemblyItem<Column> {
        public ColumnListItem(int itemLayoutId, ViewGroup parent) {
            super(itemLayoutId, parent);
        }

        @Override
        protected void onFindViews() {

        }

        @Override
        protected void onConfigViews(Context context) {

        }

        @Override
        protected void onSetData(int position, Column column) {
            getSetter().setText(R.id.tv_desc,column.getTitle());
        }
    }
}
