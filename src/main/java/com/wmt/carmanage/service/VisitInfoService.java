package com.wmt.carmanage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.VisitInfo;
import com.baomidou.mybatisplus.service.IService;
import com.wmt.carmanage.vo.VisitInfoVo;

/**
 * <p>
 * 回访信息 服务类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
public interface VisitInfoService extends IService<VisitInfo> {

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
    Page<VisitInfoVo> getVisitInfoList(String customerName, String orderCode, Integer current, String sort, Boolean asc, Integer pageSize) throws Exception;

    /**
     * 新增
     * @param visitInfo
     * @return
     * @throws Exception
     */
    boolean saveVisitInfo(VisitInfo visitInfo) throws Exception;

    /**
     * 修改
     * @param visitInfo
     * @return
     * @throws Exception
     */
    boolean editVisitInfo(VisitInfo visitInfo) throws Exception;

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteVisitInfo(Integer id) throws Exception;
}
