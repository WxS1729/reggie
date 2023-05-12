package com.wxs.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxs.reggie.common.R;
import com.wxs.reggie.dto.SetmealDto;
import com.wxs.reggie.entity.Setmeal;
import com.wxs.reggie.entity.SetmealDish;
import com.wxs.reggie.mapper.SetMealMapper;
import com.wxs.reggie.service.SetMealService;
import com.wxs.reggie.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐 保存关联表
     * @param setmealDto
     * @return
     */
    @Transactional
    @Override
    public void saveWithSetmeal(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal 执行insert操作
        this.save(setmealDto);

        //保存信息到关联表中
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map(item ->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }
}
