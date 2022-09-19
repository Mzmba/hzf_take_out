package com.ka.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ka.mapper.EmployeeMapper;
import com.ka.pojo.Employee;
import com.ka.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
