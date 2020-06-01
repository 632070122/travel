package com.hengshui.dao;

import com.hengshui.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     * 根据cid查询总记录数
     * @param cid
     * @return
     */
    int findTotalCount(int cid,String rname);


    /*
    根据cid，start，pageSize查询当前页的数据集合
     */

    /**
     * 查询当前页的数据集合
     * @param cid       查询的id
     * @param start     从第几页开始查询
     * @param pageSize  每页的条数
     * @return 当前页的数据集合
     */
    List<Route> findByPage(int cid, int start, int pageSize, String rname);


    /**
     * 根据id查询,不包含图片和商家信息
     * @param rid
     * @return
     */
    Route findOne(int rid);
}
