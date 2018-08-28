package com.wmt.carmanage.service.impl;

import com.wmt.carmanage.entity.OrderInfo;
import com.wmt.carmanage.mapper.OrderInfoMapper;
import com.wmt.carmanage.service.OrderInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

}
