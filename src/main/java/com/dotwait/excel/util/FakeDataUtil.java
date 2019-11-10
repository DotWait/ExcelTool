package com.dotwait.excel.util;

import com.dotwait.excel.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 假数据工具类
 *
 * @author DotWait
 * @Date 2019-11-08
 */
public class FakeDataUtil {
    /**
     * 生成指定数量的User对象
     *
     * @param num 指定数量
     * @return User对象集合
     */
    public static List<User> generate(int num) {
        TraceUtil traceUtil = new TraceUtil();
        traceUtil.start();
        List<User> users = new ArrayList<>(num);
        String data = "数据数据qwertysjkshdkjHASJKHDJHahshdhAHSHDHAHDHASHH82749878738SJDFSJDLJFD技术的开发健身卡家乐福的收到" +
                "甲方卡就是戒芬兰手机打开解放拉萨雕刻技法卡死了刷卡机的看法接哦文件分类名称v辛苦洛杉矶的魔法立刻哦呜i就法律上扩大解放" +
                "了可就是劳动节放假ask临界点附近阿三酱豆腐的";
        for (int i = 0; i < num; i++) {
            users.add(new User("张三" + i, i, "理发师" + i, new Date(), System.currentTimeMillis(), data));
        }
        traceUtil.stop("生成" + num + "个User对象");
        return users;
    }
}
