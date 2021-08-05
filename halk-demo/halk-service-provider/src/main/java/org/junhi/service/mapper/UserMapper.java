package org.junhi.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.junhi.service.pojo.User;

/**
 * @Author halk
 * @Date 2019/12/23 14:43
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
