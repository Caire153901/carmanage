package com.wmt.carmanage.controller.StoreManage;


import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.CarInfo;
import com.wmt.carmanage.service.CarInfoService;
import com.wmt.carmanage.vo.CarInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
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

    /**
     * 汽车信息列表
     * @param carCode             汽车编号
     * @param carName             汽车名
     * @param carModel            汽车型号
     * @param productionStartDate 生产日期查询开始时间
     * @param productionEndDate   生产日期查询结束时间
     * @param storageStartDate    入库日期查询开始时间
     * @param storageEndDate      入库日期查询结束时间
     * @param manufacturerId      厂商ID
     * @param storeInfoId         仓库ID
     * @param useStatus           汽车使用状态
     * @param current             当前页
     * @param sort                排序字段
     * @param asc                 升降序
     * @param pageSize            每页条数
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public Page<CarInfoVo> getCarInfoVoList(
                                       @RequestParam(value = "carCode",required = false) String carCode,
                                       @RequestParam(value = "carName",required = false) String carName,
                                       @RequestParam(value = "carModel",required = false) String carModel,
                                       @RequestParam(value = "productionStartDate",required = false) String productionStartDate,
                                       @RequestParam(value = "productionEndDate",required = false) String productionEndDate,
                                       @RequestParam(value = "storageStartDate",required = false) String storageStartDate,
                                       @RequestParam(value = "storageEndDate",required = false) String storageEndDate,
                                       @RequestParam(value = "manufacturerId",required = false) Integer manufacturerId,
                                       @RequestParam(value = "storeInfoId",required = false) Integer storeInfoId,
                                       @RequestParam(value = "useStatus",required = false) Integer useStatus,
                                       @RequestParam(value = "current",required = false,defaultValue = "1") Integer current,
                                       @RequestParam(value = "sort",required = false,defaultValue = "carCode") String sort,
                                       @RequestParam(value = "asc",required = false) Boolean asc,
                                       @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        return carInfoService.getCarInfoVoList(carCode,carName,carModel,productionStartDate,productionEndDate,storageStartDate,storageEndDate,manufacturerId,storeInfoId,useStatus,current,sort,asc,pageSize);
    }


    /**
     * 新车入库
     * @param carInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public boolean saveCarInfo(@Validated CarInfo carInfo) throws Exception{
        return carInfoService.saveCarInfoInStore(carInfo);
    }
    /**
     * 移库
     * @param carInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/edit")
    public boolean editCarInfo(@Validated CarInfo carInfo) throws Exception{
        return carInfoService.editCarInfoStore(carInfo);
    }


}
