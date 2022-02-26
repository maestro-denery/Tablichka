package dev.tablight.common.base.dataaddon.annotation.group;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark your class extending {@link dev.tablight.common.base.dataaddon.storeload.StoreLoadController} to use it other infrastructure. 
 * @see dev.tablight.common.base.dataaddon.DataAddonBootstrap
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {
	/**
	 * @return Unique group tag, needs to mark relation between all other infrastructure.
	 */
	String value();
}
