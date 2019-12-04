package com.cn.lucky.morning.model.service;

import com.cn.lucky.morning.model.domain.SystemSetting;

public interface SystemSettingService {

    SystemSetting getSetting();

    boolean add(SystemSetting setting);

    boolean edit(SystemSetting setting);

    SystemSetting createNew();
}
