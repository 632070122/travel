package com.hengshui.service.impl;
import com.hengshui.dao.FavoriteDao;
import com.hengshui.dao.RouteDao;
import com.hengshui.dao.RouteImgDao;
import com.hengshui.dao.SellerDao;
import com.hengshui.dao.impl.FavoriteDaoImpl;
import com.hengshui.dao.impl.RouteDaoImpl;
import com.hengshui.dao.impl.RouteImgDaoImpl;
import com.hengshui.dao.impl.SellerDaoImpl;
import com.hengshui.domain.PageBean;
import com.hengshui.domain.Route;
import com.hengshui.domain.RouteImg;
import com.hengshui.domain.Seller;
import com.hengshui.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {

         //封装PageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);

        //设置每页显示条数
        pb.setPageSize(pageSize);

        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);

        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合,查询哪里的记录,开始的记录数,每页多少条
        int end = (currentPage -1) * pageSize;
        pb.setList(routeDao.findByPage(cid,end,pageSize,rname));
        //设置总页数 = 总记录数 / 每页显示条数
        int totalPage = 0;
        if( totalCount / pageSize == 0){
            totalPage =  totalCount / pageSize ;
            System.out.println(totalPage);
        }else {
            System.out.println(totalPage);
            double ceil = Math.ceil(totalCount / pageSize);
            totalPage = new Double(ceil).intValue();
        }
        pb.setTotalPage(totalPage);
        System.out.println("总页数是:"+totalPage);
        return pb;
    }

    @Override
    public Route findOne(String rid) {

        //根据id去route表查询信息,不包含图片和商家
        Route route = routeDao.findOne(Integer.parseInt(rid));

        //获取route的id ,用于查询图片的集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());

        //将集合设置到route中
        route.setRouteImgList(routeImgList);

        //根据route的sid查询卖家的信息
        Seller seller = sellerDao.findById(route.getSid());

        //将商家信息添加到route中
        route.setSeller(seller);


        //查收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        //返回
        return route;
    }
}
