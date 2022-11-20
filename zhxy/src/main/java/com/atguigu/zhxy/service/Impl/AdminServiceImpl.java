package com.atguigu.zhxy.service.Impl;

import com.atguigu.zhxy.mapper.AdminMapper;
import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.service.AdminService;
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
 * @create 2022-11-17-19:41
 */
@Service("adminServiceImpl")
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Override
    public IPage<Admin> getAdmins(Page<Admin> pageParam,String name) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(Admin::getName,name);
        }
        wrapper.orderByDesc(Admin::getId);
        IPage<Admin> pages= baseMapper.selectPage(pageParam, wrapper);
        return pages;
    }

    @Override
    public Admin login(LoginForm loginForm) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getName,loginForm.getUsername()).
                eq(Admin::getPassword, MD5.encrypt(loginForm.getPassword()));
        Admin admin = baseMapper.selectOne(wrapper);
        return admin;
    }

    @Override
    public Admin getAdminById(Long userId) {
        Admin admin = baseMapper.selectById(userId);
        return admin;
    }
}
