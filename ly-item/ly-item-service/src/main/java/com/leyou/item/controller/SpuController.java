package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuBo;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.GoodsService;
import com.leyou.item.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spu")
public class SpuController {
    @Autowired
    SpuService spuService;
    @Autowired
    GoodsService goodsService;
    @GetMapping("page")
    public ResponseEntity<PageResult<SpuBo>> pageSpu(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "10")Integer rows,
            @RequestParam(value = "saleable",required = false)Boolean saleable,
            @RequestParam(value = "key",required = false)String key
    ){
        PageResult<SpuBo> brandPageResult = this.spuService.pageSpu(page,rows,saleable,key);
        //返回商品的分页数据
        if (brandPageResult != null  &&0!=brandPageResult.getItems().size()) {
            return ResponseEntity.ok(brandPageResult);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
