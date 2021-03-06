package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.CarInfo;
import com.wmt.carmanage.entity.Customer;
import com.wmt.carmanage.entity.OrderInfo;
import com.wmt.carmanage.entity.SysSerialNumber;
import com.wmt.carmanage.mapper.OrderInfoMapper;
import com.wmt.carmanage.service.CarInfoService;
import com.wmt.carmanage.service.CustomerService;
import com.wmt.carmanage.service.OrderInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.service.SysSerialNumberService;
import com.wmt.carmanage.util.DateUtils;
import com.wmt.carmanage.vo.OrderInfoVo;
import com.wmt.carmanage.vo.OrderLineVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
    @Autowired
    CustomerService customerService;
    @Autowired
    SysSerialNumberService sysSerialNumberService;
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
    public Page<OrderInfoVo> getOrderList(String orderCode, String customerName, String customerCode, String carName, String carModel,
                                              Integer orderStatus,String saleStartDate,String saleEndDate,
                                              Integer current, String sort, String asc, Integer pageSize) throws Exception {
        boolean orderSort = false;
        if(null==sort){
            sort="a.order_code";
        }
        if(null!=asc && asc.equals("asc")){
            orderSort = true;
        }
        Page page = new Page(current,pageSize,sort,orderSort);
        Map map = new HashMap();
        map.put("orderCode",orderCode);
        map.put("customerName",customerName);
        map.put("customerCode",customerCode);
        map.put("carModel",carModel);
        map.put("carName",carName);
        if(null!=orderStatus && orderStatus!=5){
            map.put("orderStatus",orderStatus);
        }
        map.put("saleStartDate",saleStartDate);
        map.put("saleEndDate",saleEndDate);
        List<OrderInfoVo> list = orderInfoMapper.getOrderInfoList(page,map);
        page = page.setRecords(list);
        return page;
    }

    @Override
    public List<OrderInfoVo> getOrderVoList(String orderCode, String customerName, String customerCode, String carModel, String carName, Integer orderStatus, String saleStartDate, String saleEndDate) throws Exception {
        Map map = new HashMap();
        map.put("orderCode",orderCode);
        map.put("customerName",customerName);
        map.put("customerCode",customerCode);
        map.put("carModel",carModel);
        map.put("carName",carName);
        if(null!=orderStatus && orderStatus!=5){
            map.put("orderStatus",orderStatus);
        }
        map.put("saleStartDate",saleStartDate);
        map.put("saleEndDate",saleEndDate);
        List<OrderInfoVo> list = orderInfoMapper.getOrderInfoList(map);
        return list;
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
        orderInfo.setSalesDate(DateUtils.parseDate(orderInfo.getSalesDates()));
        Wrapper<SysSerialNumber> sysSerialNumberWrapper = new EntityWrapper<>();
        sysSerialNumberWrapper.eq("config_templet","DD");
        SysSerialNumber sysSerialNumber = sysSerialNumberService.selectOne(sysSerialNumberWrapper);
        sysSerialNumber.setGmtModified(new Date());
        sysSerialNumberService.updateById(sysSerialNumber);
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
        oldCar.setUseStatus(3);
        carInfoService.updateById(oldCar);
        Integer newCarId = orderInfo.getCarId();
        CarInfo newCar = carInfoService.selectById(newCarId);
        carInfoService.updateById(newCar);
        orderInfo.setSalesDate(DateUtils.parseDate(orderInfo.getSalesDates()));
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

    /**
     * 根据年份获取订单销售情况
     * @param year
     * @return
     */
    @Override
    public Map getOrderLineByYear(Integer year) {
        Map map = new HashMap();
        //图例
        Set<String> legendData = new LinkedHashSet<>();
        List<Map<String,Object>> data = new ArrayList<>();
        EntityWrapper<OrderInfo> wrapper = new EntityWrapper<>();
        wrapper.where("YEAR(sales_date)={0}",year);
        wrapper.orderBy("sales_date",true);
        List<OrderInfo> list = super.selectList(wrapper);
        List<OrderLineVo> orderInfoVoList = new ArrayList<>();
        //X轴
        List<String> xAxisData = list.stream().map(o -> DateUtils.formatDate(o.getSalesDate())).distinct().collect(Collectors.toList());
        list.stream().forEach(orderInfo -> {
            OrderLineVo vo = new OrderLineVo();
            BeanUtils.copyProperties(orderInfo,vo);
            CarInfo carInfo = carInfoService.selectById(orderInfo.getCarId());
            vo.setCarInfoName(carInfo.getCarName());
            orderInfoVoList.add(vo);
            legendData.add(carInfo.getCarName());
        });
        //先按照汽车名分组，再按照销售日期分组并统计个数
        Map<String, Map<Date, Long>> collect = orderInfoVoList.stream()
                .collect(Collectors.groupingBy(OrderLineVo::getCarInfoName, Collectors.groupingBy(OrderLineVo::getSalesDate, Collectors.counting())));

        List<Long> yList = new ArrayList<>();
        for (String x:xAxisData){
            yList.add(Long.parseLong("0"));
        }
        collect.forEach((k, v) ->{
            Map kmap = new HashMap();
            List<Long> y = new ArrayList<>();
            y.addAll(yList);
            List<Integer> index = new ArrayList<>();
            List<Long> value = new ArrayList<>();
            v.forEach((date,count)->{
                for(int i=0;i<xAxisData.size();i++){
                    if(xAxisData.get(i).equals(DateUtils.formatDate(date))){
                        index.add(i);
                        value.add(count);
                    }
                }
            });
            for(int j=0;j<index.size();j++){
                int h = index.get(j);
                y.set(h,value.get(j));
            }
            kmap.put("data", y);
            kmap.put("type","line");
            data.add(kmap);
        });
        map.put("xAxisData",xAxisData);
        map.put("legendData",legendData);
        map.put("seriesData",data);
        map.put("list",list);
        return map;
    }
}
