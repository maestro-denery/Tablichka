package dev.tablight.common.base.registry;

public final class RegistryException extends RuntimeException {
	private static final String wentWrong = "Something went wrong while registering type: ";
	private static final String plsCheck = ", please check if your class matches Registrable requirements.";

	public RegistryException(Class<?> registrableType, String message, Throwable cause) {
		super(wentWrong + registrableType + plsCheck + " | " + message, cause);
	}

	public RegistryException(Class<?> registrableType, String message) {
		super(wentWrong + registrableType + plsCheck + " | " + message);
	}

	public RegistryException(Class<?> registrableType, Throwable cause) {
		super(wentWrong + registrableType + plsCheck, cause);
	}

	public RegistryException(Class<?> registrableType) {
		super(wentWrong + registrableType + plsCheck);
	}

	public RegistryException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegistryException(String message) {
		super(message);
	}
}
