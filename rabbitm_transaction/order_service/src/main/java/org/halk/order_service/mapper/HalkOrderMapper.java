package org.halk.order_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.halk.order_service.entity.HalkOrder;
import org.springframework.stereotype.Repository;

/**
 * 订单表(HalkOrder)表数据库访问层
 *
 * @author zhuhao
 * @since 2021-08-13 10:12:17
 */
@Repository
public interface HalkOrderMapper extends BaseMapper<HalkOrder> {


}
