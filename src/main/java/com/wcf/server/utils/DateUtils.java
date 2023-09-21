package com.wcf.server.utils;

import com.wcf.server.base.response.BizException;
import com.wcf.server.base.response.CommonEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * 将指定格式的日期字符串转换为 Date 对象。
     *
     * @param date "yyyy-MM-dd" 格式
     * @return 转换后的 Date 对象
     */
    public static Date dateFormat(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(date);
        } catch (ParseException e) {
            // 处理日期解析错误
            throw new BizException(CommonEnum.ERROR_DATE_FORMAT);
        }
    }
}
