package com.hengshui.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hengshui.domain.ResultInfo;
import com.hengshui.domain.User;
import com.hengshui.service.UserService;
import com.hengshui.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    //注册
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parameter = request.getParameter("check");

        String attribute = (String)request.getSession().getAttribute("CHECKCODE_SERVER");

        request.getSession().removeAttribute("CHECKCODE_SERVER");

        if(attribute==null && !parameter.equalsIgnoreCase(attribute)){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            response.setContentType("application/json;charset=utf-8");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);


            response.getWriter().write(json);
            return;
        }


        Map<String, String[]> map = request.getParameterMap();

        User user = new User();

        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        Boolean flag = userService.register(user);

        ResultInfo info = new ResultInfo();
        if(flag){
            info.setFlag(true);
        }else {
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }

    //登陆
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();
        String check = request.getParameter("check");
        String attribute = (String)request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");

        //1.获取user是否存在
        String username = request.getParameter("username");

        User user = userService.findById(username);
        String password = request.getParameter("password");



        if(user == null){ //2.如果不存在提示账号不存在
            info.setFlag(false);
            info.setErrorMsg("账号不存在");
        }else if (user != null && "N".equals(user.getStatus())){ //3.如果没有激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活");
        }else if(!check.equalsIgnoreCase(attribute)){  //4.判断验证码是否正确
            info.setFlag(false);
            info.setErrorMsg("验证码不正确");
        }else if(user != null && !password.equals(user.getPassword())){ //5.判断账号和密码是否正确
            info.setFlag(false);
            info.setErrorMsg("密码错误");
        }else if(password.equals(user.getPassword()) && check.equalsIgnoreCase(attribute)){
            request.getSession().setAttribute("user",user);
            System.out.println("将user对象存储到session域中"+user);
            info.setFlag(true);
        }else {
            info.setFlag(false);
            info.setErrorMsg("未知错误,请联系管理员");
        }


        //7.进行相应
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);
    }

    //查询一个
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        User user = (User)request.getSession().getAttribute("user");
        System.out.println("获取到的user对象是"+user);
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);*/
        writeValue(user,response);

    }

    //退出
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    //激活
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code != null) {
            boolean flag = userService.active(code);

            String msg = null;
            if (flag) {
                msg = "激活成功,请<a href='login.html'>登陆</a>";
            } else {
                msg = "激活失败,请重新激活,或联系管理员";
            }

            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }

    }

}
