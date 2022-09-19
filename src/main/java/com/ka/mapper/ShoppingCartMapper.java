package com.ka.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ka.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 购物车 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2022-05-30
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}
