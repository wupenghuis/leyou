package com.leyou.item.mapper;

import com.leyou.item.pojo.Spu;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface SpuMapper extends Mapper<Spu> {
    @Select("SELECT s.id,s.title,c.name,b.name from tb_spu s LEFT JOIN tb_category c on s.cid3=c.id LEFT JOIN tb_brand b on s.brand_id=b.id")
    List<Spu> selectByExamples(Example example);
}
