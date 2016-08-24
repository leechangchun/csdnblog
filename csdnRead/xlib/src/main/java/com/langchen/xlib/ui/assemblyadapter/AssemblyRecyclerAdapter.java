package com.langchen.xlib.ui.assemblyadapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class AssemblyRecyclerAdapter extends RecyclerView.Adapter {
    private static final String TAG = "AssemblyRecyclerAdapter";

    private final Object mLock = new Object();
    private List dataList;

    private int itemTypeIndex = 0;
    private boolean itemFactoryLocked;
    private ArrayList<FixedRecyclerItemInfo> headerItemList;
    private ArrayList<FixedRecyclerItemInfo> footerItemList;
    private ArrayList<AssemblyRecyclerItemFactory> itemFactoryList;
    private SparseArray<Object> itemFactoryArray;

    private boolean disableLoadMore;
    private AssemblyLoadMoreRecyclerItemFactory.AssemblyLoadMoreRecyclerItem loadMoreItem;
    private AssemblyLoadMoreRecyclerItemFactory loadMoreItemFactory;

    public AssemblyRecyclerAdapter(List dataList) {
        this.dataList = dataList;
    }

    @SuppressWarnings("unused")
    public AssemblyRecyclerAdapter(Object[] dataArray) {
        if (dataArray != null && dataArray.length > 0) {
            this.dataList = new ArrayList(dataArray.length);
            Collections.addAll(dataList, dataArray);
        }
    }

    /**
     * 添加一个将按添加顺序显示在列表头部的AssemblyRecyclerItemFactory
     */
    @SuppressWarnings("unused")
    public FixedRecyclerItemInfo addHeaderItem(AssemblyRecyclerItemFactory headerFactory, Object data) {
        if (headerFactory == null || itemFactoryLocked) {
            Log.w(TAG, "headerFactory is nll or locked");
            return null;
        }

        headerFactory.setAdapter(this);
        headerFactory.setItemType(itemTypeIndex++);
        FixedRecyclerItemInfo headerItemInfo = new FixedRecyclerItemInfo(headerFactory, data);

        if (itemFactoryArray == null) {
            itemFactoryArray = new SparseArray<Object>();
        }
        itemFactoryArray.put(headerFactory.getItemType(), headerItemInfo);

        if (headerItemList == null) {
            headerItemList = new ArrayList<FixedRecyclerItemInfo>(2);
        }
        headerItemList.add(headerItemInfo);

        return headerItemInfo;
    }

    /**
     * 添加一个用来处理并显示dataList中的数据的AssemblyRecyclerItemFactory
     */
    public void addItemFactory(AssemblyRecyclerItemFactory itemFactory) {
        if (itemFactory == null || itemFactoryLocked) {
            Log.w(TAG, "itemFactory is nll or locked");
            return;
        }

        itemFactory.setAdapter(this);
        itemFactory.setItemType(itemTypeIndex++);

        if (itemFactoryArray == null) {
            itemFactoryArray = new SparseArray<Object>();
        }
        itemFactoryArray.put(itemFactory.getItemType(), itemFactory);

        if (itemFactoryList == null) {
            itemFactoryList = new ArrayList<AssemblyRecyclerItemFactory>(5);
        }
        itemFactoryList.add(itemFactory);
    }

    /**
     * 添加一个将按添加顺序显示在列表尾部的AssemblyRecyclerItemFactory
     */
    @SuppressWarnings("unused")
    public FixedRecyclerItemInfo addFooterItem(AssemblyRecyclerItemFactory footerFactory, Object data) {
        if (footerFactory == null || itemFactoryLocked) {
            Log.w(TAG, "footerFactory is nll or locked");
            return null;
        }

        footerFactory.setAdapter(this);
        footerFactory.setItemType(itemTypeIndex++);
        FixedRecyclerItemInfo footerItemInfo = new FixedRecyclerItemInfo(footerFactory, data);

        if (itemFactoryArray == null) {
            itemFactoryArray = new SparseArray<Object>();
        }
        itemFactoryArray.put(footerFactory.getItemType(), footerItemInfo);

        if (footerItemList == null) {
            footerItemList = new ArrayList<FixedRecyclerItemInfo>(2);
        }
        footerItemList.add(footerItemInfo);

        return footerItemInfo;
    }

    /**
     * 设置一个将显示在列表最后（在Footer的后面）的加载更多尾巴
     */
    @SuppressWarnings("unused")
    public void setLoadMoreItem(AssemblyLoadMoreRecyclerItemFactory newLoadMoreItemFactory) {
        if (newLoadMoreItemFactory == null || itemFactoryLocked) {
            Log.w(TAG, "newLoadMoreItemFactory is null or locked");
            return;
        }

        newLoadMoreItemFactory.setEnd(false);
        newLoadMoreItemFactory.setAdapter(this);
        if (loadMoreItemFactory != null) {
            newLoadMoreItemFactory.setItemType(loadMoreItemFactory.getItemType());
        } else {
            newLoadMoreItemFactory.setItemType(itemTypeIndex++);
        }
        newLoadMoreItemFactory.setLoadMoreRunning(false);

        if (itemFactoryArray == null) {
            itemFactoryArray = new SparseArray<Object>();
        }
        itemFactoryArray.put(newLoadMoreItemFactory.getItemType(), newLoadMoreItemFactory);

        loadMoreItemFactory = newLoadMoreItemFactory;
    }

    /**
     * 批量添加数据
     */
    @SuppressWarnings("unused")
    public void addAll(Collection collection) {
        if (collection == null || collection.size() == 0) {
            return;
        }
        synchronized (mLock) {
            if (dataList == null) {
                dataList = new ArrayList(collection.size());
            }
            //noinspection unchecked
            dataList.addAll(collection);
        }
        notifyDataSetChanged();
    }

    /**
     * 批量添加数据
     */
    @SuppressWarnings("unused")
    public void addAll(Object... items) {
        if (items == null || items.length == 0) {
            return;
        }
        synchronized (mLock) {
            if (dataList == null) {
                dataList = new ArrayList(items.length);
            }
            Collections.addAll(dataList, items);
        }
        notifyDataSetChanged();
    }

    /**
     * 插入一条数据
     */
    @SuppressWarnings("unused")
    public void insert(Object object, int index) {
        if (object == null) {
            return;
        }
        synchronized (mLock) {
            if (dataList == null) {
                dataList = new ArrayList();
            }
            //noinspection unchecked
            dataList.add(index, object);
        }
        notifyDataSetChanged();
    }

    /**
     * 删除一条数据
     */
    @SuppressWarnings("unused")
    public void remove(Object object) {
        if (object == null) {
            return;
        }
        synchronized (mLock) {
            if (dataList != null) {
                dataList.remove(object);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    @SuppressWarnings("unused")
    public void clear() {
        synchronized (mLock) {
            if (dataList != null) {
                dataList.clear();
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 对数据排序
     */
    @SuppressWarnings("unused")
    public void sort(Comparator comparator) {
        synchronized (mLock) {
            if (dataList != null) {
                Collections.sort(dataList, comparator);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 设置是否禁用加载更多
     */
    @SuppressWarnings("unused")
    public void setDisableLoadMore(boolean disableLoadMore) {
        this.disableLoadMore = disableLoadMore;

        if (loadMoreItemFactory != null) {
            loadMoreItemFactory.setEnd(false);
            loadMoreItemFactory.setLoadMoreRunning(false);
        }

        notifyDataSetChanged();
    }

    /**
     * 设置加载更多是否已结束，已结束会显示结束的文案并且不再触发加载更多
     */
    @SuppressWarnings("unused")
    public void setLoadMoreEnd(boolean loadMoreEnd) {
        if (loadMoreItemFactory != null) {
            loadMoreItemFactory.setLoadMoreRunning(false);
            loadMoreItemFactory.setEnd(loadMoreEnd);
        }
        if (loadMoreItem != null) {
            if (loadMoreEnd) {
                loadMoreItem.showEnd();
            } else {
                loadMoreItem.showLoading();
            }
        }
    }

    /**
     * 加载更多失败的时候调用此方法显示错误提示，并可点击重新加载
     */
    @SuppressWarnings("unused")
    public void loadMoreFailed() {
        if (loadMoreItemFactory != null) {
            loadMoreItemFactory.setLoadMoreRunning(false);
        }
        if (loadMoreItem != null) {
            loadMoreItem.showErrorRetry();
        }
    }

    /**
     * 删除一个HeaderItem
     */
    @SuppressWarnings("unused")
    public void removeHeaderItem(FixedRecyclerItemInfo headerItemInfo) {
        if (headerItemList != null && headerItemInfo != null) {
            Iterator<FixedRecyclerItemInfo> iterator = headerItemList.iterator();
            FixedRecyclerItemInfo fixedItemInfo;
            while (iterator.hasNext()) {
                fixedItemInfo = iterator.next();
                if (fixedItemInfo == headerItemInfo) {
                    iterator.remove();
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    /**
     * 删除一个FooterItem
     */
    @SuppressWarnings("unused")
    public void removeFooterItem(FixedRecyclerItemInfo footerItemInfo) {
        if (footerItemList != null && footerItemInfo != null) {
            Iterator<FixedRecyclerItemInfo> iterator = footerItemList.iterator();
            FixedRecyclerItemInfo fixedItemInfo;
            while (iterator.hasNext()) {
                fixedItemInfo = iterator.next();
                if (fixedItemInfo == footerItemInfo) {
                    iterator.remove();
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    /**
     * 获取Header列表
     */
    @SuppressWarnings("unused")
    public List<FixedRecyclerItemInfo> getHeaderItemList() {
        return headerItemList;
    }

    /**
     * 获取ItemFactory列表
     */
    @SuppressWarnings("unused")
    public List<AssemblyRecyclerItemFactory> getItemFactoryList() {
        return itemFactoryList;
    }

    /**
     * 获取Footer列表
     */
    @SuppressWarnings("unused")
    public List<FixedRecyclerItemInfo> getFooterItemList() {
        return footerItemList;
    }

    /**
     * 获取数据列表
     */
    @SuppressWarnings("unused")
    public List getDataList() {
        return dataList;
    }

    /**
     * 设置数据列表
     */
    @SuppressWarnings("unused")
    public void setDataList(List dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    /**
     * 获取列表头的个数
     */
    public int getHeaderItemCount() {
        return headerItemList != null ? headerItemList.size() : 0;
    }

    /**
     * 获取ItemFactory的个数
     */
    public int getItemFactoryCount() {
        return itemFactoryList != null ? itemFactoryList.size() : 0;
    }

    /**
     * 获取列表头的个数
     */
    public int getFooterItemCount() {
        return footerItemList != null ? footerItemList.size() : 0;
    }

    /**
     * 是否有加载更多尾巴
     */
    public boolean hasLoadMoreFooter() {
        return !disableLoadMore && loadMoreItemFactory != null;
    }

    /**
     * 获取数据列表的长度
     */
    public int getDataCount() {
        return dataList != null ? dataList.size() : 0;
    }

    /**
     * 获取在各自区域的位置
     */
    @SuppressWarnings("unused")
    public int getPositionInPart(int position){
        // 头
        int headerItemCount = getHeaderItemCount();
        int headerStartPosition = 0;
        int headerEndPosition = headerItemCount - 1;
        if (position >= headerStartPosition && position <= headerEndPosition && headerItemCount > 0) {
            return position;
        }

        // 数据
        int dataCount = getDataCount();
        int dataStartPosition = headerEndPosition + 1;
        int dataEndPosition = headerEndPosition + dataCount;
        if (position >= dataStartPosition && position <= dataEndPosition && dataCount > 0) {
            return position - headerItemCount;
        }

        // 尾巴
        int footerItemCount = getFooterItemCount();
        int footerStartPosition = dataEndPosition + 1;
        int footerEndPosition = dataEndPosition + footerItemCount;
        if (position >= footerStartPosition && position <= footerEndPosition && footerItemCount > 0) {
            return position - headerItemCount - dataCount;
        }

        // 加载更多尾巴
        if (dataCount > 0 && hasLoadMoreFooter() && position == getItemCount() - 1) {
            return 0;
        }

        throw new IllegalArgumentException("illegal position: " + position);
    }

    @Override
    public int getItemCount() {
        int headerItemCount = getHeaderItemCount();
        int dataCount = getDataCount();
        int footerItemCount = getFooterItemCount();

        if (dataCount > 0) {
            return headerItemCount + dataCount + footerItemCount + (hasLoadMoreFooter() ? 1 : 0);
        } else {
            return headerItemCount + footerItemCount;
        }
    }

    public Object getItem(int position) {
        // 头
        int headerItemCount = getHeaderItemCount();
        int headerStartPosition = 0;
        int headerEndPosition = headerItemCount - 1;
        if (position >= headerStartPosition && position <= headerEndPosition && headerItemCount > 0) {
            //noinspection UnnecessaryLocalVariable
            int positionInHeaderList = position;
            return headerItemList.get(positionInHeaderList).getData();
        }

        // 数据
        int dataCount = getDataCount();
        int dataStartPosition = headerEndPosition + 1;
        int dataEndPosition = headerEndPosition + dataCount;
        if (position >= dataStartPosition && position <= dataEndPosition && dataCount > 0) {
            int positionInDataList = position - headerItemCount;
            return dataList.get(positionInDataList);
        }

        // 尾巴
        int footerItemCount = getFooterItemCount();
        int footerStartPosition = dataEndPosition + 1;
        int footerEndPosition = dataEndPosition + footerItemCount;
        if (position >= footerStartPosition && position <= footerEndPosition && footerItemCount > 0) {
            int positionInFooterList = position - headerItemCount - dataCount;
            return footerItemList.get(positionInFooterList).getData();
        }

        // 加载更多尾巴
        if (dataCount > 0 && hasLoadMoreFooter() && position == getItemCount() - 1) {
            return null;
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemFactoryCount() <= 0) {
            throw new IllegalStateException("You need to configure AssemblyItemFactory use addItemFactory method");
        }
        itemFactoryLocked = true;

        // 头
        int headerItemCount = getHeaderItemCount();
        int headerStartPosition = 0;
        int headerEndPosition = headerItemCount - 1;
        if (position >= headerStartPosition && position <= headerEndPosition && headerItemCount > 0) {
            //noinspection UnnecessaryLocalVariable
            int positionInHeaderList = position;
            return headerItemList.get(positionInHeaderList).getItemFactory().getItemType();
        }

        // 数据
        int dataCount = getDataCount();
        int dataStartPosition = headerEndPosition + 1;
        int dataEndPosition = headerEndPosition + dataCount;
        if (position >= dataStartPosition && position <= dataEndPosition && dataCount > 0) {
            int positionInDataList = position - headerItemCount;
            Object dataObject = dataList.get(positionInDataList);

            AssemblyRecyclerItemFactory itemFactory;
            for (int w = 0, size = itemFactoryList.size(); w < size; w++) {
                itemFactory = itemFactoryList.get(w);
                if (itemFactory.isTarget(dataObject)) {
                    return itemFactory.getItemType();
                }
            }

            throw new IllegalStateException("Didn't find suitable AssemblyItemFactory. " +
                    "positionInDataList=" + positionInDataList + ", " +
                    "dataObject=" + (dataObject != null ? dataObject.getClass().getName() : "null"));
        }

        // 尾巴
        int footerItemCount = getFooterItemCount();
        int footerStartPosition = dataEndPosition + 1;
        int footerEndPosition = dataEndPosition + footerItemCount;
        if (position >= footerStartPosition && position <= footerEndPosition && footerItemCount > 0) {
            int positionInFooterList = position - headerItemCount - dataCount;
            return footerItemList.get(positionInFooterList).getItemFactory().getItemType();
        }

        // 加载更多尾巴
        if (dataCount > 0 && hasLoadMoreFooter() && position == getItemCount() - 1) {
            return loadMoreItemFactory.getItemType();
        }

        throw new IllegalStateException("not found match viewType, position: " + position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Object item = itemFactoryArray.get(viewType);

        // Item或加载更多尾巴
        if (item instanceof AssemblyRecyclerItemFactory) {
            AssemblyRecyclerItemFactory itemFactory = (AssemblyRecyclerItemFactory) item;
            AssemblyRecyclerItem assemblyItem = itemFactory.createAssemblyItem(parent);
            if (assemblyItem instanceof AssemblyLoadMoreRecyclerItemFactory.AssemblyLoadMoreRecyclerItem) {
                this.loadMoreItem = (AssemblyLoadMoreRecyclerItemFactory.AssemblyLoadMoreRecyclerItem) assemblyItem;
            }
            return assemblyItem;
        }

        // 头或尾巴
        if (item instanceof FixedRecyclerItemInfo) {
            FixedRecyclerItemInfo fixedItemInfo = (FixedRecyclerItemInfo) item;
            return fixedItemInfo.getItemFactory().createAssemblyItem(parent);
        }

        throw new IllegalStateException("unknown viewType: " + viewType + ", " +
                "itemFactory: " + (item != null ? item.getClass().getName() : "null"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        //如果是foot并且  布局为瀑布流  则铺满
        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
        if(getItem(position)==null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
        }

        if (viewHolder instanceof AssemblyRecyclerItem) {
            ((AssemblyRecyclerItem) viewHolder).setData(position, getItem(position));
        }
    }

}
