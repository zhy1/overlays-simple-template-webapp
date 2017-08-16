package com.xmomen.framework.mybatis.page;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.mapping.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis - 通用分页拦截器
 */
public class PageInterceptor {
    private static final ThreadLocal<Page> localPage = new ThreadLocal<Page>();

    private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList<ResultMapping>(0);

    public static Page get() {
        return localPage.get();
    }

    public static void remove() {
        localPage.remove();
    }

    /**
     * 开始分页
     * @param baseQuery
     */
    public static void startPage(Object baseQuery) {
        Page page = PageHelper.startPage(baseQuery);
        localPage.set(page);
    }


    public static Page endPage() {
        Page page = localPage.get();
        localPage.remove();
        return page;
    }
}