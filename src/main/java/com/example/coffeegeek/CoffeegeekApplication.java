package com.example.coffeegeek;

import java.util.Collection;

import com.example.coffeegeek.model.Coffee;
import com.example.coffeegeek.repository.CoffeeRepository;
import com.example.coffeegeek.view.coffee.CoffeeContainer;
import com.example.coffeegeek.view.coffee.CoffeeForm;
import com.google.inject.Inject;
import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class CoffeegeekApplication extends Application {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private CoffeeRepository coffeeRepository;

	@Inject
	private CoffeeForm coffeeView;

	@Override
	public void init() {

		final Coffee bean = new Coffee();

		coffeeView.setCoffee(bean);

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);

		final Table coffeeList = new Table();
		updateTable(coffeeList);
		Button apply = new Button("Apply", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				try {
					coffeeView.commit();
					coffeeRepository.save(bean);
					updateTable(coffeeList);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		buttons.addComponent(apply);

		Window mainWindow = new Window("Coffeegeek Application");
		mainWindow.addComponent(coffeeList);
		mainWindow.addComponent(coffeeView);
		mainWindow.addComponent(buttons);
		setMainWindow(mainWindow);
	}

	private void updateTable(final Table coffeeList) {
		Collection<Coffee> findAll = coffeeRepository.findAll();
		if (findAll != null && !findAll.isEmpty()) {
			CoffeeContainer newDataSource = new CoffeeContainer(findAll);
			coffeeList.setContainerDataSource(newDataSource);
		}
	}
}
