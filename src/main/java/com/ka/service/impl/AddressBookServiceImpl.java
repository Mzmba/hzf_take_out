package com.ka.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ka.mapper.AddressBookMapper;
import com.ka.pojo.AddressBook;
import com.ka.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址管理 服务实现类
 * </p>
 *
 * @author cc
 * @since 2022-05-30
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
