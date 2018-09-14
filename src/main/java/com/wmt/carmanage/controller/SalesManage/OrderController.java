package com.wmt.carmanage.controller.SalesManage;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.constant.EUDataGridResult;
import com.wmt.carmanage.entity.OrderInfo;
import com.wmt.carmanage.service.OrderInfoService;
import com.wmt.carmanage.vo.OrderInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;

/**
 * 订单管理
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 订单列表
     * @param orderCode      订单编号
     * @param customerName   客户名
     * @param customerCode   客户编号
     * @param carModel       车辆类型
     * @param current        当前页
     * @param sort           排序条件
     * @param asc            升降序
     * @param pageSize       每页条数
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public EUDataGridResult getOrderList(
            @RequestParam(value = "orderCode",required = false) String orderCode,
            @RequestParam(value = "customerName",required = false) String customerName,
            @RequestParam(value = "customerCode",required = false) String customerCode,
            @RequestParam(value = "carModel",required = false) String carModel,
            @RequestParam(value = "carName",required = false) String carName,
            @RequestParam(value = "orderStatus",required = false) Integer orderStatus,
            @RequestParam(value = "saleStartDate",required = false) String saleStartDate,
            @RequestParam(value = "saleEndDate",required = false) String saleEndDate,
            @RequestParam(value = "page",required = false,defaultValue = "1") Integer current,
            @RequestParam(value = "sort",required = false,defaultValue = "a.order_code") String sort,
            @RequestParam(value = "order",required = false) String asc,
            @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "rows",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{

        Page<OrderInfoVo> page = orderInfoService.getOrderList(orderCode, customerName, customerCode, carName,carModel,orderStatus,saleStartDate,saleEndDate,current, sort, asc, pageSize);
        EUDataGridResult all = new EUDataGridResult();
        all.setRows(page.getRecords());
        all.setTotal(page.getTotal());
        return all;
    }



    /**
     * 添加
     * @param orderInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/saveOrUpdateOrderInfo")
    public boolean saveOrUpdateOrderInfo(@Validated OrderInfo orderInfo) throws Exception{
        if(null==orderInfo.getId()){
            return orderInfoService.saveOrderInfo(orderInfo);
        }else {
            return orderInfoService.editOrderInfo(orderInfo);
        }
    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    public boolean deleteOrderInfo(@RequestParam Integer id) throws Exception{
        return orderInfoService.deleteOrderInfo(id);
    }
}
