package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Teacher;
import com.atguigu.zhxy.service.TeacherService;
import com.atguigu.zhxy.util.MD5;
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
 * @create 2022-11-18-15:05
 */
@RestController
@RequestMapping("/sms/teacherController")
@Api(tags = "教师控制器")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    @PostMapping("/saveOrUpdateTeacher")
    @ApiOperation("添加或者修改老师信息")
    public Result saveOrUpdateTeacher(@ApiParam("添加或修改的JSON格式老师信息") @RequestBody Teacher teacher){
        if(teacher.getId()==null||teacher.getId()==0){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @DeleteMapping("/deleteTeacher")
    @ApiOperation("删除一个或者多个老师信息")
    public Result deleteTeacher(@ApiParam("要删除老师的id集合") @RequestBody List<Integer> ids){
        teacherService.removeByIds(ids);
        return Result.ok();
    }


    @GetMapping("/getTeachers/{pageNo}/{pageCount}")
    @ApiOperation("查询所有教师")
    public Result getTeachers(@ApiParam("当前页码") @PathVariable("pageNo") Integer pageNo,
                              @ApiParam("当前页记录数") @PathVariable("pageCount") Integer pageCount,
                              String name,
                              String clazzName){
        Page<Teacher> pageParam = new Page<>(pageNo,pageCount);
        IPage<Teacher> pages = teacherService.getAllTeacher(pageParam,name,clazzName);
        return Result.ok(pages);
    }
}
