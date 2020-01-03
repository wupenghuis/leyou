package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuBo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class SpuService {
    @Autowired
    SpuMapper spuMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    BrandMapper brandMapper;
    public PageResult<SpuBo> pageSpu(Integer page, Integer rows, Boolean saleable, String key) {

        //开启分页查询
        PageHelper.startPage(page,rows);
        // 过滤
        Example example = new Example(Spu.class);
        //创建一个查询条件的构造对象
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        // 是否过滤上下架
        if (null != saleable) {
            criteria.andEqualTo("saleable", saleable);
        }
        //判断排序字段不为空

        //直接查询并转换为分页条件结果
        //Page<Spu> brandPage = (Page<Spu>) spuMapper.selectByExample(example);
        Page<Spu> brandPage = (Page<Spu>) spuMapper.selectByExample(example);

        List<Spu> spus=brandPage.getResult();

        List<SpuBo> spuBos=new ArrayList<>();
        //将查到的spu转为SpuDao
        spus.forEach(t->{
            SpuBo spuBo=new SpuBo();
            //属性拷贝
            BeanUtils.copyProperties(t,spuBo);
            // 查询spu的商品分类名称,要查三级分类 cid1，cid2 ,cid3
            List<String> names=this.categoryService.queryNamesByIds(
                    Arrays.asList(t.getCid1(), t.getCid2(), t.getCid3()));
            // 将分类名称拼接后存入
            spuBo.setCname(StringUtils.join(names, "/"));

            //根据品牌的id查询品牌对象，并获取名称
            Brand brand = this.brandMapper.selectByPrimaryKey(t.getBrandId());

            spuBo.setBname(brand.getName());

            spuBos.add(spuBo);
        });

        //封装分页的结果对象
        /*return new PageResult<>(brandPage.getTotal(),new Long(brandPage.getPages()),brandPage);*/
        //把查询的结果整合到pageResult中
        return new PageResult<>(brandPage.getTotal(), new Long(brandPage.getPages()), spuBos);
    }
}
