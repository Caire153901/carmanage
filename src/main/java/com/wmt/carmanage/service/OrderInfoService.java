package com.wmt.carmanage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.OrderInfo;
import com.baomidou.mybatisplus.service.IService;
import com.wmt.carmanage.vo.OrderInfoVo;

import java.util.Map;

/**
 * <p>
 * 订单信息表 服务类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
public interface OrderInfoService extends IService<OrderInfo> {


    /**
     * 订单列表
     * @param orderCode       订单编号
     * @param customerName    客户名
     * @param customerCode    客户编号
     * @param carModel        汽车类型
     * @param current         当前页
     * @param sort            排序条件
     * @param asc             升降序
     * @param pageSize        每页条数
     * @return
     * @throws Exception
     */
    Page<OrderInfoVo> getVisitInfoList(String orderCode, String customerName, String customerCode, String carModel,String carName,Integer current, String sort, Boolean asc, Integer pageSize) throws Exception;


    /**
     * 新增
     * @param orderInfo
     * @return
     * @throws Exception
     */
    boolean saveOrderInfo(OrderInfo orderInfo) throws Exception;

    /**
     * 修改
     * @param orderInfo
     * @return
     * @throws Exception
     */
    boolean editOrderInfo(OrderInfo orderInfo) throws Exception;

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteOrderInfo(Integer id) throws Exception;

    /**
     * 根据年份获取车辆销售趋势图
     * @param year
     * @return
     */
    Map getOrderLineByYear(Integer year);

}
