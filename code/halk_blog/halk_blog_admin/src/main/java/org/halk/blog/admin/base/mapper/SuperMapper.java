package org.halk.blog.admin.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * mapper 父类，注意这个类不要让 mybatis-plus 扫描到！！
 *
 * @Author halk
 * @Date 2020/9/29 11:37
 */
public interface SuperMapper<T> extends BaseMapper<T> {
}
