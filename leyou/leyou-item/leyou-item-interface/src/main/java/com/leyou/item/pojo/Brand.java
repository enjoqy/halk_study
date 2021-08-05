package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Table(name = "tb_brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;// 品牌名称
    private String image;// 品牌图片
    private Character letter;
    // getter setter 略

}
