package com.wmt.carmanage.controller.SalesManage;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.constant.EUDataGridResult;
import com.wmt.carmanage.constant.ExcelData;
import com.wmt.carmanage.entity.OrderInfo;
import com.wmt.carmanage.service.OrderInfoService;
import com.wmt.carmanage.service.SysSerialNumberService;
import com.wmt.carmanage.util.DateUtils;
import com.wmt.carmanage.util.ExportExcelUtils;
import com.wmt.carmanage.vo.OrderExcelOut;
import com.wmt.carmanage.vo.OrderInfoVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单管理
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    SysSerialNumberService sysSerialNumberService;

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

    /**
     * 根据config_templet获取流水号
     * @return
     */
    @GetMapping("/order_code")
    public Map getCustomerCode(String config) throws Exception{
        Map map = new HashMap();
        String code= sysSerialNumberService.getSerialNumberByConfigTemplet(config);
        map.put("orderCode",code);
        return map;
    }


    /**
     * 导出订单EXCEL
     *
     * @return
     */
    @RequestMapping("/downLoadExcel")
    public void downLoadExcel(
            HttpServletResponse response,
            @RequestParam(value = "orderCode",required = false) String orderCode,
            @RequestParam(value = "customerName",required = false) String customerName,
            @RequestParam(value = "customerCode",required = false) String customerCode,
            @RequestParam(value = "carModel",required = false) String carModel,
            @RequestParam(value = "carName",required = false) String carName,
            @RequestParam(value = "orderStatus",required = false) Integer orderStatus,
            @RequestParam(value = "saleStartDate",required = false) String saleStartDate,
            @RequestParam(value = "saleEndDate",required = false) String saleEndDate) {
        try {
            //文件名
            String expName = "订单表_" + DateUtils.getDate() + ".xls";
            String titleName ="";
            if(null!=saleStartDate && !saleStartDate.equals("") && null!=saleEndDate && !saleEndDate.equals("")){
                titleName = saleStartDate+" 到 "+saleEndDate;
            }
            List<OrderInfoVo> list = orderInfoService.getOrderVoList(orderCode, customerName, customerCode,carModel,carName,orderStatus,saleStartDate,saleEndDate);
            List<OrderExcelOut> rows  = getRecordExcel(list);
            if(list.size()>0){
                String startDate =DateUtils.formatDate(list.stream().map(OrderInfoVo::getSalesDate).sorted().findFirst().get());
                List<Date> collect = list.stream().map(OrderInfoVo::getSalesDate).collect(Collectors.toList());
                Collections.reverse(collect);
                String endDate  =DateUtils.formatDate(collect.stream().findFirst().get());
                titleName = startDate + " 到 "+ endDate;
            }
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(titleName+" 订单表","订单表"),
                    OrderExcelOut.class, rows);
                String fileName = new String(expName.getBytes("GB2312"), "ISO_8859_1");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition","attachment; filename="+fileName);
                ServletOutputStream out = response.getOutputStream();
                workbook.write(out);
                out.close();
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    /*拼接excel表格内容*/
    public static List<OrderExcelOut> getRecordExcel(List<OrderInfoVo> orderInfoVoList){
        List<OrderExcelOut> excel = new ArrayList<>();
        for(int i=0;i<orderInfoVoList.size();i++){
            OrderExcelOut  out = new OrderExcelOut();
            out.setRowNumber(i+1);
            out.setCarCode(orderInfoVoList.get(i).getCarInfo().getCarCode());
            out.setCarColor(orderInfoVoList.get(i).getCarInfo().getCarColor());
            out.setCarModel(orderInfoVoList.get(i).getCarInfo().getCarModel());
            out.setCarName(orderInfoVoList.get(i).getCarInfo().getCarName());
            out.setClosingCost(orderInfoVoList.get(i).getClosingCost());
            out.setCustomerCode(orderInfoVoList.get(i).getCustomer().getCustomerCode());
            out.setCustomerName(orderInfoVoList.get(i).getCustomer().getCustomerName());
            out.setEngineNumber(orderInfoVoList.get(i).getCarInfo().getEngineNumber());
            out.setOrderCode(orderInfoVoList.get(i).getOrderCode());
            out.setOrderNote(orderInfoVoList.get(i).getOrderNote());
            if(0==orderInfoVoList.get(i).getCustomer().getSex()){
                out.setSex("男");
            }else if(1==orderInfoVoList.get(i).getCustomer().getSex()){
                out.setSex("女");
            }else{
                out.setSex("其他");
            }
            out.setProductionDate(orderInfoVoList.get(i).getCarInfo().getProductionDate());
            out.setSalesDate(orderInfoVoList.get(i).getSalesDate());
            out.setStorageDate(orderInfoVoList.get(i).getCarInfo().getStorageDate());
            out.setTelPhone(orderInfoVoList.get(i).getCustomer().getTelphone());
            String orderSatus = "";
            switch (orderInfoVoList.get(i).getOrderStatus()){
                case 0:
                    orderSatus="未出库";
                    break;
                case 1:
                    orderSatus="已出库";
                    break;
                case 2:
                    orderSatus="在运";
                    break;
                case 3:
                    orderSatus="已送达";
            }
            out.setOrderStatus(orderSatus);
            excel.add(out);
        }
        return excel;
    }

}
