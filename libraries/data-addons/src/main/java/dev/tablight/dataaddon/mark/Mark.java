/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.tablight.dataaddon.mark;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Mark<T, N> {

	Predicate<T> matches();

	T convert(N n);

	default Consumer<T> mark() {
		return type -> matches().test(type);
	}

	static <T, N> Mark<T, N> create(Function<? super N, ? extends T> convert, Predicate<T> matches) {
		return new Mark<>() {
			@Override
			public Predicate<T> matches() {
				return matches;
			}

			@Override
			public T convert(N n) {
				return convert.apply(n);
			}
		};
	}
}
