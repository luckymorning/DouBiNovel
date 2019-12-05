package com.cn.lucky.morning.model.common.constant;

public class Const {

    public static class session {

        public static final String LOGIN_ADMIN = "login_admin";

        public static final String VERIFICATION_CODE = "verificationCode";

    }

    public static class role {

        public static final int IS_SUPER = 1;

        public static final int NOT_SUPER = 0;

        public static final String ROLE_SUPER = "role.super";

    }

    public static class cache {

        /**
         * 根据ID保存权限组
         */
        public static final String AUTHORITY_GROUP_ID = "authorityGroup.id.";

        /**
         * 根据ID保存权限组过期权限
         */
        public static final int AUTHORITY_ID_GROUP_TTL = 60 * 60 * 24;

        /**
         * 根据ID保存权限
         */
        public static final String AUTHORITY_ID = "authority.id.";

        /**
         * 根据ID保存权限过期权限
         */
        public static final int AUTHORITY_ID_TTL = 60 * 60 * 24;
        /**
         * 根据ID保存用户
         */
        public static final String USER_ID = "user.id.";

        /**
         * 根据ID保存用户过期时间
         */
        public static final int USER_ID_TTL = 60 * 60 * 24;
        /**
         * 根据ID保存角色
         */
        public static final String ROLE_ID = "role.id.";

        /**
         * 根据ID保存角色过期时间
         */
        public static final int ROLE_ID_TTL = 60 * 60 * 24;
        /**
         * 根据网址保存书籍详情
         */
        public static final String BOOK_DETAIL = "book.detail.";

        /**
         * 根据网址保存书籍详情过期时间
         */
        public static final int BOOK_DETAIL_TTL = 60 * 60 * 12;

        /**
         * 根据网址保存书籍章节内容详情
         */
        public static final String BOOK_CATALOG_CONTENT = "book.catalog.content.";

        /**
         * 根据网址保存书籍章节内容详情过期时间
         */
        public static final int BOOK_CATALOG_CONTENT_TTL = 60 * 60 * 24 * 3;

        /**
         * 根据网址保存书籍搜索结果
         */
        public static final String BOOK_SEARCH_RESULT = "book.search.result.";

        /**
         * 根据网址保存书籍搜索结果过期时间
         */
        public static final int BOOK_SEARCH_RESULT_TTL = 60 * 60 * 24;

        /**
         * 保存系统设置
         */
        public static final String SYSTEM_SETTING = "system.setting";

        /**
         * 保存系统设置过期时间
         */
        public static final int SYSTEM_SETTING_TTL = 60 * 60 * 24;

        /**
         * 根据ID保存更新日志
         */
        public static final String UPDATE_LOG_ID = "update.log.id.";

        /**
         * 根据ID保存更新日志过期时间
         */
        public static final int UPDATE_LOG_ID_TTL = 60 * 60 * 24;

    }

}
