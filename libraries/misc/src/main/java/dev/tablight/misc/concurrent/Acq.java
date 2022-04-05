/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.tablight.misc.concurrent;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public final class Acq<T> {
	private static final VarHandle VALUE_VH;
	static {
		try {
			VALUE_VH = MethodHandles.lookup().findVarHandle(Acq.class, "value", Object.class);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new ExceptionInInitializerError("Couldn't get varhandle from value in Acq<T>");
		}
	}

	T value;

	public void setOpaque(T value) {
		VALUE_VH.setOpaque(this, value);
	}

	public void setRelease(T value) {
		VALUE_VH.setRelease(this, value);
	}

	public void setVolatile(T value) {
		VALUE_VH.setRelease(this, value);
	}

	@SuppressWarnings("unchecked")
	public T getOpaque() {
		return (T) VALUE_VH.getOpaque(this);
	}

	@SuppressWarnings("unchecked")
	public T getAcquire() {
		return (T) VALUE_VH.getAcquire(this);
	}

	@SuppressWarnings("unchecked")
	public T getVolatile() {
		return (T) VALUE_VH.getVolatile(this);
	}
}
