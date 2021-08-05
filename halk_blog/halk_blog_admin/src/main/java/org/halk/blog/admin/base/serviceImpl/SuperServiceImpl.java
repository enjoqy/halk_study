package org.halk.blog.admin.base.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.halk.blog.admin.base.mapper.SuperMapper;
import org.halk.blog.admin.base.service.SuperService;

/**
 * @Author halk
 * @Date 2020/9/29 11:38
 */
public class SuperServiceImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T> implements SuperService<T> {
}
