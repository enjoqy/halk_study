package org.halk.halkelasticsearch;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.halk.halkelasticsearch.pojo.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author halk
 * @Date 2020/5/9 0009 22:31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void test01(){
        this.elasticsearchTemplate.createIndex(Item.class);
        this.elasticsearchTemplate.putMapping(Item.class);
    }

    @Test
    public void test02(){
        Item item = new Item(1L, "小米手机8", " 手机", "小米", 3499.00, "http://image.leyou.com/13123.jpg");
        List<Item> list = new ArrayList<>();
        list.add(new Item(2L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://image.leyou.com/123.jpg"));
        list.add(new Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://image.leyou.com/3.jpg"));
        itemRepository.save(item);
        itemRepository.saveAll(list);

//        itemRepository.deleteById(2L);
    }

    @Test
    public void test03(){
        Optional<Item> item = this.itemRepository.findById(1L);
        System.out.println(item.get());
        System.out.println(item.get().getImages());

        Iterable<Item> all = this.itemRepository.findAll();
        all.forEach(x -> System.out.println(x));

        Iterable<Item> items = this.itemRepository.findAll(Sort.by("price").descending());
        items.forEach(System.out::println);
    }

    @Test
    public void test04(){
        List<Item> items = this.itemRepository.findAllByTitle("华为");
        items.forEach(System.out::println);

        items = this.itemRepository.findByPriceBetween(1000d, 4000d);
        items.forEach(System.out::println);

    }

    @Test
    public void testSearch(){
        //通过查询构建器工具构建查询条件
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "小米");
        //执行查询，获取结果集
        Iterable<Item> items = this.itemRepository.search(matchQueryBuilder);
        items.forEach(System.out::println);
    }

    @Test
    public void testNative(){
        //构建自定义查询器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加基本查询条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "华为"));
        //执行查询获取分页结果集
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        items.forEach(System.out::println);
        System.out.println(items.getTotalPages());
        System.out.println(items.getTotalElements());
        items.getContent().forEach(System.out::println);
    }

    @Test
    public void testPage(){
        //构建自定义查询器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加基本查询条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "手机"));

        //使用分页
//        queryBuilder.withPageable(PageRequest.of(1,2));
        //使用排序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));

        //执行查询获取分页结果集
        Page<Item> items = this.itemRepository.search(queryBuilder.build());

        items.getContent().forEach(System.out::println);
    }

    @Test
    public void testAggs(){
        //初始化自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加聚合
        queryBuilder.addAggregation(AggregationBuilders.terms("brandAgg").field("brand"));
        //添加结果集过滤，不包括任何字段，
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        //执行聚合查询
        AggregatedPage<Item> itemPage = (AggregatedPage<Item>)this.itemRepository.search(queryBuilder.build());

        //解析聚合结果集，根据聚合的类型以及字段类型进行强转，
        StringTerms brandAgg = (StringTerms)itemPage.getAggregation("brandAgg");
        //获取桶的集合
        List<StringTerms.Bucket> buckets = brandAgg.getBuckets();
        buckets.forEach(bucket -> {
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
        });
    }

    @Test
    public void testSubAggs(){
        //初始化自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加聚合，然后添加子聚合
        queryBuilder.addAggregation(AggregationBuilders.terms("brandAgg").field("brand")
                .subAggregation(AggregationBuilders.avg("priceAvg").field("price")));
        //添加结果集过滤，不包括任何字段，
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        //执行聚合查询
        AggregatedPage<Item> itemPage = (AggregatedPage<Item>)this.itemRepository.search(queryBuilder.build());

        //解析聚合结果集，根据聚合的类型以及字段类型进行强转，
        StringTerms brandAgg = (StringTerms)itemPage.getAggregation("brandAgg");
        //获取桶的集合
        List<StringTerms.Bucket> buckets = brandAgg.getBuckets();
        buckets.forEach(bucket -> {
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
            //获取子聚合的map集合：key-聚合名称，value-对应的子聚合对象
            Map<String, Aggregation> stringAggregationMap = bucket.getAggregations().asMap();
            InternalAvg priceAvg = (InternalAvg)stringAggregationMap.get("priceAvg");
            System.out.println(priceAvg.getValue());
            System.out.println(priceAvg.getName());
            System.out.println(priceAvg.getType());
            System.out.println(priceAvg.getMetaData());
        });

    }
}
