package com.leyou.item.mapper;

import com.leyou.item.pojo.Sku;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author halk
 * @Date 2020/4/28 0028 17:21
 */
public interface SkuMapper extends Mapper<Sku> {

    /**
     * @Author halk
     * @Description 更新sku商品为无效状态
     * @Date 2020/5/3 0003 22:41
     * @Param [sku]
     * @return void
     **/
    @Update("update tb_sku set `last_update_time` = #{lastUpdateTime} , `enable`= #{enable} where `spu_id` = #{spuId}")
    void updateSkuBySpuid(Sku sku);
}
