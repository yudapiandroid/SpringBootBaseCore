package com.suse.yuxin.base;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: yuxin
 * @date: 2019/8/6
 * @description: 默认的 mybatis 的 query dao
 */
@Component
public class MybatisBaseDao {


    @Value("${mapper.namespace:global.mapper}")
    private String nameSpace;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    Class<Map<String,Object>> mapClazz;

    private Class<Map<String,Object>> loadMapClassType() {
        if(mapClazz == null){
            try {
                mapClazz = (Class<Map<String, Object>>) Class.forName(Map.class.getName());
            } catch (ClassNotFoundException e) { }
        }
        return mapClazz;
    }


    public <T> List<T> query(String nameSpace, String id, Map parameter, Class<T> returnType) {
        List<T> result = null;
        SqlSession session = sqlSessionFactory.openSession();
        String callStr = nameSpace + "." + id;
        try {
            result = session.selectList(callStr, parameter);
        }finally {
            session.close();
        }
        return result == null ? new ArrayList() : result;
    }


    public List<Map<String,Object>> query(String nameSpace, String id, Map parameter) {
        return query(nameSpace, id, parameter, loadMapClassType());
    }


    public List<Map<String,Object>> query(String id, Map parameter) {
       return query(nameSpace,id,parameter);
    }

    public <T> List<T> query(String id, Map parameter, Class<T> returnType) {
        return query(nameSpace,id,parameter,returnType);
    }


    public <T> T queryOne(String nameSpace, String id, Map parameter, Class<T> returnClass) {
        T result = null;
        SqlSession session = sqlSessionFactory.openSession();
        String callStr = nameSpace + "." + id;
        try {
            result = session.selectOne(callStr, parameter);
        }finally {
            session.close();
        }
        return result;
    }


    public Map<String,Object> queryOne(String nameSpace, String id, Map parameter) {
        return queryOne(nameSpace, id, parameter, loadMapClassType());
    }

    public Map<String, Object> queryOne(String id, Map parameter) {
        return queryOne(nameSpace, id, parameter, loadMapClassType());
    }

    public int update(String nameSpace, String id, Map parameter) {
        String statement = nameSpace + "." + id;
        Integer result;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = session.update(statement, parameter);
        } finally {
            session.close();
        }
        return result;
    }

    public int update(String id, Map parameter) {
        return update(nameSpace, id, parameter);
    }


    public int delete(String id, Map parameter) {
        return delete(nameSpace, id, parameter);
    }

    public int delete(String nameSpace, String id, Map parameter) {
        String statement = nameSpace + "." + id;
        Integer result;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            result = session.delete(statement, parameter);
        } finally {
            session.close();
        }
        return result;
    }

    public int insert(String namespace, String id, Map parameter) {
        final SqlSession sqlSession = sqlSessionFactory.openSession();
        String statement = namespace + "." + id;
        Integer result;
        try {
            result = sqlSession.insert(statement, parameter);
        } finally {
            sqlSession.close();
        }
        return result;
    }

    public int insert(String id, Map parameter) {
        return insert(nameSpace, id, parameter);
    }

}
