package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.service.AdminService;
import com.atguigu.zhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author...Z.Yao..
 * @create 2022-11-18-15:03
 */
@RestController
@RequestMapping("/sms/adminController")
@Api(tags = "管理员控制器")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @DeleteMapping("/deleteAdmin")
    @ApiOperation("删除管理员接口")
    public Result deleteAdmin(@RequestBody List<Integer> ids){
        adminService.removeByIds(ids);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdateAdmin")
    @ApiOperation("添加或修改管理员")
    public Result saveOrUpdateAdmin(@ApiParam("添加的管理员JSON格式信息") @RequestBody Admin admin){
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    @GetMapping("/getAllAdmin/{pageNo}/{pageCount}")
    @ApiOperation("查询所有管理员")
    public Result getAllAdmin(@ApiParam("当前页码") @PathVariable("pageNo") Integer pageNo,
                              @ApiParam("当前页记录数") @PathVariable("pageCount") Integer pageCount,
                              String adminName){
        Page<Admin> pageParam = new Page<>(pageNo, pageCount);
        IPage<Admin> pages =adminService.getAdmins(pageParam,adminName);
        return Result.ok(pages);
    }
}
