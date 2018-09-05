package com.wmt.carmanage.controller.SystemManage;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.constant.EUDataGridResult;
import com.wmt.carmanage.entity.SysSerialNumber;
import com.wmt.carmanage.service.SysSerialNumberService;
import com.wmt.carmanage.vo.SysSerialNumberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * 流水号控制器
 */
@RestController
@RequestMapping("/serial_number")
public class SysSerialNumberController {


    @Autowired
    SysSerialNumberService sysSerialNumberService;
    
    /**
     * 流水号列表
     * @param authorityName
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public EUDataGridResult getSerialNumberList(
                                    @RequestParam(value = "authorityName",required = false) String authorityName,
                                    @RequestParam(value = "page",required = false,defaultValue = "1") Integer current,
                                    @RequestParam(value = "sort",required = false,defaultValue = "gmtModified") String sort,
                                    @RequestParam(value = "order",required = false) String asc,
                                    @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "rows",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        Page<SysSerialNumberVo> page = sysSerialNumberService.getSerialNumberList(authorityName, current, sort, asc, pageSize);
        EUDataGridResult all = new EUDataGridResult();
        all.setRows(page.getRecords());
        all.setTotal(page.getTotal());
        return all;
    }

    /**
     * 添加或修改
     * @param sysSerialNumber
     * @return
     * @throws Exception
     */
    @PostMapping("/saveOrUpdateSerialNumber")
    public boolean addOrUpdateSerialNumber(@Validated SysSerialNumber sysSerialNumber) throws Exception{
        if(null!=sysSerialNumber.getId() && !sysSerialNumber.getId().equals("")){
            return sysSerialNumberService.editSysSerialNumber(sysSerialNumber);
        }else {
            sysSerialNumber.setCurrutSerial(0);
            return sysSerialNumberService.saveSysSerialNumber(sysSerialNumber);
        }
    }


    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    public boolean deleteSerialNumber(@RequestParam Integer id) throws Exception{
        return sysSerialNumberService.deleteSysSerialNumber(id);
    }

    /**
     * 启用/禁用
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @PostMapping("/enable")
    public boolean enableSerialNumber(@RequestParam Integer id,@RequestParam byte type) throws Exception{
        return sysSerialNumberService.enableSysSerialNumber(id,type);
    }
    

}
