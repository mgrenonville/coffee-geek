package com.example.coffeegeek.vaadin;

import java.lang.reflect.Field;

import com.vaadin.data.Property;

/**
 * This class is an attempt to implement the Property with a wrapped JDO entity.
 * 
 * But it doesn't work
 * 
 * @author mgrenonville
 * 
 */
public class EntityProperty implements Property {

	private Object beanValue;
	private Class<?> clazzValue;
	private boolean readOnly = false;
	private final String propertyId;

	public EntityProperty(Object bean, Class<?> clazzValue, String propertyId) {
		this.beanValue = bean;
		this.clazzValue = clazzValue;
		this.propertyId = propertyId;

	}

	public Class<?> getType() {
		return clazzValue;
	}

	public Object getValue() {
		Field[] declaredFields = beanValue.getClass().getDeclaredFields();
		for (Field field : declaredFields) {

			if (field.getName().equals(propertyId)) {
				try {
					field.setAccessible(true);
					return field.get(beanValue);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return null;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean newStatus) {
		this.readOnly = newStatus;
	}

	public void setValue(Object newValue) throws ReadOnlyException,
			ConversionException {
		if (readOnly) {
			throw new ReadOnlyException();
		}
		if (newValue != null) {
			if (newValue.getClass().isAssignableFrom(clazzValue)) {
				Field[] declaredFields = beanValue.getClass()
						.getDeclaredFields();
				for (Field field : declaredFields) {
					if (field.getName().equals(propertyId)) {
						try {
							field.setAccessible(true);
							field.set(beanValue, newValue);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			}
		}
	}

	public String toString() {
		Object v = getValue();
		if (v != null) {
			return v.toString();
		} else {
			return null;
		}
	}
}
