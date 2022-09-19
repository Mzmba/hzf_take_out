package com.ka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ka.common.CustomException;
import com.ka.mapper.OrdersMapper;
import com.ka.pojo.*;
import com.ka.service.*;
import com.ka.utils.BaseContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private ShoppingCartService shoppingcartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    @Transactional
    public void submit(Orders orders) {
        //获取当前用户id
        Long currentId = BaseContext.getCurrentId();
        //查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        List<ShoppingCart> list = shoppingcartService.list(queryWrapper);

        if (list==null||list.size()==0){
            throw new CustomException("购物车为空，不能下单");
        }
        //查询用户数据
        User user = userService.getById(currentId);
        //查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if(addressBook==null){
            throw new CustomException("地址为空，不能下单");
        }

        long orderId = IdWorker.getId();//雪花算法生成订单号

        AtomicReference<BigDecimal> amount= new AtomicReference<BigDecimal>(new BigDecimal(0));

        List<OrderDetail> orderDetails;
        orderDetails = list.stream().map(new Function<ShoppingCart, OrderDetail>() {
            @Override
            public OrderDetail apply(ShoppingCart item) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderId);
                orderDetail.setNumber(item.getNumber());
                orderDetail.setDishFlavor(item.getDishFlavor());
                orderDetail.setDishId(item.getDishId());
                orderDetail.setSetmealId(item.getSetmealId());
                orderDetail.setName(item.getName());
                orderDetail.setImage(item.getImage());
                orderDetail.setAmount(item.getAmount());
                amount.updateAndGet(v -> v.add(item.getAmount().multiply(new BigDecimal(item.getNumber()))));
                return orderDetail;
            }
        }).collect(Collectors.toList());

        //向订单表中插入一条数据
        orders.setNumber(String.valueOf(orderId)); //订单号
        orders.setId(orderId);//订单id，主键
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());//下单和结账同一时间因为本项目没有做支付功能
        orders.setStatus(2);
        orders.setAmount(amount.get());//计算总金额
        orders.setUserId(currentId);
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName()==null?"":addressBook.getProvinceName())
                +(addressBook.getCityName()==null?"":addressBook.getCityName())
                +(addressBook.getDistrictName()==null?"":addressBook.getDistrictName())
                +(addressBook.getDetail()==null?"":addressBook.getDetail()));
        this.save(orders);

        //向订单明细表中插入多条数据
        orderDetailService.saveBatch(orderDetails);
        //清空购物车数据
        shoppingcartService.remove(queryWrapper);
    }
}
