package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="tb_spu")
public class Spu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;//` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'spu id',
    private Long brandId;
    private String  title;//` varchar(128) NOT NULL DEFAULT '' COMMENT '标题',
    private String  sub_title;//` varchar(256) DEFAULT '' COMMENT '子标题',
    private Long  cid1;//` bigint(20) NOT NULL COMMENT '1级类目id',
    private Long  cid2;//` bigint(20) NOT NULL COMMENT '2级类目id',
    private Long  cid3;//` bigint(20) NOT NULL COMMENT '3级类目id',
    private Long  brand_id;//` bigint(20) NOT NULL COMMENT '商品所属品牌id',
    private Long  saleable;//` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否上架，0下架，1上架',
    private Long  valid;//` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效，0已删除，1有效',
    private String  create_time;//` datetime DEFAULT NULL COMMENT '添加时间',
    private String  last_update_time;//` datetime DEFAULT NULL COMMENT '最后修改时间',

}
