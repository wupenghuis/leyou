package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.BrandService;
import com.leyou.item.service.CategoryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> pageQuery(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "10")Integer rows,
            @RequestParam(value = "sortBy",required = false)String sortBy,
            @RequestParam(value = "desc",required = false)Boolean desc,
            @RequestParam(value = "key",required = false)String key
    ){
        PageResult<Brand> brandPageResult = this.brandService.pageQuery(page,rows,sortBy,desc,key);
        //判断分页数据不为空
        if (null!=brandPageResult && null!=brandPageResult.getItems() && 0!=brandPageResult.getItems().size()) {
            return ResponseEntity.ok(brandPageResult);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 添加
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> insertBand(Brand brand, @RequestParam("cids") List<Long> cids){
        this.brandService.insertBand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();//201
    }


    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand,@RequestParam("cids") List<Long> cids){

        this.brandService.updateBrand(brand,cids);

        return ResponseEntity.status(HttpStatus.CREATED).build();//201
    }
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryByBrandId(@PathVariable("bid") Long bid){
        List<Brand> list=this.brandService.queryByBrandId(bid);
        if (list != null && 0!=list.size()) {
            return ResponseEntity.ok(list);//返回list集合同时返回状态码200
        }
        return null;
    }
}
