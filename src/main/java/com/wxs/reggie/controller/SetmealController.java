package com.wxs.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxs.reggie.common.R;
import com.wxs.reggie.dto.SetmealDto;
import com.wxs.reggie.entity.Category;
import com.wxs.reggie.entity.Setmeal;
import com.wxs.reggie.service.CategoryService;
import com.wxs.reggie.service.SetMealService;
import com.wxs.reggie.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {


    @Autowired
    private SetMealService setMealService;

    @Autowired
    private CategoryService categoryService;



    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("dto = {}",setmealDto);
        setMealService.saveWithSetmeal(setmealDto);
        return R.success("添加菜品成功");
    }

    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize,String name){
        //创建分也够早起
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);

        Page<SetmealDto> setmealDtoPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //根据name查询数据
        queryWrapper.eq(name != null,Setmeal::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        //进行分页查询
        setMealService.page(pageInfo,queryWrapper);

        BeanUtils.copyProperties(pageInfo,setmealDtoPage,"records");

        List<Setmeal> setmeals = pageInfo.getRecords();
        //添加分类名称到分页信息中
        List<SetmealDto> list = setmeals.stream().map( (item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            //根据id查询分类
            Category category = categoryService.getById(categoryId);
            if (category != null){
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(list);
        return R.success(setmealDtoPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids = {}",ids);
        setMealService.deleteWitDish(ids);
        return R.success("删除成功");
    }

    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable Integer status,@RequestParam Long[] ids){
        for (Long id : ids) {
            if (id != null && status != null){
                Setmeal setmeal = setMealService.getById(id);
                setmeal.setStatus(status);
                setMealService.updateById(setmeal);
            }
        }
        return R.success("修改状态成功");
    }



}
