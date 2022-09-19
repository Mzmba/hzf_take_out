package com.ka.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ka.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2022-05-30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
