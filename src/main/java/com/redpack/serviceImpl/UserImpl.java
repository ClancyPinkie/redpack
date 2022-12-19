package com.redpack.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpack.entity.User;
import com.redpack.mapper.UserMapper;
import com.redpack.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
