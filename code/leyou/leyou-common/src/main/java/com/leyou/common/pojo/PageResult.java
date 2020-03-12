package com.leyou.common.pojo;

import lombok.Data;

import java.util.List;

/**
 * 分页的公共类
 * @Author halk
 * @Date 2020/2/13 11:17
 */
@Data
public class PageResult<T> {

    private Long total;
    private Integer totalPage;
    private List<T> items;

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }
}
