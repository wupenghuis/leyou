package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryByParentId(@RequestParam("pid") Long id){
        List<Category> list=categoryService.queryByParentId(id);
        if (list != null && 0!=list.size()) {
            return ResponseEntity.ok(list);//返回list集合同时返回状态码200
        }
        return null;
    }
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid") Long bid){
        List<Category> list=this.categoryService.queryByBrandId(bid);
        if (list != null && 0!=list.size()) {
            return ResponseEntity.ok(list);//返回list集合同时返回状态码200
        }
        return null;
    }
}
