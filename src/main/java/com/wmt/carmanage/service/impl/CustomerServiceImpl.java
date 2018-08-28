package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Customer;
import com.wmt.carmanage.mapper.CustomerMapper;
import com.wmt.carmanage.service.CustomerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.vo.CustomerVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    @Override
    public Page<CustomerVo> getCustomerList(String customerName,String customerCode, Integer current, String sort, Boolean asc, Integer pageSize) throws Exception {
        if(null==sort){
            sort = "gmtModified";
        }
        if(null==asc){
            asc = false;
        }
        Page page = new Page(current, pageSize, sort, asc);
        EntityWrapper<Customer> wrapper = new EntityWrapper<>();
        if(null!=customerName && !customerName.equals("")){
            wrapper.like("customer_name",customerName);
        }
        if(null!=customerCode && !customerCode.equals("")){
            wrapper.like("customer_code",customerCode);
        }
        Page<Customer> customerPage = super.selectPage(page,wrapper);
        if(null!=customerPage.getRecords() && customerPage.getRecords().size()>0){
            List<CustomerVo> customerVoList = new ArrayList<>();
            customerPage.getRecords().stream().forEach(customer -> {
                CustomerVo vo = new CustomerVo();
                BeanUtils.copyProperties(customer,vo);
                customerVoList.add(vo);
            });
            page = page.setRecords(customerVoList);
        }
        return page;
    }
}
