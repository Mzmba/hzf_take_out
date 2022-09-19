package com.ka.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ka.mapper.UserMapper;
import com.ka.pojo.User;
import com.ka.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author cc
 * @since 2022-05-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
