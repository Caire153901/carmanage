package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Authority;
import com.wmt.carmanage.entity.SysSerialNumber;
import com.wmt.carmanage.mapper.SysSerialNumberMapper;
import com.wmt.carmanage.service.AuthorityService;
import com.wmt.carmanage.service.SysSerialNumberService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.util.DateUtils;
import com.wmt.carmanage.vo.SysSerialNumberVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 流水号表 服务实现类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@Service
@SuppressWarnings("all")
public class SysSerialNumberServiceImpl extends ServiceImpl<SysSerialNumberMapper, SysSerialNumber> implements SysSerialNumberService {

     @Autowired
     AuthorityService authorityService;

    /**
     * 流水号列表
     * @param authorityName
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public Page<SysSerialNumberVo> getSerialNumberList(String authorityName, Integer current, String sort, String asc, Integer pageSize) throws Exception {
        boolean orderSort = false;
        if (null == sort) {
            sort = "gmtModified";
        }
        if (null != asc && asc.equals("asc")) {
            orderSort = true;
        }
        List<SysSerialNumberVo> list = new ArrayList<>();
        Page page = new Page(current, pageSize, sort, orderSort);
        EntityWrapper<SysSerialNumber> wrapper = new EntityWrapper<>();
        Page<SysSerialNumber> sysSerialNumberPage = super.selectPage(page,wrapper);
        if(null!=sysSerialNumberPage.getRecords() && sysSerialNumberPage.getRecords().size()>0){
            List<SysSerialNumberVo> sysSerialNumberVos = new ArrayList<>();
            sysSerialNumberPage.getRecords().stream().forEach(sysSerialNumber -> {
                SysSerialNumberVo vo = new SysSerialNumberVo();
                BeanUtils.copyProperties(sysSerialNumber,vo);
                Authority authority = authorityService.selectById(sysSerialNumber.getAuthorityId());
                vo.setAuthorityName(authority.getAuthorityName());
                sysSerialNumberVos.add(vo);
            });
            //查询过滤
            if(null!=authorityName && !authorityName.equals("")){
                list = sysSerialNumberVos.stream().filter(sysSerialNumberVo -> sysSerialNumberVo.getAuthorityName().contains(authorityName)).collect(Collectors.toList());
            }else{
                list.addAll(sysSerialNumberVos);
            }
            page = page.setRecords(list);
        }
        return page;
    }

    /**
     * 新增
     * @param sysSerialNumberVo
     * @return
     * @throws Exception
     */
    @Override
    public boolean saveSysSerialNumber(SysSerialNumberVo sysSerialNumberVo) throws Exception {
        SysSerialNumber sysSerialNumber = new SysSerialNumber();
        BeanUtils.copyProperties(sysSerialNumberVo,sysSerialNumber);
        return super.insert(sysSerialNumber);
    }

    /**
     * 修改
     * @param sysSerialNumberVo
     * @return
     * @throws Exception
     */
    @Override
    public boolean editSysSerialNumber(SysSerialNumberVo sysSerialNumberVo) throws Exception {
        SysSerialNumber sysSerialNumber = new SysSerialNumber();
        BeanUtils.copyProperties(sysSerialNumberVo,sysSerialNumber);
        return super.updateById(sysSerialNumber);
    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteSysSerialNumber(Integer id) throws Exception {
        SysSerialNumber sysSerialNumber = super.selectById(id);
        sysSerialNumber.setUseStatus(2);
        return super.updateById(sysSerialNumber);
    }

    /**
     * 禁用/启用
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public boolean enableSysSerialNumber(Integer id, byte type) throws Exception {
        SysSerialNumber sysSerialNumber = super.selectById(id);
        if(type==0){//启用
            sysSerialNumber.setUseStatus(0);
        }else if(type==1){//禁用
            sysSerialNumber.setUseStatus(1);
        }
        return super.updateById(sysSerialNumber);
    }

    /**
     * 生成新的序列号
     * @param authoryityId
     * @return
     * @throws Exception
     */
    @Override
    public String getSerialNumberByAuthorityId(Integer authoryityId) throws Exception {
        String code = "";
        Wrapper<SysSerialNumber> sysSerialNumberWrapper = new EntityWrapper<>();
        sysSerialNumberWrapper.eq("authority_id",authoryityId);
        SysSerialNumber sysSerialNumber = super.selectOne(sysSerialNumberWrapper);
        Integer currutSerial =sysSerialNumber.getCurrutSerial();
        String modifiedDate = DateUtils.formatDate(sysSerialNumber.getGmtModified());
        String nowDate = DateUtils.getDate();
        if(modifiedDate.equals(nowDate)){
            currutSerial = currutSerial+1;
        }else{
            currutSerial = 1;
        }
        sysSerialNumber.setCurrutSerial(currutSerial);
        super.updateById(sysSerialNumber);
        Integer length =sysSerialNumber.getMaxSerial() - String.valueOf(currutSerial).length();
        String valueLength = "0";
        for(int i=0;i<length;i++){
            valueLength = valueLength + "0";
        }
        String config = sysSerialNumber.getConfigTemplet();
        String date = DateUtils.formatDate(new Date(),"yyyyMMdd");
        code = config + date + valueLength +currutSerial;

        return code;
    }

}
