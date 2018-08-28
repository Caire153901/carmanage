package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Manufacturer;
import com.wmt.carmanage.mapper.ManufacturerMapper;
import com.wmt.carmanage.service.ManufacturerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.vo.ManufacturerVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 厂商信息 服务实现类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@Service
public class ManufacturerServiceImpl extends ServiceImpl<ManufacturerMapper, Manufacturer> implements ManufacturerService {
    /**
     * 厂商信息列表
     * @param manufacturerCode
     * @param manufacturerName
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public Page<ManufacturerVo> getManufacturerList(String manufacturerCode, String manufacturerName, Integer current, String sort, Boolean asc, Integer pageSize) throws Exception {
        if(null==sort){
            sort ="gmtModified";
        }
        if(null==asc){
            asc = false;
        }
        Page page = new Page(current,pageSize,sort,asc);
        EntityWrapper<Manufacturer> wrapper = new EntityWrapper<>();
        if(null!=manufacturerCode && !manufacturerCode.equals("")){
            wrapper.like("manufacturer_code",manufacturerCode);
        }
        if(null!=manufacturerName && !manufacturerName.equals("")){
            wrapper.like("manufacturer_name",manufacturerName);
        }
        Page<Manufacturer> manufacturerPage = super.selectPage(page,wrapper);
        if(null!=manufacturerPage.getRecords() && manufacturerPage.getRecords().size()>0){
            List<ManufacturerVo> manufacturerVoList = new ArrayList<>();
            manufacturerPage.getRecords().stream().forEach(manufacturer -> {
                ManufacturerVo vo = new ManufacturerVo();
                BeanUtils.copyProperties(manufacturer,vo);
                manufacturerVoList.add(vo);
            });
            page = page.setRecords(manufacturerVoList);
        }
        return page;
    }
}
