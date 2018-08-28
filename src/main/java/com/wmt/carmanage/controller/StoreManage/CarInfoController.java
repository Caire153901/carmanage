package com.wmt.carmanage.controller.StoreManage;


import com.wmt.carmanage.service.CarInfoService;
import com.wmt.carmanage.vo.CarInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 新车入库模块
 */
@RestController
@RequestMapping("/car_info")
public class CarInfoController {

    @Autowired
    CarInfoService carInfoService;

    /**
     * 汽车类型下拉列表
     * @param storeId
     * @return
     */
    @GetMapping("car_type_select")
    public List<CarInfoVo> getCarTypeSelectByStoreId(Integer storeId){
        return carInfoService.getCarTypeSelectByStoreId(storeId);
    }
}
