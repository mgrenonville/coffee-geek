package com.example.coffeegeek.view.coffee;

import java.util.Collection;

import com.example.coffeegeek.model.Coffee;
import com.vaadin.data.util.BeanItemContainer;

public class CoffeeContainer extends BeanItemContainer<Coffee> {

	public CoffeeContainer(Collection<Coffee> collection)
			throws IllegalArgumentException {
		super(collection);
	}

}
