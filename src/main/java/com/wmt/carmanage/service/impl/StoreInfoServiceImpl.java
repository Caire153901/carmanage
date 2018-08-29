package com.wmt.carmanage.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.StoreInfo;
import com.wmt.carmanage.exception.BaseException;
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
        wrapper.gt("margin_capacity",0);
        List<StoreInfo> storeInfos = super.selectList(wrapper);
        storeInfos.stream().forEach(storeInfo -> {
            StoreInfoVo vo = new StoreInfoVo();
            BeanUtils.copyProperties(storeInfo,vo);
            list.add(vo);
        });
        return list;
    }

    /**
     * 仓库列表
     * @param storeName
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public Page<StoreInfoVo> getStoreInfoList(String storeName,Boolean isChoose, Integer current, String sort, Boolean asc, Integer pageSize) throws Exception {
        if(null==sort){
            sort = "gmtModified";
        }
        if(null==asc){
            asc = false;
        }
        Page page = new Page(current,pageSize,sort,asc);
        EntityWrapper<StoreInfo> wrapper = new EntityWrapper<>();
        if(null!=storeName && !storeName.equals("")){
            wrapper.eq("store_name",storeName);
        }
        if(null!=isChoose){
            wrapper.gt("margin_capacity",0);
        }
        Page<StoreInfo> storeInfoPage = super.selectPage(page,wrapper);
        if(null!=storeInfoPage.getRecords() && storeInfoPage.getRecords().size()>0){
            List<StoreInfoVo> voList = new ArrayList<>();
            storeInfoPage.getRecords().stream().forEach(storeInfo -> {
                StoreInfoVo vo = new StoreInfoVo();
                BeanUtils.copyProperties(storeInfo,vo);
                voList.add(vo);
            });
            page = page.setRecords(voList);
        }
        return page;
    }

    /**
     * 新增
     * @param storeInfo
     * @return
     * @throws Exception
     */
    @Override
    public boolean saveStoreInfo(StoreInfo storeInfo) throws Exception {
        EntityWrapper<StoreInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("store_name",storeInfo.getStoreName());
        List<StoreInfo> list = super.selectList(wrapper);
        if(list.size()>0){
            throw new BaseException("仓库名：【"+storeInfo.getStoreName()+"】已存在");
        }else{
            Integer sy = storeInfo.getMaxCapacity()-storeInfo.getCapacity();
            storeInfo.setMarginCapacity(sy);
            return super.insert(storeInfo);
        }
    }

    /**
     * 修改
     * @param storeInfo
     * @return
     * @throws Exception
     */
    @Override
    public boolean editStoreInfo(StoreInfo storeInfo) throws Exception {
        StoreInfo old = super.selectById(storeInfo.getId());
        if(!old.getStoreName().equals(storeInfo.getStoreName())){
            EntityWrapper<StoreInfo> wrapper = new EntityWrapper();
            wrapper.eq("store_name",storeInfo.getStoreName());
            List<StoreInfo> list = super.selectList(wrapper);
            if(list.size()>0){
                throw new BaseException("仓库名：【"+storeInfo.getStoreName()+"】已存在");
            }else{
                Integer sy = storeInfo.getMaxCapacity()-storeInfo.getCapacity();
                storeInfo.setMarginCapacity(sy);
            }
        }
        return super.updateById(storeInfo);
    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteStoreInfo(Integer id) throws Exception {
        StoreInfo old = super.selectById(id);
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
    public boolean enableStoreInfo(Integer id, byte type) throws Exception {
        StoreInfo old = super.selectById(id);
        if(type==0){
            old.setUseStatus(0);
        }else if(type==1){
            old.setUseStatus(1);
        }
        return super.updateById(old);
    }
}
