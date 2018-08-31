package com.wmt.carmanage.controller.CustomerManage;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Customer;
import com.wmt.carmanage.service.CustomerService;
import com.wmt.carmanage.vo.CustomerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;

/**
 * 客户管理
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

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
    @GetMapping("/list")
    public Page<CustomerVo> getCustomerList(
                                        @RequestParam(value = "customerName",required = false) String customerName,
                                        @RequestParam(value = "customerCode",required = false) String customerCode,
                                        @RequestParam(value = "current",required = false,defaultValue = "1") Integer current,
                                        @RequestParam(value = "sort",required = false,defaultValue = "gmtModify") String sort,
                                        @RequestParam(value = "asc",required = false) Boolean asc,
                                        @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        return customerService.getCustomerList(customerName, customerCode,current, sort, asc, pageSize);
    }

    /**
     * 添加
     * @param customer
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public boolean saveCustomer(@Validated Customer customer) throws Exception{
        return customerService.saveCustomer(customer);
    }

    /**
     * 修改
     * @param customer
     * @return
     * @throws Exception
     */
    @PostMapping("/edit")
    public boolean updateCustomer(@Validated Customer customer) throws Exception{
        return customerService.editCustomer(customer);
    }


    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    public boolean deleteCustomer(@RequestParam Integer id) throws Exception{
        return customerService.deleteCustomer(id);
    }

    /**
     * 启用/禁用
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @PostMapping("/enable")
    public boolean enableCustomer(@RequestParam Integer id,@RequestParam byte type) throws Exception{
        return customerService.enableCustomer(id,type);
    }

}
