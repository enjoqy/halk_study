package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author halk
 * @Date 2020/5/15 0015 11:46
 */
public interface GoodsRepository  extends ElasticsearchRepository<Goods, Long> {
}
