package com.wxs.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxs.reggie.entity.Dish;
import com.wxs.reggie.mapper.DishMapper;
import com.wxs.reggie.service.DishService;
import org.springframework.stereotype.Service;



@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
