package org.bathbranchringing.emailer.core.repo;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericDAO<E, K extends Serializable> {

	@Autowired
	private SessionFactory sessionFactory;

	protected Class<? extends E> daoType;
	
	@SuppressWarnings("unchecked")
	public GenericDAO() {
		daoType = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
				                        .getActualTypeArguments()[0];
	}
	
    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public K add(E entity) {
        return (K) currentSession().save(entity);
    }

    public void update(E entity) {
        currentSession().saveOrUpdate(entity);
    }

    public void remove(E entity) {
        currentSession().delete(entity);
    }

	@SuppressWarnings("unchecked")
    public E find(K key) {
        return (E) currentSession().get(daoType, key);
    }

	@SuppressWarnings("unchecked")
    public List<E> list() {
        return currentSession().createCriteria(daoType).list();
    }
	
}
