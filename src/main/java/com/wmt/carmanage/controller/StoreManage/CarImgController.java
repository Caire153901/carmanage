package com.wmt.carmanage.controller.StoreManage;


import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.constant.EUDataGridResult;
import com.wmt.carmanage.entity.CarImg;
import com.wmt.carmanage.service.CarImgService;
import com.wmt.carmanage.service.CarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;

/**
 * 汽车图片管理
 */
@RestController
@RequestMapping("/car_img")
public class CarImgController {

    @Autowired
    CarImgService carImgService;

    /**
     * 汽车图片
     * @param carName
     * @param carModel
     * @param carColor
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public EUDataGridResult getStoreInfoList(
            @RequestParam(value = "carName",required = false) String carName,
            @RequestParam(value = "carModel",required = false) String carModel,
            @RequestParam(value = "carColor",required = false) String carColor,
            @RequestParam(value = "page",required = false,defaultValue = "1") Integer current,
            @RequestParam(value = "sort",required = false,defaultValue = "gmtModify") String sort,
            @RequestParam(value = "order",required = false) String asc,
            @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "rows",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        Page<CarImg> page = carImgService.getCarImgList(carName, carModel, carColor, current, sort, asc, pageSize);
        EUDataGridResult all = new EUDataGridResult();
        all.setRows(page.getRecords());
        all.setTotal(page.getTotal());
        return all;
    }

    /**
     * 添加
     * @param carImg
     * @return
     * @throws Exception
     */
    @PostMapping("/saveOrUpdateCarImg")
    public boolean saveOrUpdateCarImg(@Validated CarImg carImg) throws Exception{
        if(null==carImg.getId()){
            return carImgService.saveCarImg(carImg);
        }else {
            return carImgService.editCarImg(carImg);
        }
    }



    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    public boolean deleteCarImg(@RequestParam Integer id) throws Exception{
        return carImgService.deleteCarImg(id);
    }




}
