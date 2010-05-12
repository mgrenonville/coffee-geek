package com.example.coffeegeek.repository;

import java.io.Serializable;
import java.util.Collection;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.example.coffeegeek.model.Coffee;
import com.google.inject.Singleton;

@Singleton
public class CoffeeRepository implements Serializable {
	private static transient final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

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

}
