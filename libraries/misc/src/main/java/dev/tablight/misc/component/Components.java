/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.tablight.misc.component;

import net.kyori.adventure.text.format.TextColor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Some experiment with Tagless Final in java, doesn't actually useful.
 */
public class Components {
	public interface ComponentAlgebra<A> {
		A append(final String s);
		A color(final TextColor color);
		A wrap(final String before, final String after, final List<A> as);
	}

	public interface Component<A> extends Function<ComponentAlgebra<A>, A> {}

	public static final class ComponentDSL {
		public static <A> Component<A> append(final String s) {
			return alg -> alg.append(s);
		}

		public static <A> Component<A> color(final TextColor color) {
			return alg -> alg.color(color);
		}

		@SafeVarargs
		public static <A> Component<A> wrap(final String before, final String after, final Component<A>... as) {
			return alg -> alg.wrap(before, after, Arrays.stream(as).map(comp -> comp.apply(alg)).toList());
		}
	}

	/**
	 *	Some example of this component usage.
	 */
	private <A> A tree(ComponentAlgebra<A> alg) {
		return ComponentDSL.<A>wrap("{", "}",
					ComponentDSL.append("hello,"),
					ComponentDSL.append(" Player!"),
					ComponentDSL.color(TextColor.color(0, 0, 0)),
					ComponentDSL.wrap("[[", "]]",
								ComponentDSL.append("You are on TabLight server!")
							)
				).apply(alg);
	}

}
