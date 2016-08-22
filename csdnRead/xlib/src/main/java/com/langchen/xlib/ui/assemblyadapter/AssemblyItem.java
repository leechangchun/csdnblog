package com.langchen.xlib.ui.assemblyadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class AssemblyItem<BEAN> {
    private View itemView;
    private int position;
    private BEAN data;
    private ContentSetter setter;

    public AssemblyItem(int itemLayoutId, ViewGroup parent) {
        this(LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false));
    }

    public AssemblyItem(View itemView) {
        if (itemView == null) {
            throw new IllegalArgumentException("itemView may not be null");
        }
        this.itemView = itemView;
        onFindViews();
        onConfigViews(itemView.getContext());
    }

    public void setData(int position, BEAN bean) {
        this.position = position;
        this.data = bean;
        onSetData(position, bean);
    }

    public View findViewById(int id) {
        return itemView.findViewById(id);
    }

    @SuppressWarnings("unused")
    public View findViewWithTag(Object tag) {
        return itemView.findViewWithTag(tag);
    }

    protected abstract void onFindViews();

    protected abstract void onConfigViews(Context context);

    protected abstract void onSetData(int position, BEAN bean);

    public final View getItemView() {
        return this.itemView;
    }

    public BEAN getData() {
        return data;
    }

    public int getPosition() {
        return position;
    }

    public ContentSetter getSetter() {
        if (setter == null) {
            setter = new ContentSetter(itemView);
        }
        return setter;
    }
}