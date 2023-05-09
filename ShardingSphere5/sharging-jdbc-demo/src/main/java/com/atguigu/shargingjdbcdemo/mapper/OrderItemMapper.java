package com.atguigu.shargingjdbcdemo.mapper;

import com.atguigu.shargingjdbcdemo.entity.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
