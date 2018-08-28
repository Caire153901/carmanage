package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wmt.carmanage.entity.StoreInfo;
import com.wmt.carmanage.mapper.StoreInfoMapper;
import com.wmt.carmanage.service.StoreInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.vo.StoreInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 仓库信息 服务实现类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@Service
public class StoreInfoServiceImpl extends ServiceImpl<StoreInfoMapper, StoreInfo> implements StoreInfoService {
    /**
     * 获取仓库的下拉列表
     * @return
     */
    @Override
    public List<StoreInfoVo> getSelect() {
        List<StoreInfoVo> list = new ArrayList<>();
        Wrapper<StoreInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("use_status",0);
        List<StoreInfo> storeInfos = super.selectList(wrapper);
        storeInfos.stream().forEach(storeInfo -> {
            StoreInfoVo vo = new StoreInfoVo();
            BeanUtils.copyProperties(storeInfo,vo);
            list.add(vo);
        });
        return list;
    }
}
