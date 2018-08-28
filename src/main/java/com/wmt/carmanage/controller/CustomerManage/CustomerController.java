package com.wmt.carmanage.controller.CustomerManage;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Customer;
import com.wmt.carmanage.service.CustomerService;
import com.wmt.carmanage.vo.CustomerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                                        @RequestParam(value = "sort",required = false,defaultValue = "gmtModified") String sort,
                                        @RequestParam(value = "asc",required = false) Boolean asc,
                                        @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        return customerService.getCustomerList(customerName, customerCode,current, sort, asc, pageSize);
    }

}
