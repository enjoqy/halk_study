package org.halk.halkelasticsearch;

import org.halk.halkelasticsearch.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/5/11 0011 15:17
 */
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

    /**
     * @Author halk
     * @Description 自定义方法，根据title查询
     * @Date 2020/5/11 0011 16:24
     * @Param [title]
     * @return java.util.List<org.halk.halkelasticsearch.pojo.Item>
     **/
    List<Item> findAllByTitle(String title);

    /**
     * @Author halk
     * @Description
     * @Date 2020/5/11 0011 16:28
     * @Param [d1, d2]
     * @return java.util.List<org.halk.halkelasticsearch.pojo.Item>
     **/
    List<Item> findByPriceBetween(Double d1, Double d2);
}
