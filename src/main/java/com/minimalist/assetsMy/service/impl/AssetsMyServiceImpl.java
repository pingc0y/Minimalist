package com.minimalist.assetsMy.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minimalist.assets.entity.Assets;
import com.minimalist.assets.mapper.AssetsMapper;
import com.minimalist.assetsAuthorization.entity.AssetsAuthorization;
import com.minimalist.assetsAuthorization.mapper.AssetsAuthorizationMapper;
import com.minimalist.assetsMy.entity.AssetsMy;
import com.minimalist.assetsMy.service.AssetsMyService;
import com.minimalist.user.entity.User;
import com.minimalist.userGroup.entity.UserGroup;
import com.minimalist.userGroup.mapper.UserGroupMapper;
import com.minimalist.userGroup.service.UserGroupService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AssetsMyServiceImpl implements AssetsMyService {
    @Autowired
    AssetsMapper assetsMapper;
    @Autowired
    AssetsAuthorizationMapper assetsAuthorizationMapper;
    @Autowired
    UserGroupMapper userGroupMapper;

    @Override
    public Map<String, Object> select(int from, int size, Map<String, String> conditionMap) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User)session.getAttribute("user");
        QueryWrapper ugWrapper = new QueryWrapper();
        ugWrapper.like("user_id",user.getId());
        ArrayList<UserGroup> userGroups =(ArrayList<UserGroup>) userGroupMapper.selectList(ugWrapper);
        Page<AssetsAuthorization> amPage = new Page<AssetsAuthorization>(from,size);

//        QueryWrapper amWrapper = new QueryWrapper();
        LambdaQueryWrapper<AssetsAuthorization> amWrapper = new QueryWrapper<AssetsAuthorization>().lambda();

        QueryWrapper aWrapper = new QueryWrapper();


            for (String key : conditionMap.keySet()) {
                String value = conditionMap.get(key);
                if (value != null && value.length() != 0) {
                    aWrapper.like(key, value);
                }
            }

        ArrayList<Assets> list =(ArrayList<Assets>) assetsMapper.selectList(aWrapper);
        //是否激活
        amWrapper.eq(AssetsAuthorization::getActivate,0);



            amWrapper.and(wrapper -> {
                //是否用户
                wrapper.like(AssetsAuthorization::getUserId,user.getId());
                //是否在用户组
                if(userGroups.size()!=0){
                    for (UserGroup userGroup : userGroups) {
                        wrapper.or();
                        wrapper.like(AssetsAuthorization::getUserGroupId,userGroup.getId());
                    }
                }
            });


        //搜索条件资产名
        if(list.size()!=0){
            amWrapper.and(wrapper -> {
                for (Assets assets : list) {
                    wrapper.eq(AssetsAuthorization::getAssetsId,assets.getId());
                    wrapper.or();
                }
            });

        }else if(conditionMap!=null && list.size()==0){
            amWrapper.eq(AssetsAuthorization::getAssetsId,"0");

        }


        assetsAuthorizationMapper.selectByPgEw(amPage,amWrapper);
        List<AssetsAuthorization> records = amPage.getRecords();
        ArrayList<AssetsMy> assetsmys = new ArrayList<>();
        for (AssetsAuthorization record : records) {
            Assets assets =assetsMapper.selectById(record.getAssetsId());
            AssetsMy assetsMy = new AssetsMy();
            assetsMy.setHostname(assets.getHostname());
            assetsMy.setAddress(assets.getAddress());
            assetsMy.setProtocol(assets.getProtocol());
            assetsMy.setPort(assets.getPort());
            assetsMy.setActivate(assets.getActivate());
            assetsMy.setRemark(assets.getRemark());
            assetsMy.setStatus(assets.getStatus());
            assetsMy.setAssetsId(record.getAssetsId());
            assetsMy.setAssetsUserId(record.getAssetsUserId());
            assetsmys.add(assetsMy);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("data",assetsmys);
        map.put("count",assetsmys.size());
        return map;
    }



}
