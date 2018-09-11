package com.wmt.carmanage.controller.CustomerManage;


import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.constant.EUDataGridResult;
import com.wmt.carmanage.entity.Manufacturer;
import com.wmt.carmanage.service.ManufacturerService;
import com.wmt.carmanage.service.SysSerialNumberService;
import com.wmt.carmanage.vo.CustomerVo;
import com.wmt.carmanage.vo.ManufacturerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.HashMap;
import java.util.Map;

/**
 * 厂商管理
 */
@RestController
@RequestMapping("/manufacturer")
public class ManufacturerController {

    @Autowired
    ManufacturerService manufacturerService;
    @Autowired
    SysSerialNumberService sysSerialNumberService;

    /**
     * 厂商信息列表
     * @param manufacturerCode
     * @param manufacturerName
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public EUDataGridResult getCustomerList(
            @RequestParam(value = "manufacturerCode",required = false) String manufacturerCode,
            @RequestParam(value = "manufacturerName",required = false) String manufacturerName,
            @RequestParam(value = "page",required = false,defaultValue = "1") Integer current,
            @RequestParam(value = "sort",required = false,defaultValue = "gmtModify") String sort,
            @RequestParam(value = "order",required = false) String asc,
            @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "rows",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        Page<ManufacturerVo> page = manufacturerService.getManufacturerList(manufacturerCode, manufacturerName,current, sort, asc, pageSize);
        EUDataGridResult all = new EUDataGridResult();
        all.setRows(page.getRecords());
        all.setTotal(page.getTotal());
        return all;
    }

    /**
     * 添加
     * @param manufacturer
     * @return
     * @throws Exception
     */
    @PostMapping("/saveOrUpdateManufacturer")
    public boolean saveOrUpdateManufacturer(@Validated Manufacturer manufacturer) throws Exception{
        if(null==manufacturer.getId()){
            return manufacturerService.saveManufacturer(manufacturer);
        }else {
            return manufacturerService.editManufacturer(manufacturer);
        }

    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    public boolean deleteManufacturer(@RequestParam Integer id) throws Exception{
        return manufacturerService.deleteManufacturer(id);
    }

    /**
     * 启用/禁用
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @PostMapping("/enable")
    public boolean enableManufacturer(@RequestParam Integer id,@RequestParam byte type) throws Exception{
        return manufacturerService.enableManufacturer(id,type);
    }

    /**
     * 根据config_templet获取流水号
     * @return
     */
    @GetMapping("/manufacturer_code")
    public Map getManufacturerCode(String config) throws Exception{
        Map map = new HashMap();
        String code= sysSerialNumberService.getSerialNumberByConfigTemplet(config);
        map.put("manufacturerCode",code);
        return map;
    }

}
