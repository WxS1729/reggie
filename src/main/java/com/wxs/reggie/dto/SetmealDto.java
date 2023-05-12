package com.wxs.reggie.dto;

import com.wxs.reggie.entity.Setmeal;
import com.wxs.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
