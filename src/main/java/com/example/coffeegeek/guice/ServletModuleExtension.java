package com.example.coffeegeek.guice;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import com.example.coffeegeek.CoffeegeekApplication;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.ServletScopes;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.GAEGuiceApplicationServlet;

public class ServletModuleExtension extends ServletModule {
	@Override
	protected void configureServlets() {
		serve("/*").with(GAEGuiceApplicationServlet.class);

		bind(Application.class).to(CoffeegeekApplication.class).in(
				ServletScopes.SESSION);
		bindConstant().annotatedWith(Names.named("welcome")).to(
				"This is my first Vaadin/Guice Application");

	}

	@Provides
	@Singleton
	PersistenceManagerFactory getPersistanceManagerFactory() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional");
	}
}