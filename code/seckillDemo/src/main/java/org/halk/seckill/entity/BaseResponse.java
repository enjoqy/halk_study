package org.halk.seckill.entity;

import lombok.Data;

/**
 * @Author halk
 * @Date 2020/8/6 0006 10:57
 */
@Data
public class BaseResponse<T> {

    private Integer code;
    private String msg;
    private T data;

    public BaseResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(Integer code) {
        this.code = code;
    }

    public BaseResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse() {
    }
}
