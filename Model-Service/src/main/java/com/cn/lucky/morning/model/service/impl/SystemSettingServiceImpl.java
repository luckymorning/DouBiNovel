package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.dao.SystemSettingMapper;
import com.cn.lucky.morning.model.domain.SystemSetting;
import com.cn.lucky.morning.model.domain.SystemSettingExample;
import com.cn.lucky.morning.model.service.SystemSettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemSettingServiceImpl implements SystemSettingService {
    @Resource
    private SystemSettingMapper mapper;

    @Override
    public SystemSetting getSetting() {SystemSettingExample example = new SystemSettingExample();
        List<SystemSetting> list = mapper.selectByExample(example);
        SystemSetting setting;
        if (list!=null && list.size() > 0){
            setting = list.get(0);
        }else {
            setting = createNew();
        }
        return setting;
    }

    @Override
    public boolean add(SystemSetting setting) {
        return mapper.insertSelective(setting) > 0;
    }

    @Override
    public boolean edit(SystemSetting setting) {
        return mapper.updateByPrimaryKeySelective(setting) > 0;
    }

    @Override
    public SystemSetting createNew() {
        SystemSetting setting = new SystemSetting();
        setting.setSitename("逗逼阅读");
        setting.setDomain("http://novel.luckymorning.cn");
        setting.setAdminIndexName("逗逼阅读 - 后端首页");
        setting.setMetaKeywords("逗逼联盟,逗逼阅读,无广告,小说");
        setting.setMetaDescription("做最简洁，最干净的小说聚合网站");
        setting.setCopyrightInfo("Copyright © 2020 逗逼阅读 All Rights Reserved.");
        boolean isSuccess = add(setting);
        if (isSuccess){
            return setting;
        }
        return null;
    }
}
