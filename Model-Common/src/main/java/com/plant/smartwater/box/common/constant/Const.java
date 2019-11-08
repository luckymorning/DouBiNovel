package com.plant.smartwater.box.common.constant;

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

        /**
         * 根据ID保存用户
         */
        public static final String USER_ID = "user.id.";

        /**
         * 根据CODE保存用户
         */
        public static final String USER_CODE = "user.code.";

        /**
         * 根据ID保存用户过期时间
         */
        public static final int USER_ID_TTL = 60 * 60 * 24;

        /**
         * 根据ID保存系统设置
         */
        public static final String SYSTEM_CONFIG_ID = "system.config.";

        /**
         * 根据ID保存角色过期时间
         */
        public static final int SYSTEM_CONFIG_ID_TTL = 60 * 60 * 24;

        /**
         * 根据ID保存角色
         */
        public static final String AUTHORITY_ID = "authority.id.";

        /**
         * 根据ID保存角色过期时间
         */
        public static final int AUTHORITY_ID_TTL = 60 * 60 * 24;

        /**
         * 根据ID保存设备信息
         */
        public static final String DEVICE_INFO_ID = "device_info.id.";
        /**
         * 根据ID保存设备信息过期时间
         */
        public static final int DEVICE_INFO_ID_TIL = 60 * 60 * 24;
        /**
         * 根据ID保存设备状态
         */
        public static final String DEVICE_STATIS_ID = "device_status.id.";
        /**
         * 根据ID保存设备状态过期时间
         */
        public static final int DEVICE_STATIS_ID_TIL = 60 * 60 * 24;
        /**
         * 根据ID保存设备类型
         */
        public static final String DEVICE_TYPE_ID = "device_type.id.";
        /**
         * 根据ID保存设备类型过期时间
         */
        public static final int DEVICE_TYPE_ID_TIL = 60 * 60 * 24;
        /**
         * 根据ID保存流程
         */
        public static final String FLOW_ID = "flow.id.";
        /**
         * 根据ID保存流程过期时间
         */
        public static final int FLOW_ID_TIL = 60 * 60 * 24;
        /**
         * 根据ID保存流程模板
         */
        public static final String FLOW_TEMPLATE_ID = "flowTemplate.id.";
        /**
         * 根据ID保存流程模板过期时间
         */
        public static final int FLOW_TEMPLATE_ID_TIL = 60 * 60 * 24;
        /**
         * 根据设备unique_id保存实时值
         */
        public static final String REALDATA_UNIQUE_ID = "realData.unique_id.";
        /**
         * 根据ID保存流程模板
         */
        public static final String MESSAGE_ID = "message.id.";
        /**
         * 根据ID保存流程模板过期时间
         */
        public static final int MESSAGE_ID_TIL = 60 * 60 * 24;
    }

    public static class clientType{
        public static final String DRIVER = "Driver";
        public static final String INTERFACE = "Interface";
        public static final String WEB = "Web";
    }

    public static class role {

        public static final int IS_SUPER = 1;

        public static final int NOT_SUPER = 0;

    }

    public static class user {

        public static final int IS_COSTOMER = 1;

        public static final int NOT_CUSTOMER = 0;
    }

}
