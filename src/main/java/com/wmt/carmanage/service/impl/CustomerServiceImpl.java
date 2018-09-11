package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Customer;
import com.wmt.carmanage.entity.Provincial;
import com.wmt.carmanage.exception.BaseException;
import com.wmt.carmanage.mapper.CustomerMapper;
import com.wmt.carmanage.service.CustomerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.service.ProvincialService;
import com.wmt.carmanage.vo.CustomerVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    ProvincialService provincialService;
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
    public Page<CustomerVo> getCustomerList(String customerName,String customerCode, Integer provincialId,Integer current, String sort, String asc, Integer pageSize) throws Exception {
        boolean orderSort = false;
        if(null==sort){
            sort = "gmtModify";
        }
        if(null!=asc && asc.equals("asc")){
            orderSort = true;
        }
        Page page = new Page(current, pageSize, sort, orderSort);
        EntityWrapper<Customer> wrapper = new EntityWrapper<>();
        if(null!=customerName && !customerName.equals("")){
            wrapper.like("customer_name",customerName);
        }
        if(null!=customerCode && !customerCode.equals("")){
            wrapper.like("customer_code",customerCode);
        }
        if(null!=provincialId && !provincialId.equals("")){
            wrapper.eq("provincial_id",provincialId);
        }
        Page<Customer> customerPage = super.selectPage(page,wrapper);
        if(null!=customerPage.getRecords() && customerPage.getRecords().size()>0){
            List<CustomerVo> customerVoList = new ArrayList<>();
            customerPage.getRecords().stream().forEach(customer -> {
                CustomerVo vos = new CustomerVo();
                BeanUtils.copyProperties(customer,vos);
                Provincial provincial = provincialService.selectById(customer.getProvincialId());
                vos.setProvincialName(provincial.getName());
                customerVoList.add(vos);
            });
            page = page.setRecords(customerVoList);
        }
        return page;
    }

    /**
     * 新增
     * @param customer
     * @return
     * @throws Exception
     */
    @Override
    public boolean saveCustomer(Customer customer) throws Exception {
        EntityWrapper<Customer> wrapper = new EntityWrapper<>();
        wrapper.eq("customer_name",customer.getCustomerName());
        wrapper.eq("customer_code",customer.getCustomerCode());
        wrapper.notIn("use_status","0,1");
        List<Customer> list = super.selectList(wrapper);
        if(list.size()>0){
            throw new BaseException("客户编号【"+customer.getCustomerCode()+"】,或客户名【"+customer.getCustomerName()+"】已存在");
        }else {
            return super.insert(customer);
        }
    }

    /**
     * 修改
     * @param customer
     * @return
     * @throws Exception
     */
    @Override
    public boolean editCustomer(Customer customer) throws Exception {
        Customer old = super.selectById(customer.getId());
        if(!old.getCustomerName().equals(customer.getCustomerName())){
            EntityWrapper<Customer> wrapper = new EntityWrapper<>();
            wrapper.eq("customer_name",customer.getCustomerName());
            wrapper.notIn("use_status","0,1");
            List<Customer> list = super.selectList(wrapper);
            if(list.size()>0){
                throw new BaseException("客户编号【"+customer.getCustomerCode()+"】已存在");
            }
        }
        return super.updateById(customer);
    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteCustomer(Integer id) throws Exception {
        Customer customer = super.selectById(id);
        customer.setUseStatus(2);
        return super.updateById(customer);
    }

    /**
     * 禁用/启用
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public boolean enableCustomer(Integer id, byte type) throws Exception {
        Customer old = super.selectById(id);
        if(type==0){//启用
            old.setUseStatus(0);
        }else if(type==1){//禁用
            old.setUseStatus(1);
        }
        return super.updateById(old);
    }

    /**
     * 按照省会分布进行客户统计
     * @return
     */
    @Override
    public Map getCustomerPie() {
        Map map = new HashMap();
        Set<String> legendData = new LinkedHashSet<>();
        List<Map<String,Object>> data = new ArrayList<>();
        EntityWrapper<Customer> wrapper = new EntityWrapper<>();
        List<Customer> list = super.selectList(wrapper);
        List<CustomerVo> voList = new ArrayList<>();
        list.stream().forEach(customer -> {
           CustomerVo vo = new CustomerVo();
           BeanUtils.copyProperties(customer,vo);
            Provincial provincial = provincialService.selectById(customer.getProvincialId());
            vo.setProvincialName(provincial.getName());
            voList.add(vo);
        });
        Map<String, Long> collect = voList.stream().collect(Collectors.groupingBy(CustomerVo::getProvincialName, Collectors.counting()));
        collect.forEach((key,value)->{
            Map<String,Object> valueMap = new HashMap<>();
            legendData.add(key);
            valueMap.put("name",key);
            valueMap.put("value",value);
            data.add(valueMap);
        });
        map.put("legendData",legendData);
        map.put("seriesData",data);
        return map;
    }
}
