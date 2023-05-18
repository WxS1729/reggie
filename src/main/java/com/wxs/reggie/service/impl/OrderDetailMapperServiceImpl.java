package com.wxs.reggie.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxs.reggie.entity.OrderDetail;
import com.wxs.reggie.mapper.OrderDetailMapper;
import com.wxs.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailMapperServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
