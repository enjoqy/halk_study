package com.leyou.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.item.pojo.*;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.client.SpecificationClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.repository.GoodsRepository;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 构造一个获取模型数据的方法
 *
 * @Author halk
 * @Date 2020/5/14 0014 10:17
 */
@Service
public class SearchService {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private GoodsRepository goodsRepository;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Goods buildGoods(Spu spu) throws IOException {
        Goods goods = new Goods();

        //根据分类id查询分类名称
        List<String> names = this.categoryClient.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));

        //根据bid获取品牌名称
        Brand brand = this.brandClient.queryBrandById(spu.getBrandId());

        //根据spuId获取sku集合
        List<Sku> skus = this.goodsClient.querySkusBySpuId(spu.getId());
        //收集sku的必要字段信息
        List<Map<String, Object>> skuMapList = new ArrayList<>();

        //初始化一个价格集合，收集所有的sku价格
        List<Long> priceList = skus.stream().map(sku -> {

            Map<String, Object> map = new LinkedHashMap<>(7);
            map.put("id", sku.getId());
            map.put("title", sku.getTitle());
            map.put("price", sku.getPrice());

            //获取sku中图片，数据库中的图片可能是多张，以“，”分割，获取第一张图片
            map.put("image", StringUtils.isBlank(sku.getImages()) ? " " : StringUtils.split(sku.getImages(), ",")[0]);

            skuMapList.add(map);
            return sku.getPrice();
        }).collect(Collectors.toList());

        // 根据spu中的cid3查询出所有的搜索规格参数
        List<SpecParam> specParams = this.specificationClient.queryParams(null, spu.getCid3(), null, true);

        //根据spuId查询spuDetail
        SpuDetail spuDetail = this.goodsClient.querySpuDetailById(spu.getId());
        //把通用的规格参数值，进行反序列化
        LinkedHashMap<String, Object> genericSpecMap = MAPPER.readValue(spuDetail.getGenericSpec(), new TypeReference<LinkedHashMap<String, Object>>() {
        });
        //对特有的规格参数进行反序列化
        LinkedHashMap<String, List<Object>> specialSpecMap = MAPPER.readValue(spuDetail.getSpecialSpec(), new TypeReference<LinkedHashMap<String, List<Object>>>() {
        });

        Map<String, Object> specs = new LinkedHashMap<>();
        specParams.forEach(specParam -> {
            //判断规格参数的类型，是否是通用的规格参数
            if (specParam.getGeneric()) {
                //如果是通用类型的参数，从genericSpecMap获取规格参数
                String value = genericSpecMap.get(specParam.getId().toString()).toString();
                //判断是否是数值类型，如果是数值类型，返回一个区间
                if (specParam.getNumeric()) {
                    value = this.chooseSegment(value, specParam);
                }
                specs.put(specParam.getName(), value);
            } else {
                //特殊的规格参数
                specs.put(specParam.getName(), specialSpecMap.get(specParam.getId().toString()));
            }
        });

        goods.setId(spu.getId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setBrandId(spu.getBrandId());
        goods.setSubTitle(spu.getSubTitle());
        goods.setCreateTime(spu.getCreateTime());
        //拼接all字段
        goods.setAll(spu.getSubTitle() + " " + StringUtils.join(names, " ") + " " + brand.getName());
        //获取spu下的所有sku的价格
        goods.setPrice(priceList);
        //获取spu下的所有sku的集合并转化为json字符串
        goods.setSkus(MAPPER.writeValueAsString(skuMapList));
        //获取所有查询的规格参数{name: value}
        goods.setSpecs(specs);

        return goods;
    }

    /**
     * @return java.lang.String
     * @Author halk
     * @Description 对传入的数值进行判断属于哪个区间，并加上单位
     * @Date 2020/5/14 0014 17:43
     * @Param [value, specParam]
     **/
    private String chooseSegment(String value, SpecParam specParam) {

        double val = NumberUtils.toDouble(value);
        String result = "其他";

        //保存设置段
        for (String segment : specParam.getSegments().split(",")) {
            String[] segs = segment.split("-");

            //获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if (segs.length == 2) {
                end = NumberUtils.toDouble(segs[1]);
            }

            //判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = begin + specParam.getUnit() + "以上";
                } else if (begin == 0) {
                    result = end + specParam.getUnit() + "以下";
                } else {
                    result = segment + specParam.getUnit();
                }
            }
        }

        return result;
    }

    /**
     * @return com.leyou.common.pojo.PageResult<com.leyou.search.pojo.Goods>
     * @Author halk
     * @Description 搜索商品
     * @Date 2020/5/18 0018 11:37
     * @Param [request]
     **/
    public SearchResult search(SearchRequest request) {
        if (request == null) {
            return null;
        }
        //自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        QueryBuilder basicQuery = QueryBuilders.matchQuery("all", request.getKey()).operator(Operator.AND);

        //构建一个布尔查询
        QueryBuilder basicQuery = buildBoolQueryBuilder(request);

        //添加查询条件
        queryBuilder.withQuery(basicQuery);
        //添加分页，分页页码从0开始
        queryBuilder.withPageable(PageRequest.of(request.getPage() - 1, request.getSize()));
        //添加结果集过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "skus", "subTitle"}, null));

        //添加分类和品牌的聚合
        String categoryAggName = "categories";
        String brandAggName = "brands";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        //执行查询
        AggregatedPage<Goods> pageResult = (AggregatedPage<Goods>) this.goodsRepository.search(queryBuilder.build());

        //获取聚合结果集并解析
        List<Map<String, Object>> categories = getCategoryAggResult(pageResult.getAggregation(categoryAggName));
        List<Brand> brands = getBrandAggResult(pageResult.getAggregation(brandAggName));

        List<Map<String, Object>> specs = null;
        //判断是否是一个分类，只有一个分类时才做规格参数聚合
        if(!CollectionUtils.isEmpty(categories) && categories.size() == 1){
            //对规格参数进行聚合
            specs = getParamAggResult((Long)categories.get(0).get("id"), basicQuery);
        }
        return new SearchResult(pageResult.getTotalElements(), pageResult.getTotalPages(), pageResult.getContent(), categories, brands, specs);
    }

    /**
     * 构建一个布尔查询
     * @param request
     * @return
     */
    private BoolQueryBuilder buildBoolQueryBuilder(SearchRequest request) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //给布尔查询添加基本查询条件
        boolQueryBuilder.must(QueryBuilders.matchQuery("all", request.getKey()).operator(Operator.AND));
        //添加过滤条件
        //获取用户选择的过滤信息
        Map<String, Object> filter = request.getFilter();
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.equals("品牌", key)){
                key = "brandId";
            }else if (StringUtils.equals("分类", key)){
                key = "cid3";
            }else {
                key = "specs." + key + ".keyword";
            }
            boolQueryBuilder.filter(QueryBuilders.termsQuery(key, entry.getValue()));
        }

        return boolQueryBuilder;
    }

    /**
     * @Author halk
     * @Description 根据查询条件聚合规格参数
     * @Date 2020/5/19 0019 17:33
     * @Param [id, basicQuery]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    private List<Map<String, Object>> getParamAggResult(Long cid, QueryBuilder basicQuery) {
        //自定义查询条件构建
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //添加基本查询条件
        queryBuilder.withQuery(basicQuery);

        //查询要聚合的规格参数
        List<SpecParam> specParams = this.specificationClient.queryParams(null, cid, null, true);

        //添加要聚合的规格参数
        specParams.forEach(specParam -> {
            queryBuilder.addAggregation(AggregationBuilders.terms(specParam.getName()).field("specs." + specParam.getName() + ".keyword"));
        });

        //添加结果集过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));

        //执行聚合查询
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>)this.goodsRepository.search(queryBuilder.build());

        List<Map<String, Object>> specs = new ArrayList<>();
        //解析聚合结果集
        Map<String, Aggregation> aggregationMap = goodsPage.getAggregations().asMap();
        for (Map.Entry<String, Aggregation> entry : aggregationMap.entrySet()) {
            //初始化一个map，{k：规格参数名，options：聚合的规格参数值}
            Map<String, Object> map = new HashMap<>(4);
            map.put("k", entry.getKey());
            //初始化一个options集合，手机桶中的key
            ArrayList<String> options = new ArrayList<>();
            //获取聚合
            StringTerms terms = (StringTerms)entry.getValue();
            //获取桶中的集合
            terms.getBuckets().forEach(bucket -> {
                options.add(bucket.getKeyAsString());
            });
            map.put("options",options);
            specs.add(map);
        }

        return specs;
    }

    /**
     * @return java.util.List<com.leyou.item.pojo.Brand>
     * @Author halk
     * @Description 解析品牌的聚合结果集
     * @Date 2020/5/19 0019 15:19
     * @Param [aggregation]
     **/
    private List<Brand> getBrandAggResult(Aggregation aggregation) {
        LongTerms terms = (LongTerms) aggregation;
        return terms.getBuckets().stream().map(bucket ->
                this.brandClient.queryBrandById(bucket.getKeyAsNumber().longValue())
        ).collect(Collectors.toList());
    }

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author halk
     * @Description 解析分类的聚合结果集
     * @Date 2020/5/19 0019 15:18
     * @Param [aggregation]
     **/
    private List<Map<String, Object>> getCategoryAggResult(Aggregation aggregation) {
        LongTerms terms = (LongTerms)aggregation;

        //获取桶的集合，转化为List<Map<String, Object>>
        return terms.getBuckets().stream().map(bucket -> {
            //初始化一个map
            Map<String, Object> map = new HashMap<>(4);
            //获取桶中的分类id（key）
            Long id = bucket.getKeyAsNumber().longValue();
            //根据分类id查询分类名称
            List<String> names = this.categoryClient.queryNamesByIds(Arrays.asList(id));
            map.put("id", id);
            map.put("name", names.get(0));
            return map;
        }).collect(Collectors.toList());
    }

    /**
     * 保存一条数据至Elasticsearch中，监听中的异常层层上抛
     * @param id
     * @throws IOException
     */
    public void save(Long id) throws IOException {
        Spu spu = this.goodsClient.querySpuById(id);
        Goods goods = this.buildGoods(spu);
        this.goodsRepository.save(goods);
    }

    /**
     * @Author halk
     * @Description 在ElasticSearch中，删除一条数据
     * @Date 2020/5/28 0028 23:00
     * @Param [id]
     * @return void
     **/
    public void delete(Long id) {
        this.goodsRepository.deleteById(id);
    }
}
