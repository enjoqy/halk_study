package com.leyou.search.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author halk
 * @Date 2020/5/18 0018 11:19
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * @Author halk
     * @Description  搜索商品的接口
     * @Date 2020/5/18 0018 11:23
     * @Param [request]
     * @return org.springframework.http.ResponseEntity<com.leyou.common.pojo.PageResult<com.leyou.search.pojo.Goods>>
     **/
    @PostMapping("/page")
    public ResponseEntity<SearchResult> search(@RequestBody SearchRequest request){
        SearchResult result = this.searchService.search(request);
        if (result == null || CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
