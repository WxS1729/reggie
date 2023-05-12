package com.wxs.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxs.reggie.common.CustomException;
import com.wxs.reggie.common.R;
import com.wxs.reggie.dto.SetmealDto;
import com.wxs.reggie.entity.Category;
import com.wxs.reggie.entity.Setmeal;
import com.wxs.reggie.entity.SetmealDish;
import com.wxs.reggie.mapper.SetMealMapper;
import com.wxs.reggie.service.CategoryService;
import com.wxs.reggie.service.SetMealService;
import com.wxs.reggie.service.SetmealDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;
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

    @Transactional
    @Override
    public void deleteWitDish( List<Long> ids) {
        //查看套餐状态信息
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId,ids);
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus,1);

        int count = this.count(setmealLambdaQueryWrapper);
        if (count > 0){
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(queryWrapper);

    }

    @Override
    public SetmealDto showWithDish(Long id) {
        //获取setmeal中的数据
        Setmeal setmeal = this.getById(id);
        SetmealDto setmealDto = new SetmealDto();

        BeanUtils.copyProperties(setmeal,setmealDto);
        //查询setmeal关联的菜品信息
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getSetmealId,id);
        List<SetmealDish> dishList = setmealDishService.list();

        //查询categoryName的值
        Category categoryName = categoryService.getById(setmeal.getCategoryId());

        //组合到serMealDto中
        setmealDto.setCategoryName(categoryName.getName());
        setmealDto.setSetmealDishes(dishList);
        return setmealDto;
    }
}
