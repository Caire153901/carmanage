package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Role;
import com.wmt.carmanage.mapper.RoleMapper;
import com.wmt.carmanage.service.RoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.vo.RoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    /**
     * 获取角色的下拉列表
     * @return
     */
    @Override
    public List<RoleVo> getRoleSelect() {
        List<RoleVo> list = new ArrayList<>();
        Wrapper<Role> wrapper = new EntityWrapper<>();
        wrapper.eq("use_status",0);
        List<Role> roleList = super.selectList(wrapper);
        roleList.stream().forEach(role -> {
            RoleVo vo = new RoleVo();
            BeanUtils.copyProperties(role,vo);
            list.add(vo);
        });
        return list;
    }

    /**
     * 角色列表
     * @param roleName
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public Page<RoleVo> getRoleList(String roleName, Integer current, String sort, Boolean asc, Integer pageSize) throws Exception {
        if(null == sort){
            sort ="gmtModified";
        }
        if(null==asc){
            asc = false;
        }
        Page page = new Page(current,pageSize,sort,asc);
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        if(null!=roleName && !roleName.equals("")){
            wrapper.like("")
        }
        return null;
    }



    // 1.获取池塘ID
    String poolId = getPoolId(customerId, poolNumber);
    // 2.通过养殖场ID和池塘ID查询饲料投喂记录
    EntityWrapper<Bpfeed> wrapper = new EntityWrapper<>();
        wrapper.eq("CustomerId", customerId)
            .eq("PoolId", poolId);
    Page<Bpfeed> bpfeedPage = bpfeedService.selectPage(page,wrapper);

    // 3.判断并组装返回结果
        if(null == bpfeedPage.getRecords() || bpfeedPage.getRecords().size() == 0 ){
        throw new BaseException("未查询到饲料投喂信息");
    }else {
        List<FeedInfoVo> feedInfoVoList = Lists.newArrayList();
        bpfeedPage.getRecords().stream().forEach(bpfeed -> {
            FeedInfoVo vo = new FeedInfoVo();
            vo.setFeedDate(bpfeed.getDateTime());
            vo.setFeedTypeName(bpfeed.getFeedTypeName());
            vo.setFeedTimes(bpfeed.getFeedTimes());
            vo.setFeedWeight(bpfeed.getWeight());
            vo.setUnit(bpfeed.getUnit());
            feedInfoVoList.add(vo);
        });
        page = page.setRecords(feedInfoVoList);  //查出的list调用setRecords
        return page;
    }
}
