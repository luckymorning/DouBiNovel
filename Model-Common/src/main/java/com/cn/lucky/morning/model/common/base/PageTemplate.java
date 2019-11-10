package com.cn.lucky.morning.model.common.base;

import com.google.common.base.Objects;
import com.cn.lucky.morning.model.common.log.Logs;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class PageTemplate<T> {
    
    private static final Logger log = Logs.get();
    
    public static <T> PageTemplate<T> create(List<T> list, int curr, int totalSize) {
        return create(list, curr, totalSize, null);
    }
    
    public static <T> PageTemplate<T> create(List<T> list, int curr, int totalSize, BaseQuery baseQuery) {
        PageTemplate<T> page = create();
        
        page.setList(list);
        page.setCurrPage(curr);
        page.setTotalSize(totalSize);
        page.setBaseQuery(baseQuery);
        if(baseQuery != null){
            page.setPageSize(baseQuery.getSize());
        }
        
        return page;
    }
    
    public static <T> PageTemplate<T> create(){
        return new PageTemplate<T>();
    }
    
    public static <T> PageTemplate<T> create(Object example, Object mapper) {
        return create(example, mapper, null);
    }

    public static <T> PageTemplate<T> create(Object example, Object mapper, BaseQuery baseQuery) {
        PageTemplate<T> pageTmpl = new PageTemplate<T>();
        try {
            List<T> list = selectList(example, mapper);
            int count = selectCount(example, mapper);
            int pageSize = getPageSize(example);
            int currPage = getCurrPage(example) / pageSize;
            
            pageTmpl.setList(list);
            pageTmpl.setTotalSize(count);
            pageTmpl.setCurrPage(currPage);
            pageTmpl.setPageSize(pageSize);
            
            if (baseQuery != null) {
                pageTmpl.setBaseQuery(baseQuery);
            }
        } catch (Exception e) {
            log.error("创建PageTemplate对象失败", e);
        }
        return pageTmpl;
    }
    
    private static int getPageSize(Object example) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method mtd = getMethod("getLimitEnd", example);
        return Integer.parseInt(mtd.invoke(example) + "");
    }

    private static int getCurrPage(Object example) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method mtd = getMethod("getLimitStart", example);
        return Integer.parseInt(mtd.invoke(example) + "");
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> selectList(Object example, Object mapper) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method mtd = getMethod("selectByExample", mapper);
        return (List<T>) mtd.invoke(mapper, example);
    }

    private static int selectCount(Object example, Object mapper) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method getStartMtd = getMethod("getLimitStart", example);
        Method getEndMtd = getMethod("getLimitEnd", example);
         Integer startRow = (Integer) getStartMtd.invoke(example);
        Integer endRow = (Integer) getEndMtd.invoke(example);
        
        Method getCountMtd = getMethod("countByExample", mapper);
        Long count = (Long) getCountMtd.invoke(mapper, example);
        
        Method setStartMtd = getMethod("setLimitStart", example);
        Method setEndMtd = getMethod("setLimitEnd", example);
        setStartMtd.invoke(example, startRow);
        setEndMtd.invoke(example, endRow);
        return count.intValue();
    }
    
    private static Method getMethod(String methodName, Object mapper) {
        Method[] mtds = mapper.getClass().getDeclaredMethods();
        for (Method mtd : mtds) {
            if (Objects.equal(mtd.getName(), methodName)) {
                return mtd;
            }
        }
        return null;
    }

    private BaseQuery baseQuery;

    /**
     * 当前页
     */
    private int currPage;

    /**
     * 每页数量
     */
    private int pageSize = 20;

    /**
     * 总数量
     */
    private int totalSize;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 数据列表
     */
    private List<T> list;

    public BaseQuery getBaseQuery() {
        return baseQuery;
    }

    public void setBaseQuery(BaseQuery baseQuery) {
        this.baseQuery = baseQuery;
    }

    public int getCurrPage() {
        return currPage + 1;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        totalPage = this.totalSize / this.pageSize + (this.totalSize % this.pageSize > 0 ? 1 : 0);
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
