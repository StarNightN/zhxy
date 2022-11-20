package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Grade;
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
 * @create 2022-11-18-15:04
 */
@RestController
@RequestMapping("/sms/gradeController")
@Api(tags = "年级控制器")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/getGrades")
    @ApiOperation("获取所有年级接口")
    public Result getGrade(){
        List<Grade> gardes = gradeService.getGrades();
        return Result.ok(gardes);
    }

    //删除年级
    @ApiOperation(value = "删除年级接口")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@ApiParam("要删除的年纪的id集合") @RequestBody List<Integer> ids){//使用数组接收前端传来的数组
        gradeService.removeByIds(ids);
        return Result.ok();
    }


    @ApiOperation("添加或者修改年纪信息接口")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveUpdateGrade(@ApiParam("年级对象") @RequestBody Grade grade){//使用对象和前端传过来的数据进行匹配接收
        //接收参数
        //调用服务层方法完成添加或者修改
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    @ApiOperation(value = "分页带条件查询所有年级")
    public Result getGrade(@ApiParam("页码数") @PathVariable("pageNo") Integer pageNo,
                           @ApiParam("每页记录数") @PathVariable("pageSize") Integer pageSize,
                           @ApiParam("年级名称") String gradeName){
        //分页条件查询
        Page<Grade> page = new Page<>(pageNo, pageSize);
        //通过服务层查询
        IPage<Grade> pageRs = gradeService.getGradesByOpr(page,gradeName);
        //封装Result对象返回
        return Result.ok(pageRs);
    }
}
