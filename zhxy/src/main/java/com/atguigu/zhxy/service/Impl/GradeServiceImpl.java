package com.atguigu.zhxy.service.Impl;

import com.atguigu.zhxy.mapper.GradeMapper;
import com.atguigu.zhxy.pojo.Grade;
import com.atguigu.zhxy.service.GradeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author...Z.Yao..
 * @create 2022-11-17-19:47
 */
@Service("gradeServiceImpl")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Override
    public IPage<Grade> getGradesByOpr(Page<Grade> pageParam, String gradeName) {
        LambdaQueryWrapper<Grade> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(gradeName)){//模糊查询
            wrapper.like(Grade::getName,gradeName);
        }
        wrapper.orderByDesc(Grade::getId);//排序
        Page<Grade> page = baseMapper.selectPage(pageParam, wrapper);
        return page;
    }

    @Override
    public List<Grade> getGrades() {
        List<Grade> grades = baseMapper.selectList(null);
        return grades;
    }
}
