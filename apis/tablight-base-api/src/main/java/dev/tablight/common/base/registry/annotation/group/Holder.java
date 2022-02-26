package dev.tablight.common.base.registry.annotation.group;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark your class extending {@link dev.tablight.common.base.registry.holder.TypeHolder} to use it with other infrastructure.
 * @see dev.tablight.common.base.registry.DataAddonBootstrap
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Holder {
	/**
	 * @return Unique group tag, needs to mark relation between all other infrastructure.
	 */
	String value();
}
