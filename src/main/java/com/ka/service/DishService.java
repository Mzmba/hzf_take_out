package com.ka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ka.pojo.Dish;
import com.ka.pojo.DishDto;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);
}
