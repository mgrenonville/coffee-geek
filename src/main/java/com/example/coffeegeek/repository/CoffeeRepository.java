package com.example.coffeegeek.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.example.coffeegeek.model.Coffee;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CoffeeRepository implements Serializable {

	protected static Logger log = Logger.getLogger(CoffeeRepository.class
			.getName());

	private static transient PersistenceManagerFactory pmfInstance;

	@Inject
	public CoffeeRepository(PersistenceManagerFactory persistenceManagerFactory) {
		pmfInstance = persistenceManagerFactory;

	}

	public void save(Coffee bean) {
		PersistenceManager persistenceManager = pmfInstance
				.getPersistenceManager();
		try {
			persistenceManager.makePersistent(bean);
		} finally {
			persistenceManager.close();
		}
	}

	public Collection<Coffee> findAll() {
		PersistenceManager persistenceManager = pmfInstance
				.getPersistenceManager();
		return (Collection<Coffee>) persistenceManager.newQuery(Coffee.class)
				.execute();
	}

	public Coffee find(Long itemId) {
		PersistenceManager persistenceManager = pmfInstance
				.getPersistenceManager();
		return (Coffee) persistenceManager.getObjectById(Coffee.class, itemId);
	}

	public Collection<Long> findAllIds() {
		PersistenceManager persistenceManager = pmfInstance
				.getPersistenceManager();
		Query newQuery = persistenceManager.newQuery(Coffee.class);
		newQuery.setResult("uuid");
		return (Collection<Long>) newQuery.execute();
	}

	public void saveOrUpdate(Coffee coffee) {
		log.info("saveOrUpdate : " + coffee);
		log.info("state : " + JDOHelper.getObjectState(coffee));
		PersistenceManager persistenceManager = pmfInstance
				.getPersistenceManager();
		try {
			persistenceManager.makePersistent(coffee);
		} finally {
			persistenceManager.close();
		}

	}

}
