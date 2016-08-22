package com.langchen.xlib.api.resp;

import java.util.ArrayList;

/**
 * Created by admin on 2016/8/18.
 */
public class PageResp<T> extends BaseResp {
    private int page;
    private int count;
    private int size;
    private ArrayList<T> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageResp{" +
                "page=" + page +
                ", count=" + count +
                ", size=" + size +
                ", list=" + list +
                '}';
    }
}
