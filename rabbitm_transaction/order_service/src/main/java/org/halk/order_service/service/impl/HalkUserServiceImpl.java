package org.halk.order_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.halk.order_service.entity.HalkUser;
import org.halk.order_service.mapper.HalkUserMapper;
import org.halk.order_service.service.HalkUserService;
import org.springframework.stereotype.Service;

/**
 * (HalkUser)表服务实现类
 *
 * @author zhuhao
 * @since 2021-08-13 10:12:22
 */
@Service("halkUserService")
@RequiredArgsConstructor
public class HalkUserServiceImpl extends ServiceImpl<HalkUserMapper, HalkUser> implements HalkUserService {
    private final HalkUserMapper halkUserMapper;
}
