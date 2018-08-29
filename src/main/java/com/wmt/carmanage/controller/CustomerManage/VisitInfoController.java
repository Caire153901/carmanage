package com.wmt.carmanage.controller.CustomerManage;


import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.VisitInfo;
import com.wmt.carmanage.service.VisitInfoService;
import com.wmt.carmanage.vo.VisitInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;

/**
 * 回访信息管理
 */
@RestController
@RequestMapping("/visit")
public class VisitInfoController {


    @Autowired
    VisitInfoService visitInfoService;

    /**
     * 回访信息列表
     * @param customerName
     * @param orderCode
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public Page<VisitInfoVo> getCustomerList(
            @RequestParam(value = "customerName",required = false) String customerName,
            @RequestParam(value = "orderCode",required = false) String orderCode,
            @RequestParam(value = "current",required = false,defaultValue = "1") Integer current,
            @RequestParam(value = "sort",required = false,defaultValue = "visitDate") String sort,
            @RequestParam(value = "asc",required = false) Boolean asc,
            @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        return visitInfoService.getVisitInfoList(customerName, orderCode,current, sort, asc, pageSize);
    }


    /**
     * 添加
     * @param visitInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public boolean saveVisitInfo(@Validated VisitInfo visitInfo) throws Exception{
        return visitInfoService.saveVisitInfo(visitInfo);
    }

    /**
     * 修改
     * @param visitInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/edit")
    public boolean updateVisitInfo(@Validated VisitInfo visitInfo) throws Exception{
        return visitInfoService.editVisitInfo(visitInfo);
    }


    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    public boolean deleteVisitInfo(@RequestParam Integer id) throws Exception{
        return visitInfoService.deleteVisitInfo(id);
    }

}
