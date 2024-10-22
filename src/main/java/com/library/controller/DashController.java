package com.library.controller;

import com.library.mapper.DashMapper;
import com.library.pojo.Dashboard;
import com.library.utils.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: zbq
 * @Date: 2023/5/29 下午12:33
 */
@RestController
public class DashController {
    @Resource
    private DashMapper mapper;
    @GetMapping("getDash")
    public JsonResult<Dashboard> getDash(){
        mapper.update();
        Dashboard dash = mapper.getDash();
        return new JsonResult<Dashboard>(200,"success",dash);
    }
}
