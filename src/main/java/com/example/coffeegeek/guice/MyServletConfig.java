package com.example.coffeegeek.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public class MyServletConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {

		ServletModule module = new ServletModuleExtension();

		Injector injector = Guice.createInjector(module);

		return injector;
	}
}