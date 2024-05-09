package com.seven.mybatis;

import com.seven.mybatis.vo.Category;
import com.seven.mybatis.enums.TypeEnum;
import com.seven.mybatis.mapper.CategoryMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;

public class Mybatis {
    private SqlSession sqlSession = null;
    @SneakyThrows
    @Test
    public void test() {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        try {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            CategoryMapper categoryMapper = sqlSession.getMapper(CategoryMapper.class);
            Category category = new Category();
            category.setId("002");
            category.setSn(Boolean.TRUE);
            category.setName(TypeEnum.NO);
            categoryMapper.insertCategory(category);
            sqlSession.commit();
            System.out.println(categoryMapper.selectCategoryById("002").toString());
            // System.out.println(categoryMapper.selectCategoryById(1));
            // System.out.println(categoryMapper.pagination(1, 10));
            // Map<String, Object> map = categoryMapper.selectMap();
        }finally {
            sqlSession.close();
        }
    }
}
