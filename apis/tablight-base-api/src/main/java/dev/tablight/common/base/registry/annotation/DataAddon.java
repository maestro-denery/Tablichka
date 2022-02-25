package dev.tablight.common.base.registry.annotation;

import dev.tablight.common.base.registry.storeload.StoreLoadLookup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataAddon {
	String identifier();
	String groupTag();
	Class<?> nativeClass();
	Class<? extends StoreLoadLookup<?, ?>> lookup();
}
