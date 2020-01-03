package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> pageQuery(Integer page, Integer rows, String sortBy, Boolean desc, String key) {

        //开启分页查询
        PageHelper.startPage(page,rows);
        // 过滤
        Example example = new Example(Brand.class);

        if (StringUtils.isNotBlank(key)){

            //创建一个查询条件的构造对象
            Example.Criteria criteria = example.createCriteria();

            criteria.andLike("name","%"+key+"%");
        }

        //判断排序字段不为空
        if (StringUtils.isNotBlank(sortBy)){

            //desc 为true ，倒序
            example.setOrderByClause(sortBy+ (desc ? " DESC":" ASC")); //order by id desc
        }

        //直接查询并转换为分页条件结果
        Page<Brand> brandPage = (Page<Brand>) brandMapper.selectByExample(example);

        //封装分页的结果对象
        return new PageResult<>(brandPage.getTotal(),new Long(brandPage.getPages()),brandPage);
    }
    @Transactional
    public void insertBand(Brand brand, List<Long> cids) {
        this.brandMapper.insertSelective(brand);
        for (Long cid:cids) {
            this.brandMapper.insertCategoryBrand(brand.getId(),cid);
        }
    }
    @Transactional
    public void updateBrand(Brand brand, List<Long> cids) {
        //直接修改
        this.brandMapper.updateByPrimaryKeySelective(brand);
        //删除这个品牌之前的所有的关联分类，然后重新关联品牌和分类
        //根据品牌的id删除
        this.brandMapper.deleteBrandCategory(brand.getId());
        cids.forEach(cid->{
            this.brandMapper.insertCategoryBrand(cid,brand.getId());
        });

    }

    public List<Brand> queryByBrandId(Long bid) {
        return this.brandMapper.queryByBrandId(bid);
    }
}
