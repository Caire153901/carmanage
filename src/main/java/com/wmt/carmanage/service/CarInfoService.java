package com.wmt.carmanage.service;

import com.wmt.carmanage.entity.CarInfo;
import com.baomidou.mybatisplus.service.IService;
import com.wmt.carmanage.vo.CarInfoVo;

import java.util.List;

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

}
