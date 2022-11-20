package com.atguigu.zhxy.service.Impl;

import com.atguigu.zhxy.mapper.ClazzMapper;
import com.atguigu.zhxy.pojo.Clazz;
import com.atguigu.zhxy.service.ClazzService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author...Z.Yao..
 * @create 2022-11-17-19:46
 */
@Service("clazzServiceImpl")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Override
    public IPage<Clazz> getClazzs(IPage<Clazz> pageParam, String gradeName, String name) {
        LambdaQueryWrapper<Clazz> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            wrapper.like(Clazz::getName,name);
        }
        if(!StringUtils.isEmpty(gradeName)){
            wrapper.like(Clazz::getGradeName,gradeName);
        }
        IPage<Clazz> page = baseMapper.selectPage(pageParam, wrapper);

        return page;
    }

    @Override
    public List<Clazz> findClazzs() {
        List<Clazz> clazzes = baseMapper.selectList(null);
        return clazzes;
    }
}
