package com.hengshui.dao;

import com.hengshui.domain.Seller;

public interface SellerDao {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Seller findById(int id);
}
