package cn.edu.jmu.cec.dao;

import cn.edu.jmu.cec.common.Page;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.List;

/**
 * Created by yangjb on 2015/1/25.
 */
public interface BaseDAO<T> {

    void save(T entity);

    void delete(T entity);

    List<T> findAll(Class<T> entityClass);

    void saveOrUpdate(T entity);

    T findById(Class<T> entityClass, int id);

    List<Object> find(String hql, Object... values);

    Object findUnique(String hql, Object... values);

    int findInt(String hql, Object... values);

    int findTotalCount(Class<T> entityClass, String where,
                       Object... values);

    int findTotalCount(Class<T> entityClass);

    List<T> findByProperty(Class<T> entityClass, String propertyName,
                           Object value);

    List<T> findByExample(T entity);

    T merge(T entity);

    void attachClean(T entity);

    Page<T> findByPage(String hql, Integer offset, Integer length,
                       Object... values);

    Query createQuery(Session session, String hql, Object... objects);

    void deleteMany(int[] ids);

    HibernateTemplate getHibernateTemplate();

    void setHibernateTemplate(HibernateTemplate hibernateTemplate);

    public int getTotalCount(final String hql,final Object... values);
}
