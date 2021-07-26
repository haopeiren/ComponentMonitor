package com.ffcs.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * JsonUtil
 *
 * @author haopeiren
 * @since 2021/7/20
 */
@UtilityClass
@Slf4j
public class JsonUtil {
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将对象转化为json字符串
     *
     * @param input 需要转换的对象
     * @return json字符串
     */
    public String objectToString(Object input) {
        if (input instanceof String) {
            return (String) input;
        }
        try {
            return objectMapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            log.error("failed to convert value to string");
            return null;
        }
    }
}
