package com.ka.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ka.common.R;
import com.ka.pojo.ShoppingCart;
import com.ka.service.ShoppingCartService;
import com.ka.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 购物车 前端控制器
 * </p>
 *
 * @author cc
 * @since 2022-05-30
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingcartService;


    /**
     * 往购物车内部添加
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("购物车数据：{}", shoppingCart);
        //设置用户id，指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        //查询当前菜品或者套餐是否已经在购物车当中
        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (dishId != null) {
            //添加到购物车的为菜品
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            //添加到购物车的为套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        //SQL:select *from shopping_cart where user_id=? and dish_id/setmeal_id =?
        //  如果可以查出来，说明购物车已经加入了相关菜品
        ShoppingCart cartServiceone = shoppingcartService.getOne(queryWrapper);

        if(cartServiceone!=null) {
            //如果已经存在，则在原来的基础上加一
            Integer number = cartServiceone.getNumber();
            cartServiceone.setNumber(number+1);
            shoppingcartService.updateById(cartServiceone);
        }else {
            //如果不存在，则添加到购物车中，默认为一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingcartService.save(shoppingCart);
            cartServiceone=shoppingCart;
        }
        return R.success(cartServiceone);
    }

    /**
     * @return 购物车列表
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        log.info("查看购物车");
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        queryWrapper.orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingcartService.list(queryWrapper);
        return R.success(list);
    }


    /**
     * 一次性清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        //获取当前购物车用户Id
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);
        shoppingcartService.remove(lambdaQueryWrapper);
        return R.success("清空成功");
    }
    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        Long setmealId = shoppingCart.getSetmealId();
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());

        if (setmealId!=null){
            queryWrapper.eq(ShoppingCart::getSetmealId,setmealId);
        }else {
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
        }
        ShoppingCart cart = shoppingcartService.getOne(queryWrapper);
        Integer number = cart.getNumber();
        if(number==1){
            shoppingcartService.remove(queryWrapper);
        }else {
            cart.setNumber(number-1);
            shoppingcartService.updateById(cart);
        }

        return R.success(cart);
    }

}
