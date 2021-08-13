package org.halk.order_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.halk.order_service.entity.HalkOrderMessage;
import org.springframework.stereotype.Repository;

/**
 * 订单消息冗余表(HalkOrderMessage)表数据库访问层
 *
 * @author zhuhao
 * @since 2021-08-13 10:12:22
 */
@Repository
public interface HalkOrderMessageMapper extends BaseMapper<HalkOrderMessage> {


}
