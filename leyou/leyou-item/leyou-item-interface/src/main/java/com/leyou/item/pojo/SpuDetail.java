package com.leyou.item.pojo;

import lombok.Data;
import lombok.Value;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author halk
 * @Description
 * @Date 2020/4/21 15:23
 * @Param
 * @return
 **/
@Data
@Table(name = "tb_spu_detail")
public class SpuDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spuId;

    /**
     * 商品描述信息
     */
    private String description;

    /**
     * 通用规格参数数据
     */
    private String genericSpec;

    /**
     * 特有规格参数及可选值信息，json格式
     */
    private String specialSpec;

    /**
     * 包装清单
     */
    private String packingList;

    /**
     * 售后服务
     */
    private String afterService;
}
