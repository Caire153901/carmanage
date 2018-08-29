package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Customer;
import com.wmt.carmanage.entity.OrderInfo;
import com.wmt.carmanage.entity.VisitInfo;
import com.wmt.carmanage.mapper.VisitInfoMapper;
import com.wmt.carmanage.service.CustomerService;
import com.wmt.carmanage.service.OrderInfoService;
import com.wmt.carmanage.service.VisitInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.vo.VisitInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 回访信息 服务实现类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@Service
public class VisitInfoServiceImpl extends ServiceImpl<VisitInfoMapper, VisitInfo> implements VisitInfoService {

    @Autowired
    CustomerService customerService;
    @Autowired
    OrderInfoService orderInfoService ;
    /**
     * 回访信息列表
     * @param customerName
     * @param orderCode
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public Page<VisitInfoVo> getVisitInfoList(String customerName, String orderCode, Integer current, String sort, Boolean asc, Integer pageSize) throws Exception {
        if(null==sort){
            sort = "visitDate";
        }
        if(null==asc){
            asc = false;
        }
        Page page = new Page(current,pageSize,sort,asc);

        EntityWrapper<VisitInfo> wrapper = new EntityWrapper<>();
        Page<VisitInfo> visitInfoPage = super.selectPage(page,wrapper);
        if(null!=visitInfoPage.getRecords() && visitInfoPage.getRecords().size()>0){
            List<VisitInfoVo> visitInfoVoList = new ArrayList<>();
            List<VisitInfoVo> list =new ArrayList<>();
            visitInfoPage.getRecords().stream().forEach(visitInfo -> {
                VisitInfoVo vo = new VisitInfoVo();
                BeanUtils.copyProperties(visitInfo,vo);
                Customer customer = customerService.selectById(visitInfo.getCustomerId());
                OrderInfo orderInfo = orderInfoService.selectById(visitInfo.getOrderId());
                vo.setCustomer(customer);
                vo.setOrderInfo(orderInfo);
                visitInfoVoList.add(vo);
            });
            if(null!=customerName && !customerName.equals("") && null !=orderCode && !orderCode.equals("")){
                list= visitInfoVoList.stream()
                        .filter(visitInfoVo -> visitInfoVo.getCustomer().getCustomerName().contains(customerName))
                        .filter(visitInfoVo -> visitInfoVo.getOrderInfo().getOrderCode().contains(orderCode)).collect(Collectors.toList());
            }else if(null!=customerName && !customerName.equals("")){
                list= visitInfoVoList.stream()
                        .filter(visitInfoVo -> visitInfoVo.getCustomer().getCustomerName().contains(customerName)).collect(Collectors.toList());
            }else if(null !=orderCode && !orderCode.equals("")){
                list= visitInfoVoList.stream()
                        .filter(visitInfoVo -> visitInfoVo.getOrderInfo().getOrderCode().contains(orderCode)).collect(Collectors.toList());
            }else {
                list.addAll(visitInfoVoList);
            }
            page = page.setRecords(list);
        }
        return page;
    }

    /**
     * 新增
     * @param visitInfo
     * @return
     * @throws Exception
     */
    @Override
    public boolean saveVisitInfo(VisitInfo visitInfo) throws Exception {
        return super.insert(visitInfo);
    }

    /**
     * 修改
     * @param visitInfo
     * @return
     * @throws Exception
     */
    @Override
    public boolean editVisitInfo(VisitInfo visitInfo) throws Exception {
        return super.updateById(visitInfo);
    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteVisitInfo(Integer id) throws Exception {
        VisitInfo old = super.selectById(id);
        old.setUseStatus(2);
        return super.updateById(old);
    }
}
