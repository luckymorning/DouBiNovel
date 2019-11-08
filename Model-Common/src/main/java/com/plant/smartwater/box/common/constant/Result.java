package com.plant.smartwater.box.common.constant;

import com.google.common.collect.Maps;

import java.util.Map;

public class Result {

    public static class codes {

        public String message;

        public int code;

        public codes(int code, String message) {
            super();
            this.message = message;
            this.code = code;
        }

        public static final codes SUCCESS = new codes(200, "成功");

        /**
         * 无法解析请求体
         */
        public static final codes UNPROCESSABLE_ENTITY = new codes(422, "无法解析请求体");

        /**
         * 注册： 注册失败
         */
        public static final codes REG_FAIL = new codes(1001, "用户注册失败，请稍后再试");
        /**
         * 登录：用户不存在
         */
        public static final codes LOGIN_USER_NOT_EXISTS = new codes(1002, "用户不存在");

        /**
         * 登录：用户不存在
         */
        public static final codes LOGIN_USER_NOT_AUDIT = new codes(1003, "用户未通过审核，请联系上家");
        /**
         * 登录：登录密码错误
         */
        public static final codes LOGIN_PWD_FAIL = new codes(1004, "登录密码错误");

        /**
         * 登录：用户未登录
         */
        public static final codes UNLOGIN = new codes(1005, "用户未登录");

        /**
         * 找回密码：修改密码失败
         */
        public static final codes MODIFY_PWD_FAIL = new codes(1006, "修改密码失败");
        /**
         * 上传图片错误
         */
        public static final codes IMAGE_FAIL = new codes(1007, "上传图片错误，请选择正确图片");
        
        public static final codes SALE_FAIL = new codes(1008, "销售数据操作失败，请稍后再试");

        public static final codes SAVE_ROLE_FAIL = new codes(2001, "保存角色失败");
        /**
         * 文件夹:添加文件夹失败
         */
        public static final codes SAVE_Folder_FAIL = new codes(3001, "添加文件夹失败");
        /**
         * 文件夹:修改文件夹失败
         */
        public static final codes Edit_Folder_FAIL = new codes(3002, "修改文件夹失败");
        /**
         * 文件夹:删除件夹失败
         */
        public static final codes Delete_Folder_FAIL = new codes(3003, "删除件夹失败");
        /**
         * 文件夹:文件夹名称不能为空
         */
        public static final codes Folder_Name_Null = new codes(3004, "文件夹名称不能为空");
        /**
         * 文件夹:文件夹Id不能为空
         */
        public static final codes Folder_Id_Null = new codes(3005, "文件夹Id不能为空");
        /**
         * 文件夹:未找到对应文件夹
         */
        public static final codes Not_Find_Folder = new codes(3006, "未找到对应文件夹");
        /**
         * 文件:文件夹Id不能为空
         */
        public static final codes File_Id_Null = new codes(3007, "文件Id不能为空");
        /**
         * 文件:粘贴成功
         */
        public static final codes PasteFile_S = new codes(3008, "粘贴成功");
        /**
         * 文件:粘贴失败
         */
        public static final codes PasteFile_F = new codes(3009, "粘贴失败");
        /**
         * 文件:粘贴失败
         */
        public static final codes DELETE_F = new codes(3010, "删除失败");
    }

    private int code = codes.SUCCESS.code;

    private String message;

    private Map<String, Object> data;

    public static Result create(int code, String msg, Map<String, Object> data) {
        Result rst = new Result();
        rst.setCode(code);
        rst.setMessage(msg);
        rst.setData(data);
        return rst;
    }

    public static Result create(int code, String msg) {
        return create(code, msg, null);
    }

    public static Result create(int code) {
        return create(code, null);
    }

    public static Result create(codes code) {
        return create(code.code, code.message);
    }

    public static Result create(codes code, String message) {
        return create(code.code, message);
    }

    public static Result success() {
        return create(codes.SUCCESS.code, null, null);
    }

    public Result set(String key, Object value) {
        if (data == null) {
            data = Maps.newHashMap();
        }
        data.put(key, value);
        return this;
    }

    public Result setAll(Map<String, Object> val) {
        if (data == null) {
            data = Maps.newHashMap();
        }
        data.putAll(val);
        return this;
    }

    /******** getter / setter *******/

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Result setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

}
