package com.ka.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ka.mapper.DishFlavorMapper;
import com.ka.pojo.DishFlavor;
import com.ka.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
