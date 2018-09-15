package com.wmt.carmanage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.CarInfo;
import com.baomidou.mybatisplus.service.IService;
import com.wmt.carmanage.vo.CarInfoVo;
import com.wmt.carmanage.vo.StoreVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 汽车信息 服务类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
public interface CarInfoService extends IService<CarInfo> {
    /**
     * 根据仓库Id获取汽车类型下拉列表
     * @param storeId
     * @return
     */
    List<CarInfoVo> getCarTypeSelectByStoreId(Integer storeId);

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
    Page<CarInfoVo> getCarInfoVoList(String carCode,String carName,String carModel,String productionStartDate,String productionEndDate,
                                     String storageStartDate,String storageEndDate,Integer manufacturerId,Integer storeInfoId,Integer useStatus,
                                     Integer current,String sort,String asc,Integer pageSize) throws Exception;

    /**
     * 选择汽车列表
     * @param carCode
     * @param carName
     * @param carModel
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    Page<CarInfoVo> getCarChooseList(String carCode,String carName,String carModel,Integer current,String sort,String asc,Integer pageSize)throws Exception;
    /**
     * 库存信息
     * @param storeId
     * @param carName
     * @param carModel
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    Page<StoreVo> getCarInfoByStore(Integer storeId, String carName, String carModel, Integer current, String sort, String asc, Integer pageSize) throws Exception;

    /**
     * 新车入库
     * @param carInfo
     * @return
     */
    boolean saveCarInfoInStore(CarInfo carInfo);

    /**
     * 修改
     * @param carInfo
     * @return
     */
    boolean editCarInfo(CarInfo carInfo);

    /**
     * 移库
     * @param carInfo
     * @return
     */
    boolean editCarInfoStore(CarInfo carInfo);

    /**
     * 根据仓库ID获取仓库车辆情况
     * @param storeId
     * @return
     */
    Map getStorePieByStoreId(Integer storeId);

}
