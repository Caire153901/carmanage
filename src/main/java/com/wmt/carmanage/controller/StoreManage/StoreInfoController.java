package com.wmt.carmanage.controller.StoreManage;

import com.wmt.carmanage.service.StoreInfoService;
import com.wmt.carmanage.vo.StoreInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 仓库信息管理
 */
@RestController
@RequestMapping("/store")
public class StoreInfoController {
    @Autowired
    StoreInfoService storeInfoService;

    /**
     * 获取仓库下拉列表
     * @return
     */
    @GetMapping("/store_select")
    public List<StoreInfoVo> getStoreSelect(){
        return storeInfoService.getSelect();
    }
}
