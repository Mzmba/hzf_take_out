package com.ka.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ka.pojo.Category;

public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
