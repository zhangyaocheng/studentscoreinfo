package com.example.studentscoreinfo.util;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回结果
 * 01397466
 */
public class PageResult<T> implements Serializable {

    private int pageNum; // 当前起始数目
    private int pageSize; // 当前页数
    private long totalSize; // 总数据数目
    private int totalPages; // 总页数
    private List<T> data; // 获取到的数目

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
