package com.example.coffeegeek.guice;

import com.example.coffeegeek.CoffeegeekApplication;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.ServletScopes;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.GAEGuiceApplicationServlet;

public class MyServletConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {

		ServletModule module = new ServletModule() {
			@Override
			protected void configureServlets() {
				serve("/*").with(GAEGuiceApplicationServlet.class);

				bind(Application.class).to(CoffeegeekApplication.class).in(
						ServletScopes.SESSION);
				bindConstant().annotatedWith(Names.named("welcome")).to(
						"This is my first Vaadin/Guice Application");
			}
		};

		Injector injector = Guice.createInjector(module);

		return injector;
	}
}