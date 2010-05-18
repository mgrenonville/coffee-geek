package com.example.coffeegeek.vaadin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class EntityItem<T> implements Item {

	private Map<String, Class<T>> propertiesType;
	private T wrapped;
	private Map<Object, Property> properties = new HashMap<Object, Property>();

	public EntityItem(T cwrapped, Map<String, Class<T>> cpropertiesType) {
		this.wrapped = cwrapped;
		this.propertiesType = cpropertiesType;

	}

	public boolean addItemProperty(Object id, Property property)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public Property getItemProperty(Object id) {
		return apply(id);
	}

	public Property apply(Object id) {
		if (properties.get(id) == null) {
			properties.put(id, new EntityProperty(wrapped, propertiesType
					.get(id.toString()), id.toString()));
		}
		return properties.get(id);
	}

	public Collection<?> getItemPropertyIds() {
		return propertiesType.keySet();
	}

	public boolean removeItemProperty(Object id)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

}