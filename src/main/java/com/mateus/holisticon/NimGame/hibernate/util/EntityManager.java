package com.mateus.holisticon.NimGame.hibernate.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EntityManager {
	
	@Autowired SessionFactory sessionFactory;
	
	private static final String ERROR_STRING = "Failed to ";
	
	/**
	 * Create a new Entity<T>
	 * @param entity
	 * @return
	 */
	public <T> T create(T entity) {
		Session session = null;
		T returnedEntity = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			
			session.persist(entity);
			
			session.getTransaction().commit();
			returnedEntity = entity;
		} catch(HibernateException exception) {
			log.error(ERROR_STRING+exception.getStackTrace()[0].getMethodName(), exception);
			if(session != null && session.getTransaction() != null)
				session.getTransaction().rollback();
		} finally {
			if(session != null)
				session.close();	
		}
		return returnedEntity;
	}
	
	/**
	 * Update Entity<T>
	 * @param entity
	 * @return
	 */
	public <T> T update(T entity) {
		Session session = null;
		T returnedEntity = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
	
			session.merge(entity);
	
			session.getTransaction().commit();
			returnedEntity = entity;
		} catch(HibernateException exception) {
			log.error(ERROR_STRING + exception.getStackTrace()[0].getMethodName(), exception);
			if(session != null && session.getTransaction() != null)
				session.getTransaction().rollback();
		} finally {
			if(session != null)
				session.close();	
		}
		return returnedEntity;
	}	
}
