package com.leyou.auth.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author halk
 * @Date 2020/6/1 0001 17:33
 */
@FeignClient("user-service")
public interface UserClient extends UserApi {
}
