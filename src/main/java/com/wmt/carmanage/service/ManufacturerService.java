package com.wmt.carmanage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Manufacturer;
import com.baomidou.mybatisplus.service.IService;
import com.wmt.carmanage.vo.ManufacturerVo;

/**
 * <p>
 * 厂商信息 服务类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
public interface ManufacturerService extends IService<Manufacturer> {

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
    Page<ManufacturerVo> getManufacturerList(String manufacturerCode, String manufacturerName, Integer current, String sort, Boolean asc, Integer pageSize) throws Exception;

}
