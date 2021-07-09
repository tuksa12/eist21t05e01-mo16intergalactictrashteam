package de.tum.in.ase.eist.igt;

public final class GalacticGarbagemen {

	private GalacticGarbagemen() {
		// Private constructor because a utility class should not be instantiable.
	}

	public static void main(String[] args) {
		// This is a workaround for a known issue when starting JavaFX applications
		GalacticGarbagemenApplication.startApp(args);
	}
}
