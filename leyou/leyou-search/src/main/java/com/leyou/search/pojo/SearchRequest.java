package com.leyou.search.pojo;

import lombok.Data;

import java.util.Map;

/**
 * @Author halk
 * @Description 搜索条件封装成对象
 * @Date 2020/5/18 0018 11:19
 * @Param
 * @return
 **/
@Data
public class SearchRequest {

    /**
     * 搜索条件
     */
    private String key;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 搜索界面传来的过滤字段
     */
    private Map<String, Object> filter;

    /**
     * 每页大小，不从页面接收，而是固定大小
     */
    private static final Integer DEFAULT_SIZE = 20;

    /**
     * 默认页
     */
    private static final Integer DEFAULT_PAGE = 1;


    public Integer getPage() {
        if(page == null){
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }

}
