package org.halk.halkelasticsearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Author halk
 * @Description肆佰贰拾六
 * @Date 2020/5/9 0009 22:27
 * @Param
 * @return
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "item", type = "docs", shards = 1, replicas = 0)
public class Item {

    @Id
    Long id;
    /**
     * 标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    String title;
    /**
     *  分类
     */
    @Field(type = FieldType.Keyword)
    String category;
    /**
     *  品牌
     */
    @Field(type = FieldType.Keyword)
    String brand;
    /**
     * 价格
     */
    @Field(type = FieldType.Double)
    Double price;
    /**
     * 图片地址
     */
    @Field(type = FieldType.Keyword, index = false)
    String images;
}
