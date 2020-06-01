package com.hengshui.web.servlet;
import com.hengshui.domain.PageBean;
import com.hengshui.domain.Route;
import com.hengshui.domain.User;
import com.hengshui.service.FavoriteService;
import com.hengshui.service.RouteService;
import com.hengshui.service.impl.FavoriteServiceImpl;
import com.hengshui.service.impl.RouteServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();

    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 分页查询,需要接收三个参数,普通的打开页面,分页
     *      cid 分页的id,具体获取的是哪个数据,北京,衡水
     *      currentPage 当前页码
     *      pageSize 每页显示记录数
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //接受参数,当前页码,每页记录数,cid
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname"); //搜索
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");


        //处理参数cid参数,转换成int类型
        int cid = 0;
        if(cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }

        //当前页码,第几页
        int currentPage = 0;
        if(currentPageStr != null && currentPageStr.length() > 0 ){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }

        //每页的记录条数
        int pageSize = 0;
        if(pageSizeStr !=null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }

        //调用service查询PageBean对象,返回数据
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize,rname);


        //将PageBean序列化Json然后返回
        writeValue(pb, response);
    }

    /**
     * 根据id查询详细信息
     * @param request
     * @param response
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        //接受id
        String rid = request.getParameter("rid");

        //调用service查询route对象
        Route route = routeService.findOne(rid);

        //转换为json写回客户端
        writeValue(route,response);
    }


    /**
     * 判断当前登陆用户是否收藏过该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{



        //获取线路ID
        String rid = request.getParameter("rid");

        //获取当前登录的用户user
        User user = (User)request.getSession().getAttribute("user");

        int uid ;
        //用户没有登陆
        if(user == null){
            uid = 0;
        }else {//用户已经登陆
            uid = user.getUid();
        }

        //调用FavoriteService是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);

        //写回
        writeValue(flag,response);
    }


    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addfavorite(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        String rid = request.getParameter("rid");
        //获取当前登录的用户user
        User user = (User)request.getSession().getAttribute("user");
        //获取当前登录的用户
        int uid ;
        //用户没有登陆
        if(user == null){
            return;
        }else {//用户已经登陆
            uid = user.getUid();
            //调用service进行添加
            favoriteService.add(rid,uid);
        }


    }


}
