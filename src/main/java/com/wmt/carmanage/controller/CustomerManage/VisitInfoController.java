package com.wmt.carmanage.controller.CustomerManage;


import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.service.VisitInfoService;
import com.wmt.carmanage.vo.VisitInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
