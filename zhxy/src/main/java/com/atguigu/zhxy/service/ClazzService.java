package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.Clazz;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author...Z.Yao..
 * @create 2022-11-17-19:45
 */
public interface ClazzService extends IService<Clazz> {
    IPage<Clazz> getClazzs(IPage<Clazz> pageParam,String gardeName,String name);

    List<Clazz> findClazzs();
}
