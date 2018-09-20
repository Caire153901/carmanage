package com.wmt.carmanage.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.OrderInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wmt.carmanage.vo.OrderInfoVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    /**
     * 订单列表查询
     * @param map
     * @return
     */
    List<OrderInfoVo> getOrderInfoList(Page page,Map map);
    /**
     * 订单
     */
    List<OrderInfoVo> getOrderInfoList(Map map);

}
