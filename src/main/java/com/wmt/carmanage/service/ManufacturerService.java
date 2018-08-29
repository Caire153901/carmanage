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

    /**
     * 新增
     * @param manufacturer
     * @return
     * @throws Exception
     */
    boolean saveManufacturer(Manufacturer manufacturer) throws Exception;

    /**
     * 修改
     * @param manufacturer
     * @return
     * @throws Exception
     */
    boolean editManufacturer(Manufacturer manufacturer) throws Exception;

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteManufacturer(Integer id) throws Exception;

    /**
     * 禁用/启用
     * @param id
     * @return
     * @throws Exception
     */
    boolean enableManufacturer(Integer id,byte type) throws Exception;

}
