package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author halk
 * @Date 2020/4/21 15:31
 */
@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private  CategoryService categoryService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private final static Logger LOGGER = LoggerFactory.getLogger(GoodsService.class);

    /**
     * @return com.leyou.common.pojo.PageResult<com.leyou.item.bo.SpuBo>
     * @Author halk
     * @Description 根据分页条件查询spu
     * @Date 2020/4/21 16:49
     * @Param [key, saleable, page, rows]
     **/
    public PageResult<SpuBo> querySpuByPage(String key, String saleable, Integer page, Integer rows) {

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        //添加查询条件
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("title", "%" + key + "%");
        }

        //添加上下架的过滤条件
        if (StringUtils.isNotEmpty(saleable)){
            criteria.andEqualTo("saleable", saleable);
        }

        if(rows == -1){
            rows = 0;
        }

        //添加分页，这行代码下面的查询会被分页
        PageHelper.startPage(page, rows);

        //执行查询，获取spu集合
        List<Spu> spus = this.spuMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo(spus);

        //spu转化为spuBo集合
        List<SpuBo> spuBoList = spus.stream().map(spu -> {
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu, spuBo);

            //查询品牌名称
            Brand brand = this.brandMapper.selectByPrimaryKey(spuBo.getBrandId());
            spuBo.setBname(brand.getName());
            //查询品牌分类
            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(names, "-"));
            return spuBo;
        }).collect(Collectors.toList());

        //返回pageResult<spuBo>
        return new PageResult<>(pageInfo.getTotal(), spuBoList);
    }

    /**
     * @Author halk
     * @Description 新增商品
     * @Date 2020/4/28 0028 17:29
     * @Param [spuBo]
     * @return void
     **/
    /**
     * 在@Transactional注解中如果不配置rollbackFor属性,那么事物只会在遇到RuntimeException的时候才会回滚,
     * 加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveGoods(SpuBo spuBo) {

        //新增顺序，spu,spuDetail,sku,stock
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        this.spuMapper.insert(spuBo);

        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailMapper.insert(spuDetail);

        SaveSkuAndStock(spuBo);

        //使用管道同步调用
        this.sendMsg("insert", spuBo.getId());

    }

    /**
     * @Author halk
     * @Description 抽取，发送消息至队列中
     * @Date 2020/5/29 0029 9:18
     * @Param [type, id]
     * @return void
     **/
    private void sendMsg(String type, Long id) {
        try {
            this.amqpTemplate.convertAndSend("item." + type, id);
        } catch (AmqpException e) {
            LOGGER.info("发送消息至管道失败：{}" + e, id);
            e.printStackTrace();
        }
    }

    private void SaveSkuAndStock(SpuBo spuBo) {
        spuBo.getSkus().forEach(sku -> {
            sku.setId(null);
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insert(sku);

            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insert(stock);
        });
    }

    /**
     * @Author halk
     * @Description 根据id查询spuDetail
     * @Date 2020/4/29 0029 11:04
     * @Param [spuId]
     * @return com.leyou.item.pojo.SpuDetail
     **/
    public SpuDetail querySpuDetailById(Long spuId) {
        return this.spuDetailMapper.selectByPrimaryKey(spuId);
    }

    /**
     * @Author halk
     * @Description 根据spuId查询sku集合
     * @Date 2020/4/29 0029 11:11
     * @Param [spuId]
     * @return java.util.List<com.leyou.item.pojo.Sku>
     **/
    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
                List<Sku> skuList = this.skuMapper.select(sku);
        skuList.forEach(x -> {
            Stock stock = this.stockMapper.selectByPrimaryKey(x.getId());
            x.setStock(stock.getStock());
        });
        return skuList;
    }

    /**
     * @Author halk
     * @Description 更新商品
     * @Date 2020/4/29 0029 11:52
     * @Param [spuBo]
     * @return void
     **/
    @Transactional(rollbackFor = Exception.class)
    public void updateGoods(SpuBo spuBo) {
        //先更新子表在更新主表，先删除在插入，有些字段不需要用户更新
        //先删除stock，sku，在新增，在更新spu，spuDetail
        Sku record = new Sku();
        record.setSpuId(spuBo.getId());
        List<Sku> skuList = this.skuMapper.select(record);
        skuList.forEach(sku -> {
            //删除stock
            this.stockMapper.deleteByPrimaryKey(sku.getId());
        });

        //删除sku
        this.skuMapper.delete(record);

        //保存stock，sku
        SaveSkuAndStock(spuBo);

        //更新spu,spuDetail
        spuBo.setCreateTime(null);
        spuBo.setLastUpdateTime(null);
        spuBo.setSaleable(null);
        spuBo.setValid(null);
        this.spuMapper.updateByPrimaryKeySelective(spuBo);
        this.spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());

        //使用管道同步调用
        this.sendMsg("update", spuBo.getId());
    }

    /**
     * @Author halk
     * @Description 逻辑删除商品
     * @Date 2020/5/3 0003 20:16
     * @Param [spuId]
     * @return void
     **/
    @Transactional(rollbackFor = Exception.class)
    public void deleteSpuDetailById(Long spuId) {
        //更新spu，sku的状态

        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setValid(false);
        spu.setLastUpdateTime(new Date());
        this.spuMapper.updateByPrimaryKeySelective(spu);

        Sku sku = new Sku();
        sku.setSpuId(spuId);
        sku.setLastUpdateTime(new Date());
        sku.setEnable(false);
        this.skuMapper.updateSkuBySpuid(sku);

        //使用管道同步调用
        this.sendMsg("insert", spuId);
    }

    /**
     * @Author halk
     * @Description 根据id查询spu
     * @Date 2020/5/24 0024 10:22
     * @Param [id]
     * @return com.leyou.item.pojo.Spu
     **/
    public Spu querySpuById(Long id) {
        return this.spuMapper.selectByPrimaryKey(id);
    }

    /**
     * @Author halk
     * @Description 根据id查询sku
     * @Date 2020/6/8 0008 11:31
     * @Param [skuId]
     * @return com.leyou.item.pojo.Sku
     **/
    public Sku querySkuById(Long skuId) {
        return this.skuMapper.selectByPrimaryKey(skuId);
    }
}
