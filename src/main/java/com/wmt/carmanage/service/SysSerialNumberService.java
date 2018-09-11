package com.wmt.carmanage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wmt.carmanage.entity.SysSerialNumber;
import com.wmt.carmanage.vo.SysSerialNumberVo;

/**
 * <p>
 * 流水号表 服务类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
public interface SysSerialNumberService extends IService<SysSerialNumber> {

    /**
     * 流水号列表
     * @param authorityName
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    Page<SysSerialNumberVo> getSerialNumberList(String authorityName, Integer current, String sort, String asc, Integer pageSize) throws Exception;

    /**
     * 新增
     * @param sysSerialNumber
     * @return
     * @throws Exception
     */
    boolean saveSysSerialNumber(SysSerialNumber sysSerialNumber) throws Exception;

    /**
     * 修改
     * @param sysSerialNumber
     * @return
     * @throws Exception
     */
    boolean editSysSerialNumber(SysSerialNumber sysSerialNumber) throws Exception;

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysSerialNumber(Integer id) throws Exception;

    /**
     * 禁用/启用
     * @param id
     * @return
     * @throws Exception
     */
    boolean enableSysSerialNumber(Integer id,byte type) throws Exception;

    /**
     * 生成新的序列号
     * @param authoryityId
     * @return
     * @throws Exception
     */
    String getSerialNumberByAuthorityId(Integer authoryityId) throws Exception;

    /**
     * 根据config_templet生成新的序列号
     * @param config
     * @return
     * @throws Exception
     */
    String getSerialNumberByConfigTemplet(String config) throws Exception;


}
