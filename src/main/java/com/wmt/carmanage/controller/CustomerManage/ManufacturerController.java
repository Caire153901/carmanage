package com.wmt.carmanage.controller.CustomerManage;


import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Manufacturer;
import com.wmt.carmanage.service.ManufacturerService;
import com.wmt.carmanage.vo.CustomerVo;
import com.wmt.carmanage.vo.ManufacturerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;

/**
 * 厂商管理
 */
@RestController
@RequestMapping("/manufacturer")
public class ManufacturerController {

    @Autowired
    ManufacturerService manufacturerService;

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
    public Page<ManufacturerVo> getCustomerList(
            @RequestParam(value = "manufacturerCode",required = false) String manufacturerCode,
            @RequestParam(value = "manufacturerName",required = false) String manufacturerName,
            @RequestParam(value = "current",required = false,defaultValue = "1") Integer current,
            @RequestParam(value = "sort",required = false,defaultValue = "gmtModify") String sort,
            @RequestParam(value = "asc",required = false) Boolean asc,
            @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        return manufacturerService.getManufacturerList(manufacturerCode, manufacturerName,current, sort, asc, pageSize);
    }


    /**
     * 添加
     * @param manufacturer
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public boolean saveManufacturer(@Validated Manufacturer manufacturer) throws Exception{
        return manufacturerService.saveManufacturer(manufacturer);
    }

    /**
     * 修改
     * @param manufacturer
     * @return
     * @throws Exception
     */
    @PostMapping("/edit")
    public boolean updateManufacturer(@Validated Manufacturer manufacturer) throws Exception{
        return manufacturerService.editManufacturer(manufacturer);
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
}
