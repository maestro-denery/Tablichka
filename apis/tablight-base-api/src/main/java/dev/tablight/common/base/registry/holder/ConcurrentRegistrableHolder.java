package dev.tablight.common.base.registry.holder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import dev.tablight.common.base.registry.Registrable;
import dev.tablight.common.base.registry.RegistryException;
import dev.tablight.common.base.registry.TypeRegistry;

public class ConcurrentRegistrableHolder extends RegistrableHolder {
	protected boolean running = false;
	protected Disruptor<HolderEvent> disruptor;
	protected final Collection<TypeRegistry> typeRegistries = new ArrayList<>();
	protected final Multimap<Class<? extends Registrable>, Registrable> instances =
			Multimaps.newMultimap(new ConcurrentHashMap<>(), ConcurrentHashMap::newKeySet);

	@Override
	public <T extends Registrable> void hold(T instance) {
		checkRegistered(instance.getClass());
		instances.put(instance.getClass(), instance);
		checkRunning();
		publishEvent(instance.getClass());
	}

	@Override
	public void addTypeRegistry(TypeRegistry typeRegistry) {
		typeRegistries.add(typeRegistry);
	}

	@Override
	public <T extends Registrable> void release(T instance) {
		checkRegistered(instance.getClass());
		instances.remove(instance.getClass(), instance);
		checkRunning();
		publishEvent(instance.getClass());
	}

	@Override
	public <T extends Registrable> void release(Class<T> registrableType) {
		checkRegistered(registrableType);
		instances.removeAll(registrableType);
		checkRunning();
		publishEvent(registrableType);
	}

	@Override
	public Collection<TypeRegistry> getTypeRegistries() {
		return typeRegistries;
	}

	@Override
	public void release(String identifier) {
		release(getClassByID(identifier));
	}

	@Override
	public void clearHeld() {
		instances.clear();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Registrable> Collection<T> getHeld(Class<T> registrableType) {
		return (Collection<T>) instances.get(registrableType);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Registrable> Collection<T> getHeld(String identifier) {
		return (Collection<T>) instances.get(getClassByID(identifier));
	}

	@Override
	public void handle(EventHandler<? super HolderEvent> handler) {
		checkDisruptorNull();
		disruptor.handleEventsWith(handler);
	}

	@Override
	public void forceStart() {
		running = true;
		checkDisruptorNull();
		disruptor.start();
	}

	@Override
	public <T extends Registrable> boolean containsInstance(T registrable) {
		return instances.containsValue(registrable);
	}

	// Implementation details methods, they're not specified in RegistrableHolder.
	
	public boolean isRunning() {
		return running;
	}
	
	public void shutdownDisruptor() {
		disruptor.shutdown();
	}

	public Multimap<Class<? extends Registrable>, Registrable> getInternalMap() {
		return instances;
	}

	private <T extends Registrable> void checkRegistered(Class<T> tClass) {
		if (!typeRegistries.stream().anyMatch(typeRegistry -> typeRegistry.isRegistered(tClass))) {
			throw new RegistryException(tClass, "Can't hold registrable because it isn't registered");
		};
	}

	@SuppressWarnings("all")
	private <T extends Registrable> Class<T> getClassByID(String id) {
		return (Class<T>) typeRegistries.stream()
				.map(typeRegistry -> typeRegistry.getRegistrableType(id)).findFirst().orElseThrow(() -> new RegistryException("There is no Registrables defined with given Id"));
	}

	private <T extends Registrable> void publishEvent(Class<T> tClass) {
		disruptor.publishEvent((event, sequence) -> {
			event.setRegistrables(instances.get(tClass));
			event.setRegistrableType(tClass);
		});
	}

	private void checkRunning() {
		if (!running) forceStart();
	}

	private void checkDisruptorNull() {
		if (disruptor == null) disruptor = new Disruptor<>(
				HolderEvent::new,
				1024,
				DaemonThreadFactory.INSTANCE,
				ProducerType.MULTI,
				new BusySpinWaitStrategy()
		);
	}
}
