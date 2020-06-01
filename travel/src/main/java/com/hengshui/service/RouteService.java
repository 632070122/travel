package com.hengshui.service;

import com.hengshui.domain.PageBean;
import com.hengshui.domain.Route;

public interface RouteService {


    /**
     *
     * @param cid 分类id
     * @param currentPage 当前页码
     * @param pageSize 每页的记录条数
     * @return
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname);

    /**
     * 根据id查询详细页面
     * @param rid
     * @return
     */
    Route findOne(String rid);
}
