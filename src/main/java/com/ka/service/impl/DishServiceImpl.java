package com.ka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ka.mapper.DishMapper;
import com.ka.pojo.Dish;
import com.ka.pojo.DishDto;
import com.ka.pojo.DishFlavor;
import com.ka.service.DishFlavorService;
import com.ka.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品基本信息到菜品表dish
        //为什么这里传dishDto可以，因为DishDto是Dish的子类，所以DishDto包含了Dish和一些其他信息，所以可以先存Dishto的一部分信息（Dish)到Dish表中，DishDto扩展的数据可以下一步再存
        this.save(dishDto);
        //拿ID和口味数组，为存DishDto做准备
        Long dishid = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        //把一个菜品的id和菜品口味数组中的每一个元素dishFlavors组合在一起才能形成完整的dishFlavors（用遍历）
//        for (DishFlavor dishFlavors:flavor) {
//            dishFlavors.setDishId(dishId);
//        }
        flavors = flavors.stream().map(item -> {
            item.setDishId(dishid);
            return item;
        }).collect(Collectors.toList());


        //dishFlavorService.saveBatch(dishDto.getFlavors());
        //saveBatch是批量集合的存储,保存菜品口味到菜品数据表dish_flavor，注意一个flavors数组中的dishFlavors类型元素就在数据库中占了一行，所以实际上insert很多次！
        dishFlavorService.saveBatch(flavors);
    }
    @Override
    @Transactional
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息
        Dish dish = this.getById(id);
        DishDto dishDto=new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        //查询菜品口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> list = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(list);
        return dishDto;
    }
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);

        //更新dish_flavor表信息delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        //更新dish_flavor表信息insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

}
