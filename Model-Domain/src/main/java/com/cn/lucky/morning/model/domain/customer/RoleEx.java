package com.cn.lucky.morning.model.domain.customer;


import com.cn.lucky.morning.model.domain.Role;

public class RoleEx extends Role {
    private Object[] authoritys;

    public Object[] getAuthoritys() {
        return authoritys;
    }

    public void setAuthoritys(Object[] authoritys) {
        this.authoritys = authoritys;
    }
}
