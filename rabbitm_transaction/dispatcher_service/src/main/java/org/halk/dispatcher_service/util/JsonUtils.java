package org.halk.dispatcher_service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author zhuhao
 * @Date 2021/8/13 0013 11:46
 * @desc
 */
@Slf4j
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 对象转换为String
     * @param obj
     * @return
     */
    public static String toStringValue(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json转换异常", e);
        }
        return "";
    }

    /**
     * String转换为对象
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static<T> T parseStr(String str, Class<T> clazz) {
        try {
            return objectMapper.readValue(str, clazz);
        } catch (JsonProcessingException e) {
            log.error("json转换异常", e);
        }
        return null;
    }
}
