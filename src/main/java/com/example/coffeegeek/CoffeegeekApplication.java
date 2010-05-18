package com.example.coffeegeek;

import com.example.coffeegeek.model.Coffee;
import com.example.coffeegeek.repository.CoffeeRepository;
import com.example.coffeegeek.vaadin.RepositoryContainer;
import com.example.coffeegeek.view.coffee.CoffeeForm;
import com.example.coffeegeek.view.coffee.CoffeeList;
import com.google.inject.Inject;
import com.vaadin.Application;
import com.vaadin.data.util.BeanItem;
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

	@Inject
	RepositoryContainer container;

	@Override
	public void init() {

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);

		Button apply = new Button("Apply", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				try {
					coffeeView.commit();
					Coffee c = (Coffee) ((BeanItem) coffeeView
							.getItemDataSource()).getBean();
					// coffeeList.updateTable();
					coffeeRepository.save(c);
					// try to find how to remove this call to
					// fireItemSetChange...
					container.fireItemSetChange();
					getMainWindow().showNotification("Ok !");

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
