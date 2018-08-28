package com.wmt.carmanage.service;

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

}
