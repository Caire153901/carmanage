package com.wmt.carmanage.controller.SalesManage;


import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.constant.EUDataGridResult;
import com.wmt.carmanage.entity.CarInfo;
import com.wmt.carmanage.service.CarInfoService;
import com.wmt.carmanage.service.SysSerialNumberService;
import com.wmt.carmanage.util.FileUtils;
import com.wmt.carmanage.vo.CarInfoVo;
import com.wmt.carmanage.vo.StoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 新车入库模块
 */
@RestController
@RequestMapping("/car")
public class CarInfoController {

    @Autowired
    CarInfoService carInfoService;
    @Autowired
    SysSerialNumberService sysSerialNumberService;

    /**
     * 汽车类型下拉列表
     * @param storeId
     * @return
     */
    @GetMapping("/car_type_select")
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
    public EUDataGridResult getCarInfoVoList(
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
                                       @RequestParam(value = "page",required = false,defaultValue = "1") Integer current,
                                       @RequestParam(value = "sort",required = false,defaultValue = "a.car_code") String sort,
                                       @RequestParam(value = "order",required = false) String asc,
                                       @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "rows",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        Page<CarInfoVo> page = carInfoService.getCarInfoVoList(carCode,carName,carModel,productionStartDate,productionEndDate,storageStartDate,storageEndDate,manufacturerId,storeInfoId,useStatus,current,sort,asc,pageSize);
        EUDataGridResult all = new EUDataGridResult();
        all.setRows(page.getRecords());
        all.setTotal(page.getTotal());
        return all;
    }

    /**
     * 新车入库
     * @param carInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/saveOrUpdateCar")
    public boolean saveOrUpdateCar(@Validated CarInfo carInfo) throws Exception{
        if(null==carInfo.getId()){
            return carInfoService.saveCarInfoInStore(carInfo);
        }else {
            return carInfoService.editCarInfo(carInfo);
        }
    }


    /**
     * 移库
     * @param carInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/move_store")
    public boolean moveStore(@Validated CarInfo carInfo) throws Exception{
        return carInfoService.editCarInfoStore(carInfo);
    }


    /**
     * 根据config_templet获取流水号
     * @return
     */
    @GetMapping("/car_code")
    public Map getCarCode(String config) throws Exception{
        Map map = new HashMap();
        String code= sysSerialNumberService.getSerialNumberByConfigTemplet(config);
        map.put("carCode",code);
        return map;
    }

    /**
     * 库存信息
     * @param carName
     * @param carModel
     * @param storeId
     * @param useStatus
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GetMapping("/store/list")
    public EUDataGridResult getCarListByStoreId(
            @RequestParam(value = "carName",required = false) String carName,
            @RequestParam(value = "carModel",required = false) String carModel,
            @RequestParam(value = "storeId",required = false) Integer storeId,
            @RequestParam(value = "useStatus",required = false) Integer useStatus,
            @RequestParam(value = "page",required = false,defaultValue = "1") Integer current,
            @RequestParam(value = "sort",required = false,defaultValue = "a.car_code") String sort,
            @RequestParam(value = "order",required = false) String asc,
            @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "rows",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        Page<StoreVo> page = carInfoService.getCarInfoByStore(storeId, carName, carModel, current, sort, asc, pageSize);
        EUDataGridResult all = new EUDataGridResult();
        all.setRows(page.getRecords());
        all.setTotal(page.getTotal());
        return all;
    }


    @GetMapping("/choose/list")
    public EUDataGridResult getCarChooseVoList(
            @RequestParam(value = "carCode",required = false) String carCode,
            @RequestParam(value = "carName",required = false) String carName,
            @RequestParam(value = "carModel",required = false) String carModel,
            @RequestParam(value = "page",required = false,defaultValue = "1") Integer current,
            @RequestParam(value = "sort",required = false,defaultValue = "a.car_code") String sort,
            @RequestParam(value = "order",required = false) String asc,
            @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "rows",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        Page<CarInfoVo> page = carInfoService.getCarChooseList(carCode,carName,carModel,current,sort,asc,pageSize);
        EUDataGridResult all = new EUDataGridResult();
        all.setRows(page.getRecords());
        all.setTotal(page.getTotal());
        return all;
    }
}
