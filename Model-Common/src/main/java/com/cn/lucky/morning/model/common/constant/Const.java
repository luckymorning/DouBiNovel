package com.cn.lucky.morning.model.common.constant;

public class Const {

    public static class session {

        public static final String LOGIN_ADMIN = "login_admin";

    }

    public static class cache {
        /**
         * 根据ID保存角色
         */
        public static final String ROLE_ID = "role.id.";

        /**
         * 根据ID保存角色过期时间
         */
        public static final int ROLE_ID_TTL = 60 * 60 * 24;

    }

}
