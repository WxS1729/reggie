package com.wxs.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxs.reggie.common.R;
import com.wxs.reggie.dto.SetmealDto;
import com.wxs.reggie.entity.Setmeal;

import java.util.List;

public interface SetMealService extends IService<Setmeal> {

    /**
     * 新增套餐 保存关联表
     * @param setmealDto
     * @return
     */
    public void saveWithSetmeal(SetmealDto setmealDto);


    /**
     * 根据id删除套餐信息以及绑定的菜品信息
     * @param ids
     */
    public void deleteWitDish(List<Long> ids);
}
