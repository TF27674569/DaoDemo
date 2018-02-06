package org.dao;

import org.dao.support.QuerySupport;

import java.util.List;

/**
 * description：
 * <p/>
 * Created by TIAN FENG on 2017/10/23
 * QQ：27674569
 * Email: 27674569@qq.com
 * Version：1.0
 */

public interface IDaoSupport<T> {

    void init(Class<T> clazz);
    // 插入数据
    long insert(T data);
    // 批量插入  检测性能
    void insert(List<T> datas);

    // 删除
    int delete(String whereClause, String... whereArgs);

    // 更新
    int update(T obj, String whereClause, String... whereArgs);

    // 获取专门查询的支持类
    QuerySupport<T> querySupport();
}
