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
         * 根据网址保存书籍章节内容详情
         */
        public static final int BOOK_CATALOG_CONTENT_TTL = 60 * 60 * 24;

    }

    public static class analysisSource{
        public static final String BI_QU_GE6 = "https://www.xbiquge6.com/";

    }

}
