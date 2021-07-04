package com.example.studentscoreinfo.util;

import com.github.pagehelper.PageInfo;


/**
 * 将分页信息封装到一个接口
 */
public class PageUtils {

    public static PageResult getPageResult(PageInfo<?> pageInfo){

        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setData(pageInfo.getList());
        return pageResult;
    }


}

