package com.example.coffeegeek;

import com.example.coffeegeek.repository.CoffeeRepository;
import com.example.coffeegeek.view.coffee.CoffeeForm;
import com.example.coffeegeek.view.coffee.CoffeeList;
import com.google.inject.Inject;
import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
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

	@Inject
	protected CoffeeList coffeeList;

	@Override
	public void init() {

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);

		Button apply = new Button("Apply", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				try {
					coffeeView.commit();
					// coffeeList.updateTable();

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

}
