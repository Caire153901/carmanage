package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
     * @return
     */
    @Override
    public List<SysSerialNumberVo> getList(String authorityName) {
        List<SysSerialNumberVo> serialNumberVoList = new ArrayList<>();
        Wrapper<SysSerialNumber> wrapper = new EntityWrapper<>();
        if(null!=authorityName && !authorityName.equals("")){
           Wrapper<Authority> authorityWrapper = new EntityWrapper<>();
           authorityWrapper.eq("authority_name",authorityName);
           Authority authority = authorityService.selectOne(authorityWrapper);
            wrapper.eq("authority_id",authority.getId());
        }
        List<SysSerialNumber> list = super.selectList(wrapper);
        list.stream().forEach(l->{
            SysSerialNumberVo vo = new SysSerialNumberVo();
            BeanUtils.copyProperties(l,vo);
            Authority authority = authorityService.selectById(l.getAuthorityId());
            vo.setAuthorityName(authority.getAuthorityName());
            serialNumberVoList.add(vo);
        });
        return serialNumberVoList;
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
