package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.Grade;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author...Z.Yao..
 * @create 2022-11-17-19:47
 */
public interface GradeService extends IService<Grade> {
    //分页查询所有年纪
    IPage<Grade> getGradesByOpr(Page<Grade> page, String gradeName);

    List<Grade> getGrades();

}
