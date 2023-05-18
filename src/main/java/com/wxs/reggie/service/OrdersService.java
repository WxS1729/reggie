package com.wxs.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxs.reggie.entity.Orders;

public interface OrdersService extends IService<Orders> {

    public void submit(Orders orders);
}
