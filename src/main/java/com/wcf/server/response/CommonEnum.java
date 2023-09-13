package com.wcf.server.response;

public enum CommonEnum implements BaseErrorInfoInterface {

    // 数据操作错误定义, 返回的请求全部是200成功的, code 只是在信息里供参考的.
    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400", "请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401", "请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503", "服务器正忙，请稍后再试!"),
    REQUEST_METHOD_SUPPORT_ERROR("40001", "当前请求方法不支持");

    private final String resultCode;
    private final String resultMsg;

    CommonEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}
