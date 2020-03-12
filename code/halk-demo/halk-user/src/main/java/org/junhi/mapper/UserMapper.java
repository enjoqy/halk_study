package org.junhi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.junhi.pojo.User;

/**
 * @author junhi
 * @date 2019/12/22 14:59
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
