package com.hengshui.service.impl;

import com.hengshui.dao.FavoriteDao;
import com.hengshui.dao.impl.FavoriteDaoImpl;
import com.hengshui.domain.Favorite;
import com.hengshui.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {


    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite != null;  //对象不为空就返回,有值就为true
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
