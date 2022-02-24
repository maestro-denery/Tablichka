package dev.tablight.common.base.registry;

import dev.tablight.common.base.registry.Registrable;

public final class RegistryException extends RuntimeException {
	private static final String wentWrong = "Something went wrong while registering type: ";
	private static final String plsCheck = ", please check if your class matches Registrable requirements.";

	public RegistryException(Class<? extends Registrable> registrableType, String message, Throwable cause) {
		super(wentWrong + registrableType + plsCheck + " | " + message, cause);
	}

	public RegistryException(Class<? extends Registrable> registrableType, String message) {
		super(wentWrong + registrableType + plsCheck + " | " + message);
	}

	public RegistryException(Class<? extends Registrable> registrableType, Throwable cause) {
		super(wentWrong + registrableType + plsCheck, cause);
	}

	public RegistryException(Class<? extends Registrable> registrableType) {
		super(wentWrong + registrableType + plsCheck);
	}

	public RegistryException(String message) {
		super(message);
	}
}
