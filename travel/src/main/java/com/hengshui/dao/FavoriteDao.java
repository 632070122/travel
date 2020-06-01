package com.hengshui.dao;

import com.hengshui.domain.Favorite;

public interface FavoriteDao {

    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    Favorite findByRidAndUid(int rid, int uid);

    int findCountByRid(int rid);

    void add(int i, int uid);
}
