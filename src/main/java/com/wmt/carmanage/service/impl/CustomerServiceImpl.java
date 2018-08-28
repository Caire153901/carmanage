package com.wmt.carmanage.service.impl;

import com.wmt.carmanage.entity.Customer;
import com.wmt.carmanage.mapper.CustomerMapper;
import com.wmt.carmanage.service.CustomerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户信息表 服务实现类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}
