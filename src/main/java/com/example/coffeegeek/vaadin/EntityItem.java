package com.example.coffeegeek.vaadin;

import java.util.Collection;
import java.util.Map;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class EntityItem<T> implements Item {

	private final Map<String, Class<T>> properties;
	private final T wrapped;

	public EntityItem(T wrapped, Map<String, Class<T>> properties) {
		this.wrapped = wrapped;
		this.properties = properties;

	}

	public boolean addItemProperty(Object id, Property property)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public Property getItemProperty(Object id) {
		return new EntityProperty(wrapped, properties.get(id.toString()), id
				.toString());
	}

	public Collection<?> getItemPropertyIds() {
		return properties.keySet();
	}

	public boolean removeItemProperty(Object id)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

}