package org.dao.support;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.dao.annotation.Column;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description：
 * <p/>
 * Created by TIAN FENG on 2017/10/23
 * QQ：27674569
 * Email: 27674569@qq.com
 * Version：1.0
 */

public class QuerySupport<T> {
    private SQLiteDatabase mSQLiteDatabase;
    private Class<T> mClazz;

    // 查询的列
    private String[] mQueryColumns;
    // 查询的条件
    private String mQuerySelection;
    // 查询的参数
    private String[] mQuerySelectionArgs;
    // 查询分组
    private String mQueryGroupBy;
    // 查询对结果集进行过滤
    private String mQueryHaving;
    // 查询排序
    private String mQueryOrderBy;
    // 查询可用于分页
    private String mQueryLimit;

    public QuerySupport(SQLiteDatabase sqLiteDatabase, Class<T> clazz) {
        this.mSQLiteDatabase = sqLiteDatabase;
        this.mClazz = clazz;
    }

    public QuerySupport columns(String... columns) {
        this.mQueryColumns = columns;
        return this;
    }

    public QuerySupport selectionArgs(String... selectionArgs) {
        this.mQuerySelectionArgs = selectionArgs;
        return this;
    }

    public QuerySupport having(String having) {
        this.mQueryHaving = having;
        return this;
    }

    public QuerySupport orderBy(String orderBy) {
        this.mQueryOrderBy = orderBy;
        return this;
    }

    public QuerySupport limit(String limit) {
        this.mQueryLimit = limit;
        return this;
    }

    public QuerySupport groupBy(String groupBy) {
        this.mQueryGroupBy = groupBy;
        return this;
    }

    public QuerySupport selection(String selection) {
        this.mQuerySelection = selection;
        return this;
    }

    /**
     * 查询
     */
    public List<T> query() {
        Cursor cursor = mSQLiteDatabase.query(DaoUtil.getTabName(mClazz), mQueryColumns, mQuerySelection,
                mQuerySelectionArgs, mQueryGroupBy, mQueryHaving, mQueryOrderBy, mQueryLimit);
        clearQueryParams();
        return cursorToList(cursor);
    }

    /**
     * 查询全部
     */
    public List<T> queryAll() {
        Cursor cursor = mSQLiteDatabase.query(DaoUtil.getTabName(mClazz), null, null, null, null, null, null);
        return cursorToList(cursor);
    }

    /**
     * 清空参数
     */
    private void clearQueryParams() {
        mQueryColumns = null;
        mQuerySelection = null;
        mQuerySelectionArgs = null;
        mQueryGroupBy = null;
        mQueryHaving = null;
        mQueryOrderBy = null;
        mQueryLimit = null;
    }


    /**
     * 通过Cursor封装成查找对象
     *
     * @return 对象集合列表
     */
    private List<T> cursorToList(Cursor cursor) {
        List<T> datas = new ArrayList<>();

        // 游标移动到首位
        if (cursor != null && cursor.moveToFirst()) {
            do {
                T instance = null;
                try {
                    // 首先通过反射创建一个实例
                    instance = mClazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new IllegalStateException("请保证类" + mClazz.getName() + "中存在一个空的构造函数。");
                }

                try {
                    Field[] fields = mClazz.getDeclaredFields();
                    // 遍历属性
                    for (Field field : fields) {
                        field.setAccessible(true);
                        // 判断是否有注解
                        Column column = field.getAnnotation(Column.class);

                        if (column == null) {
                            continue;
                        }

                        // 获取属性名称
                        String name = DaoUtil.getColumnName(field);
                        // 获取角标
                        int index = cursor.getColumnIndex(name);
                        if (index == -1) {
                            continue;
                        }

                        // 通过反射获取 游标的方法
                        Method cursorMethod = cursorMethod(field.getType());

                        // 如果存在这个函数 实现这个方法
                        if (cursorMethod != null) {
                            Object value = cursorMethod.invoke(cursor, index);
                            if (value == null) {
                                continue;
                            }
                            // 处理一些特殊的部分
                            if (field.getType() == boolean.class || field.getType() == Boolean.class) {//boolean类型
                                if ("0".equals(String.valueOf(value))) {
                                    value = false;
                                } else if ("1".equals(String.valueOf(value))) {
                                    value = true;
                                }
                            } else if (field.getType() == char.class || field.getType() == Character.class) {//char类型
                                value = ((String) value).charAt(0);
                            } else if (field.getType() == Date.class) {// 日期毫秒值
                                long date = (Long) value;
                                if (date <= 0) {
                                    value = null;
                                } else {
                                    value = new Date(date);
                                }
                            }
                            field.set(instance, value);
                        }
                        datas.add(instance);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return datas;
    }

    /**
     * 获取游标的方法
     */
    private Method cursorMethod(Class<?> type) throws Exception {
        String methodName = getColumnMethodName(type);
        Method method = Cursor.class.getMethod(methodName, int.class);
        return method;
    }

    private String getColumnMethodName(Class<?> fieldType) {
        String typeName;
        if (fieldType.isPrimitive()) {
            typeName = DaoUtil.capitalize(fieldType.getName());
        } else {
            typeName = fieldType.getSimpleName();
        }
        String methodName = "get" + typeName;
        if ("getBoolean".equals(methodName)) {
            methodName = "getInt";
        } else if ("getChar".equals(methodName) || "getCharacter".equals(methodName)) {
            methodName = "getString";
        } else if ("getDate".equals(methodName)) {
            methodName = "getLong";
        } else if ("getInteger".equals(methodName)) {
            methodName = "getInt";
        }
        return methodName;
    }
}
