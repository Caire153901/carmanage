package com.wmt.carmanage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.CarImg;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  汽车图片 服务类
 * </p>
 *
 * @author wumt
 * @since 2018-09-14
 */
public interface CarImgService extends IService<CarImg> {

    Page<CarImg> getCarImgList(String carName,String carModel,String carColor,Integer current,String sort,String asc,Integer pageSize)throws Exception;


    /**
     * 新增
     * @param carImg
     * @return
     * @throws Exception
     */
    boolean saveCarImg(CarImg carImg) throws Exception;

    /**
     * 修改
     * @param carImg
     * @return
     * @throws Exception
     */
    boolean editCarImg(CarImg carImg) throws Exception;

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCarImg(Integer id) throws Exception;

}
