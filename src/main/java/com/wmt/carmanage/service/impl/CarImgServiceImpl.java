package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.CarImg;
import com.wmt.carmanage.mapper.CarImgMapper;
import com.wmt.carmanage.service.CarImgService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  汽车图片服务实现类
 * </p>
 *
 * @author wumt
 * @since 2018-09-14
 */
@Service
public class CarImgServiceImpl extends ServiceImpl<CarImgMapper, CarImg> implements CarImgService {
    /**
     * 汽车图片列表
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
    @Override
    public Page<CarImg> getCarImgList(String carName, String carModel, String carColor, Integer current, String sort, String asc, Integer pageSize) throws Exception {
        boolean orderSort = false;
        if(null==sort){
            sort = "gmtModify";
        }
        if(null!=asc && asc.equals("asc")){
            orderSort = true;
        }
        Page page = new Page(current,pageSize,sort,orderSort);
        EntityWrapper<CarImg> wrapper = new EntityWrapper<>();
        if(null!=carName && !carName.equals("")){
            wrapper.eq("car_name",carName);
        }
        if(null!=carModel && !carModel.equals("")){
            wrapper.eq("car_model",carModel);
        }
        if(null!=carColor && !carColor.equals("")){
            wrapper.eq("car_color",carColor);
        }
        Page<CarImg> carImgPage = super.selectPage(page,wrapper);
        if(null!=carImgPage.getRecords() && carImgPage.getRecords().size()>0){
            page = page.setRecords(carImgPage.getRecords());
        }
        return page;
    }

    /**
     * 保存
     * @param carImg
     * @return
     * @throws Exception
     */
    @Override
    public boolean saveCarImg(CarImg carImg) throws Exception {
        return super.insert(carImg);
    }

    /**
     * 修改
     * @param carImg
     * @return
     * @throws Exception
     */
    @Override
    public boolean editCarImg(CarImg carImg) throws Exception {
        return super.updateById(carImg);
    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteCarImg(Integer id) throws Exception {
        CarImg carImg = super.selectById(id);
        carImg.setUseStatus(2);
        return super.updateById(carImg);
    }
}
