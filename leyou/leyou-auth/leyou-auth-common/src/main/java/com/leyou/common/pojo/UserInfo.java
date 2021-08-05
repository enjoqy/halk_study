package com.leyou.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author halk
 * @Description 载荷对象
 * @Date 2020/6/1 0001 16:36
 * @Param
 * @return
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Long id;

    private String username;

}
