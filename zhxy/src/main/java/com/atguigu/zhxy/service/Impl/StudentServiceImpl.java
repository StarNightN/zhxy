package com.atguigu.zhxy.service.Impl;

import com.atguigu.zhxy.mapper.StudentMapper;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Student;
import com.atguigu.zhxy.service.StudentService;
import com.atguigu.zhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author...Z.Yao..
 * @create 2022-11-17-19:49
 */
@Service("studentServiceImpl")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Student login(LoginForm loginForm) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getName,loginForm.getUsername())
                .eq(Student::getPassword, MD5.encrypt(loginForm.getPassword()));
        Student student = baseMapper.selectOne(wrapper);
        return student;
    }

    @Override
    public Student getStudentById(Long userId) {
        Student student = baseMapper.selectById(userId);
        return student;
    }

    @Override
    public IPage<Student> getStudents(IPage<Student> pageParam, String name, String clazzName) {

        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            wrapper.like(Student::getName,name);
        }
        if(!StringUtils.isEmpty(clazzName)){
            wrapper.like(Student::getClazzName,clazzName);
        }
        IPage<Student> page = baseMapper.selectPage(pageParam, wrapper);
        return page;
    }
}
