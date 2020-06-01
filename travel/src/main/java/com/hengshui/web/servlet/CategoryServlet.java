package com.hengshui.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hengshui.domain.Category;
import com.hengshui.service.CategoryService;
import com.hengshui.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService categoryService = new CategoryServiceImpl();

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categoryList = categoryService.findAll();
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),categoryList);
      /*  writeValue(categoryList,response);*/
    }

}
