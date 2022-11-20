package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.pojo.LoginForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author...Z.Yao..
 * @create 2022-11-17-19:41
 */
public interface AdminService extends IService<Admin> {
     IPage<Admin> getAdmins(Page<Admin> pageParam, String name);

    Admin login(LoginForm loginForm);

    Admin getAdminById(Long userId);
}
