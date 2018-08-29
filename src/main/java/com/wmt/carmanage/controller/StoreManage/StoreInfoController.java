package com.wmt.carmanage.controller.StoreManage;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.StoreInfo;
import com.wmt.carmanage.service.StoreInfoService;
import com.wmt.carmanage.vo.StoreInfoVo;
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
    public Page<StoreInfoVo> getStoreInfoList(
                                         @RequestParam(value = "storeName",required = false) String storeName,
                                         @RequestParam(value = "isChoose",required = false) Boolean isChoose,
                                         @RequestParam(value = "current",required = false,defaultValue = "1") Integer current,
                                         @RequestParam(value = "sort",required = false,defaultValue = "gmtModified") String sort,
                                         @RequestParam(value = "asc",required = false) Boolean asc,
                                         @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        return storeInfoService.getStoreInfoList(storeName, isChoose,current, sort, asc, pageSize);
    }


    /**
     * 添加
     * @param storeInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public boolean saveStoreInfo(@Validated StoreInfo storeInfo) throws Exception{
        return storeInfoService.saveStoreInfo(storeInfo);
    }

    /**
     * 修改
     * @param storeInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/edit")
    public boolean updateStoreInfo(@Validated StoreInfo storeInfo) throws Exception{
        return storeInfoService.editStoreInfo(storeInfo);
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

}
