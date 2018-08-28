package com.wmt.carmanage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Customer;
import com.baomidou.mybatisplus.service.IService;
import com.wmt.carmanage.vo.CustomerVo;

/**
 * <p>
 * 客户信息表 服务类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
public interface CustomerService extends IService<Customer> {
    /**
     * 客户信息列表
     * @param customerName
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    Page<CustomerVo> getCustomerList(String customerName,String customerCode, Integer current, String sort,Boolean asc,Integer pageSize) throws Exception;

}
