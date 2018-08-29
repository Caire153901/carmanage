package com.wmt.carmanage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.StoreInfo;
import com.baomidou.mybatisplus.service.IService;
import com.wmt.carmanage.vo.StoreInfoVo;

import java.util.List;

/**
 * <p>
 * 仓库信息 服务类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
public interface StoreInfoService extends IService<StoreInfo> {

    /**
     * 获取仓库下拉列表
     * @return
     */
    List<StoreInfoVo> getSelect();

    /**
     * 仓库信息列表
     * @param storeName
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    Page<StoreInfoVo> getStoreInfoList(String storeName, Boolean isChoose,Integer current, String sort, Boolean asc, Integer pageSize)throws Exception;

    /**
     * 新增
     * @param storeInfo
     * @return
     * @throws Exception
     */
    boolean saveStoreInfo(StoreInfo storeInfo) throws Exception;

    /**
     * 修改
     * @param storeInfo
     * @return
     * @throws Exception
     */
    boolean editStoreInfo(StoreInfo storeInfo) throws Exception;

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteStoreInfo(Integer id) throws Exception;

    /**
     * 禁用/启用
     * @param id
     * @return
     * @throws Exception
     */
    boolean enableStoreInfo(Integer id,byte type) throws Exception;

}
