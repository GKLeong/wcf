package com.wcf.server.log;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class WebLog implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    // 操作描述
    private String operation;
    // IP地址
    private String ipAddress;
    // URI
    private String uri;
    // 请求类型
    private String httpMethod;
    // 请求参数
    private Object params;
    // 操作时间
    private Long startTime;
    // 操作结果
    private Boolean success;
    // 请求返回的结果
    private Object result;
    // 消耗时间
    private Integer timeCost;
    // URL
    private String url;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}