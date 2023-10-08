package com.wcf.server.utils;

import com.wcf.server.base.response.BizException;
import com.wcf.server.base.response.CommonEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 将指定格式的日期字符串转换为 Date 对象。
     *
     * @param date "yyyy-MM-dd" 格式
     * @return 转换后的 Date 对象
     */
    public static Date dateFormat(String date) {
        if (date == null) return null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(date);
        } catch (ParseException e) {
            // 处理日期解析错误
            throw new BizException(CommonEnum.ERROR_DATE_FORMAT);
        }
    }


    /**
     * 生成账单日期
     *
     * @param date 日期
     * @return 如果 date<=15号，得到当月1号的日期，否则得到下个月1号的日期
     */
    public static Date generateBillDate(Date date) {
        // 创建一个Calendar对象，并将其设置为给定的日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 获取日期的天数
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // 如果日期小于等于15号，将billDate设为当月1号
        if (dayOfMonth <= 15) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        } else {
            // 否则，将billDate设为下个月1号
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }

        // 返回生成的billDate
        return calendar.getTime();
    }

    public static Date getNextMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getMonthFinalDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 计算两个日期的天数差, 小数部分进一
     *
     * @param earlier 较小的日期
     * @param later   较大的日期
     * @return 31号0时-1号0时 = 30天/ 2号 12时 - 1号 8时 = 2天
     */
    public static int calculateDaysDifference(Date earlier, Date later) {
        // 获取两个日期的时间戳（毫秒数）
        long time1 = earlier.getTime();
        long time2 = later.getTime();

        // 计算毫秒数差值
        long timeDiff = time2 - time1;

        // 将毫秒数转换为天数并进一取整
        double daysDiff = (double) timeDiff / (24 * 60 * 60 * 1000);

        return (int) Math.ceil(daysDiff);
    }
}
