package org.halk.rabbitmqdemp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author halk
 * @Date 2020/7/24 0024 17:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private Integer id;
    private String message;
}
