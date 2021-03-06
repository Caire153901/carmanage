package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Manufacturer;
import com.wmt.carmanage.entity.SysSerialNumber;
import com.wmt.carmanage.exception.BaseException;
import com.wmt.carmanage.mapper.ManufacturerMapper;
import com.wmt.carmanage.service.ManufacturerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.service.SysSerialNumberService;
import com.wmt.carmanage.vo.ManufacturerVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    SysSerialNumberService sysSerialNumberService;

    /**
     * 获取厂商下拉列表
     * @return
     */
    @Override
    public List<ManufacturerVo> getSelect() {
        List<ManufacturerVo> list = new ArrayList<>();
        Wrapper<Manufacturer> wrapper = new EntityWrapper<>();
        wrapper.eq("use_status",0);
        List<Manufacturer> manufacturerList = super.selectList(wrapper);
        manufacturerList.stream().forEach(manufacturer -> {
            ManufacturerVo vo = new ManufacturerVo();
            BeanUtils.copyProperties(manufacturer,vo);
            list.add(vo);
        });
        return list;
    }

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
    public Page<ManufacturerVo> getManufacturerList(String manufacturerCode, String manufacturerName, Integer current, String sort, String asc, Integer pageSize) throws Exception {
        boolean orderSort = false;
        if(null==sort){
            sort ="gmtModify";
        }
        if(null!=asc && asc.equals("asc")){
            orderSort = true;
        }
        Page page = new Page(current,pageSize,sort,orderSort);
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

    /**
     * 新增
     * @param manufacturer
     * @return
     * @throws Exception
     */
    @Override
    public boolean saveManufacturer(Manufacturer manufacturer) throws Exception {
        Wrapper<Manufacturer> wrapper = new EntityWrapper<>();
        wrapper.eq("manufacturer_name",manufacturer.getManufacturerName());
        wrapper.or("manufacturer_code",manufacturer.getManufacturerCode());
        wrapper.notIn("use_status","0,1");
        List<Manufacturer> list = super.selectList(wrapper);
        if(list.size()>0){
            throw new BaseException("厂商名："+manufacturer.getManufacturerName()+",或厂商编号"+manufacturer.getManufacturerCode()+"已存在");
        }else{
            Wrapper<SysSerialNumber> sysSerialNumberWrapper = new EntityWrapper<>();
            sysSerialNumberWrapper.eq("config_templet","CS");
            SysSerialNumber sysSerialNumber = sysSerialNumberService.selectOne(sysSerialNumberWrapper);
            sysSerialNumber.setGmtModified(new Date());
            sysSerialNumberService.updateById(sysSerialNumber);
            return super.insert(manufacturer);
        }
    }

    /**
     * 修改
     * @param manufacturer
     * @return
     * @throws Exception
     */
    @Override
    public boolean editManufacturer(Manufacturer manufacturer) throws Exception {
        Wrapper<Manufacturer> wrapper = new EntityWrapper<>();
        Manufacturer old = super.selectById(manufacturer.getId());
        if(!manufacturer.getManufacturerName().equals(old.getManufacturerName())){
            wrapper.eq("manufacturer_name",manufacturer.getManufacturerName());
            wrapper.notIn("use_status","0,1");
            List<Manufacturer> list = super.selectList(wrapper);
            if(list.size()>0){
                throw new BaseException("厂商名："+manufacturer.getManufacturerName()+"已存在");
            }
        }
        return super.updateById(manufacturer);
    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteManufacturer(Integer id) throws Exception {
        Manufacturer old = super.selectById(id);
        old.setUseStatus(2);
        return super.updateById(old);
    }

    /**
     * 启用/禁用
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public boolean enableManufacturer(Integer id, byte type) throws Exception {
        Manufacturer old = super.selectById(id);
        if(type==0){//启用
            old.setUseStatus(0);
        }else if(type==1){//禁用
            old.setUseStatus(1);
        }
        return super.updateById(old);
    }
}
