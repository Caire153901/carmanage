package com.wmt.carmanage.controller.SystemManage;

import com.wmt.carmanage.service.SysSerialNumberService;
import com.wmt.carmanage.vo.SysSerialNumberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     *  流水号页面跳转
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.GET})
    public String login() {
        return "/system/serial_number";
    }

    /**
     * 流水号列表查询
     * @param authorityName
     * @return
     */
    @GetMapping("/list")
    public List<SysSerialNumberVo> getSerialNumberList(String authorityName){
        return  sysSerialNumberService.getList(authorityName);
    }

    /**
     * 添加
     * @param sysSerialNumberVo
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public boolean saveSerialNumber(@Validated SysSerialNumberVo sysSerialNumberVo) throws Exception{
        return sysSerialNumberService.saveSysSerialNumber(sysSerialNumberVo);
    }

    /**
     * 修改
     * @param sysSerialNumberVo
     * @return
     * @throws Exception
     */
    @PostMapping("/edit")
    public boolean updateSerialNumber(@Validated SysSerialNumberVo sysSerialNumberVo) throws Exception{
        return sysSerialNumberService.editSysSerialNumber(sysSerialNumberVo);
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
