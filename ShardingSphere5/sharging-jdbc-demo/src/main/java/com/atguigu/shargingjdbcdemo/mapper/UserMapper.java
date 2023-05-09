package com.atguigu.shargingjdbcdemo.mapper;

import com.atguigu.shargingjdbcdemo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
