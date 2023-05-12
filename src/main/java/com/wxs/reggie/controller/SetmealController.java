package com.wxs.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxs.reggie.common.R;
import com.wxs.reggie.dto.SetmealDto;
import com.wxs.reggie.entity.Setmeal;
import com.wxs.reggie.service.SetMealService;
import com.wxs.reggie.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {


    @Autowired
    private SetMealService setMealService;

    @Autowired
    private SetmealDishService setmealDishService;


    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("dto = {}",setmealDto);
        setMealService.saveWithSetmeal(setmealDto);
        return R.success("添加菜品成功");
    }




}
