package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.CarInfo;
import com.wmt.carmanage.entity.StoreInfo;
import com.wmt.carmanage.exception.BaseException;
import com.wmt.carmanage.mapper.CarInfoMapper;
import com.wmt.carmanage.service.CarInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.service.StoreInfoService;
import com.wmt.carmanage.vo.CarInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * <p>
 * 汽车信息 服务实现类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@Service
@SuppressWarnings("all")
public class CarInfoServiceImpl extends ServiceImpl<CarInfoMapper, CarInfo> implements CarInfoService {

    @Autowired
    CarInfoMapper carInfoMapper;
    @Autowired
    StoreInfoService storeInfoService;
    /**
     * 根据仓库ID获取汽车类型下拉列表
     * @param storeId
     * @return
     */
    @Override
    public List<CarInfoVo> getCarTypeSelectByStoreId(Integer storeId) {
        List<CarInfoVo> list = new ArrayList<>();
        Wrapper<CarInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("store_id",storeId);
        wrapper.eq("use_status",0);
        List<CarInfo> carInfoList = super.selectList(wrapper);
        carInfoList.stream().forEach(carInfo -> {
            CarInfoVo vo = new CarInfoVo();
            BeanUtils.copyProperties(carInfo,vo);
            list.add(vo);
        });
        return list;
    }

    /**
     * 汽车列表
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
    @Override
    public Page<CarInfoVo> getCarInfoVoList(String carCode, String carName, String carModel, String productionStartDate, String productionEndDate, String storageStartDate, String storageEndDate, Integer manufacturerId, Integer storeInfoId, Integer useStatus, Integer current, String sort, Boolean asc, Integer pageSize) throws Exception {
        if(null==sort){
            sort = "carCode";
        }
        if(null==asc){
            asc = false;
        }
        Page page = new Page(current,pageSize,sort,asc);
        Map map = new HashMap();
        map.put("carCode",carCode);
        map.put("carName",carName);
        map.put("carModel",carModel);
        map.put("productionStartDate",productionStartDate);
        map.put("productionEndDate",productionEndDate);
        map.put("storageStartDate",storageStartDate);
        map.put("storageEndDate",storageEndDate);
        map.put("manufacturerId",manufacturerId);
        map.put("storeInfoId",storeInfoId);
        map.put("useStatus",useStatus);
        List<CarInfoVo> list = carInfoMapper.getCarInfoVoList(page,map);
        page = page.setRecords(list);
        return page;
    }

    /**
     * 新车入库
     * @param carInfo
     * @return
     */
    @Override
    public boolean saveCarInfoInStore(CarInfo carInfo) {
        EntityWrapper<CarInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("engine_number",carInfo.getEngineNumber());
        wrapper.or("car_code",carInfo.getCarCode());
        List<CarInfo> list = super.selectList(wrapper);
        if(list.size()>0){
            throw new BaseException("发动机编号【"+carInfo.getEngineNumber()+"】或汽车编号【"+carInfo.getCarCode()+"】已存在");
        }else {
            StoreInfo storeInfo = storeInfoService.selectById(carInfo.getStoreId());
            Integer capacity = storeInfo.getCapacity();
            storeInfo.setCapacity(capacity + 1);
            storeInfo.setMarginCapacity(storeInfo.getMaxCapacity()-capacity-1);
            storeInfoService.updateById(storeInfo);
            return super.insert(carInfo);
        }
    }

    /**
     * 移库
     * @param carInfo
     * @return
     */
    @Override
    public boolean editCarInfoStore(CarInfo carInfo) {
        CarInfo old = super.selectById(carInfo.getId());
        StoreInfo oldStore = storeInfoService.selectById(old.getStoreId());
        Integer oldCapacity = oldStore.getCapacity();
        oldStore.setCapacity(oldCapacity-1);
        oldStore.setMarginCapacity(oldStore.getMarginCapacity()+1);
        storeInfoService.updateById(oldStore);
        StoreInfo storeInfo = storeInfoService.selectById(carInfo.getStoreId());
        Integer capacity = storeInfo.getCapacity();
        storeInfo.setCapacity(capacity + 1);
        storeInfo.setMarginCapacity(storeInfo.getMarginCapacity()-1);
        storeInfoService.updateById(storeInfo);
        return super.updateById(carInfo);
    }

    /**
     * 根据仓库ID获取车辆统计情况
     * @param storeId
     * @return
     */
    @Override
    public Map getStorePieByStoreId(Integer storeId) {
        Map map = new HashMap();
        Set<String> legendData = new LinkedHashSet<>();
        List<Map<String,Object>> data = new ArrayList<>();
        Map<String,Object> valueMap = new HashMap<>();
        EntityWrapper<CarInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("store_id",storeId);
        List<CarInfo> list = super.selectList(wrapper);
        Map<String, Long> collect = list.stream().collect(Collectors.groupingBy(CarInfo::getCarName, Collectors.counting()));
        collect.forEach((key,value)->{
            legendData.add(key);
            valueMap.put("name",key);
            valueMap.put("value",value);
            data.add(valueMap);
        });
        map.put("legendData",legendData);
        map.put("seriesData",data);
        return map;
    }
}
