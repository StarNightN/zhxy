package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Clazz;
import com.atguigu.zhxy.pojo.Grade;
import com.atguigu.zhxy.service.ClazzService;
import com.atguigu.zhxy.service.GradeService;
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
@RequestMapping("/sms/clazzController")
@Api(tags = "班级控制器")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private GradeService gradeService;

    @DeleteMapping("/deleteClazz")
    @ApiOperation("删除单个和多个班级信息")
    public Result deleteClazz(@ApiParam("要删除的班级id集合") @RequestBody List<Integer> ids){
        clazzService.removeByIds(ids);
        return Result.ok();
    }


    @GetMapping("/getClazzsByOpr/{pageNo}/{pageCount}")
    @ApiOperation("分页条件查询查询班级")
    public Result getClazzsByOpr(@ApiParam("分页页码") @PathVariable("pageNo") Integer pageNo,
                                 @ApiParam("每页记录数") @PathVariable("pageCount") Integer pageCount,
                                 String gradeName,
                                 String name){

        IPage<Clazz> pageParam = new Page<>(pageNo,pageCount);
        IPage<Clazz> clazzs =  clazzService.getClazzs(pageParam,gradeName,name);
        return Result.ok(clazzs);
    }

    @PostMapping("/saveOrUpdateClazz")
    @ApiOperation("添加或修改班级接口")
    public Result saveOrUpdateClazz(@ApiParam("JSON格式的班级信息") @RequestBody Clazz clazz){//Clazz对象接收请求体中的数据
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    @GetMapping("/getClazzs")
    @ApiOperation("查询所有班级")
    public Result getClazzs(){
        List<Clazz> clazzes = clazzService.findClazzs();
        return Result.ok(clazzes);
    }


}
