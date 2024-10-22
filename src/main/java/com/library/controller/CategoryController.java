package com.library.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.library.pojo.Category;
import com.library.service.CategoryService;
import com.library.service.exception.UpdateDataBaseException;
import com.library.utils.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Author ingo
 * @CreatedAt 2023/5/7 16:31
 */
@RestController
@CrossOrigin
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    // 添加Category接口
    @PostMapping("/category/add")
    public JsonResult<?> addCategory(@RequestParam("cateName") String cateName) {
        Category category = new Category();
        category.setCateName(cateName);
        category.setCreateTime(new Date(System.currentTimeMillis()));
        category.setUpdateTime(new Date(System.currentTimeMillis()));
        boolean result = categoryService.save(category);
        return result ? new JsonResult<>(200, "添加成功", null) : new JsonResult<>(500, "添加失败", null);
    }

    // 删除Category接口
    @DeleteMapping("/category/del")
    public JsonResult<Void> deleteCategory(@RequestParam("cid") Integer cid) {
        /*boolean result = categoryService.removeById(cid);
        return result ? new JsonResult<>(200, "删除成功", null) : new JsonResult<>(500, "删除失败", null);*/
        try {
            categoryService.deleteByCid(cid);
            return new JsonResult<Void>(200,"success",null);
        }catch (UpdateDataBaseException e){
            return new JsonResult<Void>(500,"fail",null);
        }
    }

    // 修改Category接口
    @PutMapping("/category/update")
    public JsonResult<?> updateCategory(@RequestParam("cid") Integer cid, @RequestParam("cateName") String cateName) {
        Category category = new Category();
        category.setCid(cid);
        category.setCateName(cateName);
        category.setUpdateTime(new Date(System.currentTimeMillis()));
        boolean result = categoryService.updateById(category);
        return result ? new JsonResult<>(200, "修改成功", null) : new JsonResult<>(500, "修改失败", null);
    }

    // 查询Category列表接口
    @GetMapping("/category/list")
    public JsonResult<List<Category>> getCategoryList() {
        List<Category> categoryList = categoryService.getAllCate();
        if(categoryList!=null){
            return new JsonResult<>(200, "查询成功", categoryList);
        } else {
            return new JsonResult<>(500, "查询失败", null);
        }
    }
}




