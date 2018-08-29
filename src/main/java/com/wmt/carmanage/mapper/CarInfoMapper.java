package com.wmt.carmanage.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.CarInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wmt.carmanage.vo.CarInfoVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 汽车信息 Mapper 接口
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
public interface CarInfoMapper extends BaseMapper<CarInfo> {

    List<CarInfoVo> getCarInfoVoList(Page page, Map map);

}
