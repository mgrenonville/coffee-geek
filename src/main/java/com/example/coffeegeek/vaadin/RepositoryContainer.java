package com.example.coffeegeek.vaadin;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManagerFactory;
import javax.jdo.metadata.MemberMetadata;
import javax.jdo.metadata.TypeMetadata;

import com.example.coffeegeek.model.Coffee;
import com.example.coffeegeek.repository.CoffeeRepository;
import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import com.google.inject.Inject;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Container.ItemSetChangeNotifier;
import com.vaadin.data.util.BeanItem;

/**
 * This class is an attempt to implement a Container with a wrapped JDO
 * repository.
 * 
 * But it doesn't work because of Vaadin painting strategy.
 * 
 * @author mgrenonville
 * 
 */
public class RepositoryContainer implements Container, ItemSetChangeNotifier {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5739630717144699155L;

	protected static Logger log = Logger.getLogger(RepositoryContainer.class
			.getName());

	protected Class<?> clazz = Coffee.class;

	protected transient Map<Object, Item> itemCache;

	private Map<String, Class<?>> properties = new HashMap<String, Class<?>>();

	protected CoffeeRepository coffeeRepository;

	private LinkedList<ItemSetChangeListener> itemSetChangeListeners;

	@Inject
	public RepositoryContainer(PersistenceManagerFactory pmfInstance,
			CoffeeRepository coffeeRepository) {
		this.coffeeRepository = coffeeRepository;
		TypeMetadata metadata = pmfInstance.getMetadata(clazz
				.getCanonicalName());
		MemberMetadata[] members = metadata.getMembers();
		for (MemberMetadata memberMetadata : members) {
			properties.put(memberMetadata.getName(), memberMetadata.getClass());
		}

		initItemCache();
	}

	private void initItemCache() {
		itemCache = new MapMaker()
				.makeComputingMap(new Function<Object, Item>() {

					public Item apply(Object itemId) {
						log.info("loading entity " + itemId);
						return new BeanItem(
								RepositoryContainer.this.coffeeRepository
										.find((Long) itemId));
					}
				});
	}

	public boolean addContainerProperty(Object propertyId, Class<?> type,
			Object defaultValue) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public Object addItem() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public Item addItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public boolean containsId(Object itemId) {
		return false;
	}

	public Property getContainerProperty(Object itemId, Object propertyId) {
		return getItem(itemId).getItemProperty(propertyId);
	}

	public Collection<?> getContainerPropertyIds() {
		return properties.keySet();
	}

	public Item getItem(Object itemId) {
		return itemCache.get(itemId);
	}

	public Collection<?> getItemIds() {
		return coffeeRepository.findAllIds();
	}

	public Class<?> getType(Object propertyId) {
		return properties.get(propertyId);
	}

	public boolean removeAllItems() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public boolean removeContainerProperty(Object propertyId)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public boolean removeItem(Object itemId)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public int size() {
		int size = getItemIds().size();
		log.info("size() : " + size);
		return size;
	}

	private void writeObject(java.io.ObjectOutputStream stream)
			throws IOException {
		stream.defaultWriteObject();

	}

	private void readObject(java.io.ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		initItemCache();
		fireItemSetChange();
	}

	public void addListener(ItemSetChangeListener listener) {
		log.info("adding Listener");
		if (itemSetChangeListeners == null) {
			itemSetChangeListeners = new LinkedList<ItemSetChangeListener>();
		}
		itemSetChangeListeners.add(listener);
	}

	public void removeListener(ItemSetChangeListener listener) {
		if (itemSetChangeListeners != null) {
			itemSetChangeListeners.remove(listener);
		}
	}

	private void fireItemSetChange() {
		if (itemSetChangeListeners != null) {
			final Container.ItemSetChangeEvent event = new Container.ItemSetChangeEvent() {
				public Container getContainer() {
					return RepositoryContainer.this;
				}
			};
			for (ItemSetChangeListener listener : itemSetChangeListeners) {
				listener.containerItemSetChange(event);
			}
		}
	}

}
