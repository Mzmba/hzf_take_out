package com.ka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ka.pojo.Setmeal;
import com.ka.pojo.SetmealDto;

public interface SetmealService extends IService<Setmeal> {
    //新增套餐，同时要保持与菜品的关联关系
    void saveWithDish(SetmealDto setmealDto);

    SetmealDto getByIdWithDish(Long id);

    void updateWithDish(SetmealDto setmealDto);
}
