package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Clazz;
import com.atguigu.zhxy.pojo.Student;
import com.atguigu.zhxy.service.StudentService;
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
 * @create 2022-11-18-15:04
 */
@RestController
@RequestMapping("/sms/studentController")
@Api(tags = "学生控制器")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @DeleteMapping("/delStudentById")
    @ApiOperation("删除单个或者多个学生信息")
    public Result delStudentById(@ApiParam("学生id集合") @RequestBody List<Integer> ids){
        studentService.removeByIds(ids);
        return Result.ok();
    }



    @PostMapping("/addOrUpdateStudent")
    @ApiOperation("添加或者修改学生信息")
    public Result addOrUpdateStudent(@ApiParam("学生JSON数据") @RequestBody Student student){
        Integer id = student.getId();
        if(null == id || 0 == id){
          student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }


    @GetMapping("/getStudentByOpr/{pageNo}/{pageCount}")
    @ApiOperation("分页带条件查询学生")
    public Result getStudents(@ApiParam("当前页码") @PathVariable("pageNo") Integer pageNo,
                              @ApiParam("当前页记录数") @PathVariable("pageCount") Integer pageCount,
                              String name,
                              String clazzName){
        IPage<Student> pageParam = new Page<>(pageNo, pageCount);
        IPage<Student> students = studentService.getStudents(pageParam,name, clazzName);
        return Result.ok(students);
    }

}
