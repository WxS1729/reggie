package com.wxs.reggie.controller;


import com.wxs.reggie.service.SetMealService;
import com.wxs.reggie.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {


    @Autowired
    private SetMealService setMealService;

    @Autowired
    private SetmealDishService setmealDishService;


}
