package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
   // @Insert("insert into tb_category_brand (category_id,brand_id) values(#{cid},#{id})")
    @Insert("insert into tb_category_brand values(#{cid},#{id})")
    void insertCategoryBrand(@Param("cid") Long cid,@Param("id") Long id);
    @Delete("delete from tb_category_brand where brand_id = #{bid}")
    void deleteBrandCategory(@Param("bid") Long bid);

    @Select("select b.name from tb_brand b LEFT JOIN tb_category_brand cb on b.id=cb.brand_id LEFT JOIN tb_category c on cb.category_id=c.id where c.id=#{bid}")
    List<Brand> queryByBrandId(@Param("bid") Long bid);
}
