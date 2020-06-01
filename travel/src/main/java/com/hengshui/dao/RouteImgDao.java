package com.hengshui.dao;

import com.hengshui.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

    /**
     * 根据线路route的id来查询图片
     * @param rid
     * @return
     */
    List<RouteImg> findByRid(int rid);
}
