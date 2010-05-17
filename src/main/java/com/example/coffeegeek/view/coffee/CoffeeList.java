package com.example.coffeegeek.view.coffee;

import java.util.Arrays;
import java.util.logging.Logger;

import com.example.coffeegeek.vaadin.RepositoryContainer;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

@Singleton
public class CoffeeList extends Table {

	protected static Logger log = Logger.getLogger(CoffeeList.class.getName());

	private final CoffeeForm coffeeForm;

	@Inject
	public CoffeeList(CoffeeForm coffeeForm, RepositoryContainer container) {
		this.coffeeForm = coffeeForm;

		setSelectable(true);
		setImmediate(true);
		setContainerDataSource(container);
		addListener(new Property.ValueChangeListener() {

			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				log.info("value change : " + event);
				Property property = event.getProperty();
				if (property == CoffeeList.this) {
					Item item = CoffeeList.this.getItem(CoffeeList.this
							.getValue());

					log.info("item  : " + item);
					if (item != CoffeeList.this.coffeeForm.getItemDataSource()) {
						log.info("Update form : " + item);
						CoffeeList.this.coffeeForm.setItemDataSource(item);
						CoffeeList.this.coffeeForm
								.setVisibleItemProperties(Arrays
										.asList(new String[] { "name",
												"country", "date", "degree" }));
					}
				}
			}
		});
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException,
			ConversionException {
		log.info("setValue");
		super.setValue(newValue);
	}
	// public void updateTable() {
	// Collection<Coffee> findAll = coffeeRepository.findAll();
	// if (findAll != null && !findAll.isEmpty()) {
	// CoffeeContainer newDataSource = new CoffeeContainer(findAll);
	// this.setContainerDataSource(newDataSource);
	// }
	// }

}
