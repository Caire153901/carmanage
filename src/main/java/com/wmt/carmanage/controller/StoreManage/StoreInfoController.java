package com.wmt.carmanage.controller.StoreManage;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.constant.EUDataGridResult;
import com.wmt.carmanage.entity.StoreInfo;
import com.wmt.carmanage.service.StoreInfoService;
import com.wmt.carmanage.vo.StoreInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * 仓库信息管理
 */
@RestController
@RequestMapping("/store")
public class StoreInfoController {
    @Autowired
    StoreInfoService storeInfoService;

    /**
     * 获取仓库下拉列表
     * @return
     */
    @GetMapping("/store_select")
    public List<StoreInfoVo> getStoreSelect(){
        return storeInfoService.getSelect();
    }

    /**
     * 仓库信息
     * @param storeName
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public EUDataGridResult getStoreInfoList(
                                         @RequestParam(value = "storeName",required = false) String storeName,
                                         @RequestParam(value = "isChoose",required = false) Boolean isChoose,
                                         @RequestParam(value = "page",required = false,defaultValue = "1") Integer current,
                                         @RequestParam(value = "sort",required = false,defaultValue = "gmtModify") String sort,
                                         @RequestParam(value = "order",required = false) String asc,
                                         @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "rows",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        Page<StoreInfoVo> page = storeInfoService.getStoreInfoList(storeName, isChoose,current, sort, asc, pageSize);
        EUDataGridResult all = new EUDataGridResult();
        all.setRows(page.getRecords());
        all.setTotal(page.getTotal());
        return all;
    }

    /**
     * 添加
     * @param storeInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/saveOrUpdateStoreInfo")
    public boolean saveOrUpdateStoreInfo(@Validated StoreInfo storeInfo) throws Exception{
        if(null==storeInfo.getId()){
            return storeInfoService.saveStoreInfo(storeInfo);
        }else {
            return storeInfoService.editStoreInfo(storeInfo);
        }
    }
    
    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    public boolean deleteStoreInfo(@RequestParam Integer id) throws Exception{
        return storeInfoService.deleteStoreInfo(id);
    }

    /**
     * 启用/禁用
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @PostMapping("/enable")
    public boolean enableStoreInfo(@RequestParam Integer id,@RequestParam byte type) throws Exception{
        return storeInfoService.enableStoreInfo(id,type);
    }

    /**
     * 根据ID获取仓库信息
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public StoreInfoVo getStoreInfoVoById(@RequestParam Integer id){
        StoreInfoVo vo = new StoreInfoVo();
        StoreInfo storeInfo = storeInfoService.selectById(id);
        BeanUtils.copyProperties(storeInfo,vo);
        return vo;
    }

}
