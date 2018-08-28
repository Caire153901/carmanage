package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wmt.carmanage.entity.CarInfo;
import com.wmt.carmanage.mapper.CarInfoMapper;
import com.wmt.carmanage.service.CarInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.vo.CarInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 汽车信息 服务实现类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@Service
public class CarInfoServiceImpl extends ServiceImpl<CarInfoMapper, CarInfo> implements CarInfoService {
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
}
