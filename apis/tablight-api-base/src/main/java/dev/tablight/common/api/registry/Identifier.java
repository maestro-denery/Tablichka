package dev.tablight.common.api.registry;

import org.jetbrains.annotations.NotNull;

public final class Identifier implements Comparable<Identifier> {
	private final String identifier;
	public Identifier(String identifier) {
		this.identifier = identifier;
	}

	public String getID() {
		return identifier;
	}

	@Override
	public int compareTo(@NotNull Identifier o) {
		return 0;
	}
}
