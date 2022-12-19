package com.redpack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redpack.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
