package com.wmt.carmanage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Customer;
import com.baomidou.mybatisplus.service.IService;
import com.wmt.carmanage.vo.CustomerVo;

import java.util.Map;

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

    /**
     * 新增
     * @param customer
     * @return
     * @throws Exception
     */
    boolean saveCustomer(Customer customer) throws Exception;

    /**
     * 修改
     * @param customer
     * @return
     * @throws Exception
     */
    boolean editCustomer(Customer customer) throws Exception;

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCustomer(Integer id) throws Exception;

    /**
     * 禁用/启用
     * @param id
     * @return
     * @throws Exception
     */
    boolean enableCustomer(Integer id,byte type) throws Exception;

    /**
     * 客户统计
     * @return
     */
    Map getCustomerPie();


}
