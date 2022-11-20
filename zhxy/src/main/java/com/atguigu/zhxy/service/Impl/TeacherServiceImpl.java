package com.atguigu.zhxy.service.Impl;

import com.atguigu.zhxy.mapper.TeacherMapper;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Teacher;
import com.atguigu.zhxy.service.TeacherService;
import com.atguigu.zhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author...Z.Yao..
 * @create 2022-11-17-19:51
 */
@Service("teacherServiceImpl")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher login(LoginForm loginForm) {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getName,loginForm.getUsername())
                .eq(Teacher::getPassword, MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = baseMapper.selectOne(wrapper);
        return teacher;
    }

    @Override
    public Teacher getTeacherById(Long userId) {
        Teacher teacher = baseMapper.selectById(userId);
        return teacher;
    }

    @Override
    public IPage<Teacher> getAllTeacher(Page<Teacher> pageParam,String name,String clazzName) {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(Teacher::getName,name);
        }
        if (!StringUtils.isEmpty(clazzName)) {
            wrapper.like(Teacher::getClazzName,clazzName);
        }
        wrapper.orderByDesc(Teacher::getId);
        IPage<Teacher> pages = baseMapper.selectPage(pageParam, wrapper);
        return pages;
    }
}
