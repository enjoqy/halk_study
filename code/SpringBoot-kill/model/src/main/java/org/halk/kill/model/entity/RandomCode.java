package org.halk.kill.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * random_code
 * @author
 */
@Data
public class RandomCode implements Serializable {
    private Integer id;

    private String code;

    private static final long serialVersionUID = 1L;
}
