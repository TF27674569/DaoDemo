package org.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description：列名
 * <p/>
 * Created by TIAN FENG on 2017/10/23
 * QQ：27674569
 * Email: 27674569@qq.com
 * Version：1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
}
