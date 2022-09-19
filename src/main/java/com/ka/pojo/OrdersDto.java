package com.ka.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : 837312868@qq.com
 * @version : 1.0
 * @Project : reggie
 * @Package : com.ka.pojo
 * @createTime : 2022/9/17 20:41 星期六
 * @Description :
 */
@Data
public class OrdersDto extends Orders{
    List<OrderDetail> orderDetails=new ArrayList<>();
    private Integer sumNum;
}
