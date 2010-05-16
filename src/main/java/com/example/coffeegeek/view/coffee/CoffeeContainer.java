package com.example.coffeegeek.view.coffee;

import java.util.Collection;

import com.example.coffeegeek.model.Coffee;
import com.example.coffeegeek.repository.CoffeeRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vaadin.data.util.BeanItemContainer;

@Singleton
public class CoffeeContainer extends BeanItemContainer<Coffee> {

	private final CoffeeRepository repository;

	@Inject
	public CoffeeContainer(CoffeeRepository repository)
			throws IllegalArgumentException {
		super(Coffee.class);
		this.repository = repository;

		Collection<Coffee> findAll = repository.findAll();
		for (Coffee coffee : findAll) {
			this.addBean(coffee);
		}

	}

}
