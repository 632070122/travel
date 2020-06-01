package com.hengshui.service.impl;
import com.hengshui.dao.CategoryDao;
import com.hengshui.dao.impl.CategoryDaoImpl;
import com.hengshui.domain.Category;
import com.hengshui.service.CategoryService;
import com.hengshui.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {


    private CategoryDao categoryDao = new CategoryDaoImpl();
	
	private int age;
	
    @Override
    public List<Category> findAll() {
        //1.获取jedis,操作redis
        Jedis jedis = JedisUtil.getJedis();
        //2.从redis中获取数据查询sortedset中分数(id)和值(cname)
        Set<Tuple> categoryRedis = jedis.zrangeWithScores("category", 0, -1);

        //3.定义一个Category的List集合,用于存储数据,最终返回
        List<Category> categoryList = null;
        //4.判断redis中有没有数据
        if(categoryRedis == null || categoryRedis.size() == 0){
            System.out.println("从数据库中查询......");
            //5.如果没有数据,就从数据库查询,赋值给集合
            categoryList = categoryDao.findAll();
            //6.遍历该集合
            for (int i = 0; i < categoryList.size(); i++) {
                //7.存储到redis中
                jedis.zadd("category",categoryList.get(i).getCid(),categoryList.get(i).getCname());
            }
            //8.如果redis中有数据
        }else {
            System.out.println("从redis中查询......");
            //9.创建ArrayList集合,准备把set集合数据存储到list中
            categoryList = new ArrayList<>();
            //9.遍历categoryRedis的set集合
            for (Tuple tuple : categoryRedis) {
                //创建10.Category对象
                Category category = new Category();
                category.setCid((int)tuple.getScore());
                category.setCname(tuple.getElement());
                categoryList.add(category);
            }
        }

        return categoryList;
    }
}
