package com.wmt.carmanage.controller.CustomerManage;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.constant.EUDataGridResult;
import com.wmt.carmanage.entity.Customer;
import com.wmt.carmanage.entity.Provincial;
import com.wmt.carmanage.service.CustomerService;
import com.wmt.carmanage.service.ProvincialService;
import com.wmt.carmanage.service.SysSerialNumberService;
import com.wmt.carmanage.vo.CustomerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户管理
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    ProvincialService provincialService;
    @Autowired
    SysSerialNumberService sysSerialNumberService;

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
    public EUDataGridResult getCustomerList(
                                        @RequestParam(value = "customerName",required = false) String customerName,
                                        @RequestParam(value = "customerCode",required = false) String customerCode,
                                        @RequestParam(value = "provincialId",required = false) Integer provincialId,
                                        @RequestParam(value = "page",required = false,defaultValue = "1") Integer current,
                                        @RequestParam(value = "sort",required = false,defaultValue = "gmtModify") String sort,
                                        @RequestParam(value = "order",required = false) String asc,
                                        @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "rows",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        Page<CustomerVo> page =  customerService.getCustomerList(customerName, customerCode,provincialId,current, sort, asc, pageSize);
        EUDataGridResult all = new EUDataGridResult();
        all.setRows(page.getRecords());
        all.setTotal(page.getTotal());
        return all;
    }

    /**
     *
     * @param customerName
     * @param customerCode
     * @param useStatus
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GetMapping("/choose/list")
    public EUDataGridResult getCustomerChooseList(
            @RequestParam(value = "customerName",required = false) String customerName,
            @RequestParam(value = "customerCode",required = false) String customerCode,
            @RequestParam(value = "useStatus",required = false) Integer useStatus,
            @RequestParam(value = "page",required = false,defaultValue = "1") Integer current,
            @RequestParam(value = "sort",required = false,defaultValue = "gmtModify") String sort,
            @RequestParam(value = "order",required = false) String asc,
            @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "rows",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        Page<CustomerVo> page =  customerService.getCustomerChooseList(customerName, customerCode,useStatus,current, sort, asc, pageSize);
        EUDataGridResult all = new EUDataGridResult();
        all.setRows(page.getRecords());
        all.setTotal(page.getTotal());
        return all;
    }
    /**
     * 添加
     * @param customer
     * @return
     * @throws Exception
     */
    @PostMapping("/saveOrUpdateCustomer")
    public boolean saveOrUpdateCustomer(@Validated Customer customer) throws Exception{
        if(null==customer.getId()){
            return customerService.saveCustomer(customer);
        }else {
            return customerService.editCustomer(customer);
        }
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

    /**
     * 获取省份
     * @return
     */
    @GetMapping("/provincial")
    public List<Provincial> getProvincial(){
        Wrapper<Provincial> wrapper = new EntityWrapper<>();
        return provincialService.selectList(wrapper);
    }

    /**
     * 根据config_templet获取流水号
     * @return
     */
    @GetMapping("/customer_code")
    public Map getCustomerCode(String config) throws Exception{
        Map map = new HashMap();
       String code= sysSerialNumberService.getSerialNumberByConfigTemplet(config);
       map.put("customerCode",code);
        return map;
    }

}
