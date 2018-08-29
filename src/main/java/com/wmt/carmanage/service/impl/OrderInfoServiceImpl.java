package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.CarInfo;
import com.wmt.carmanage.entity.OrderInfo;
import com.wmt.carmanage.mapper.OrderInfoMapper;
import com.wmt.carmanage.service.CarInfoService;
import com.wmt.carmanage.service.OrderInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.vo.OrderInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@Service
@SuppressWarnings("all")
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    CarInfoService carInfoService;
    /**
     * 订单列表
     * @param orderCode       订单编号
     * @param customerName    客户名
     * @param customerCode    客户编号
     * @param carModel        汽车类型
     * @param carName         汽车名
     * @param current         当前页
     * @param sort            排序条件
     * @param asc             升降序
     * @param pageSize        每页条数
     * @return
     * @throws Exception
     */
    @Override
    public Page<OrderInfoVo> getVisitInfoList(String orderCode, String customerName, String customerCode, String carModel, String carName, Integer current, String sort, Boolean asc, Integer pageSize) throws Exception {
        if(null==sort){
            sort="orderCode";
        }
        if(null==asc){
            asc = false;
        }
        Page page = new Page(current,pageSize,sort,asc);
        Map map = new HashMap();
        map.put("orderCode",orderCode);
        map.put("customerName",customerName);
        map.put("customerCode",customerCode);
        map.put("carModel",carModel);
        map.put("carName",carName);
        List<OrderInfoVo> list = orderInfoMapper.getOrderInfoList(page,map);
        page = page.setRecords(list);
        return page;
    }

    /**
     * 新增
     * @param orderInfo
     * @return
     * @throws Exception
     */
    @Override
    public boolean saveOrderInfo(OrderInfo orderInfo) throws Exception {
        Integer carId = orderInfo.getCarId();
        CarInfo carInfo = carInfoService.selectById(carId);
        carInfo.setUseStatus(3);
        carInfoService.updateById(carInfo);
        return super.insert(orderInfo);
    }

    /**
     * 修改
     * @param orderInfo
     * @return
     * @throws Exception
     */
    @Override
    public boolean editOrderInfo(OrderInfo orderInfo) throws Exception {
        OrderInfo old = super.selectById(orderInfo.getId());
        Integer oldCarId = old.getCarId();
        CarInfo oldCar = carInfoService.selectById(oldCarId);
        oldCar.setUseStatus(0);
        carInfoService.updateById(oldCar);
        Integer newCarId = orderInfo.getCarId();
        CarInfo newCar = carInfoService.selectById(newCarId);
        carInfoService.updateById(newCar);
        return super.updateById(orderInfo);
    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteOrderInfo(Integer id) throws Exception {
        OrderInfo orderInfo = super.selectById(id);
        orderInfo.setUseStatus(2);
        return super.updateById(orderInfo);
    }
}
