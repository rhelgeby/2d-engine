package no.helgeby.gamedev.testenv;

import no.helgeby.gamedev.engine.Environment;
import no.helgeby.gamedev.engine.Vector2D;

public class TestEnvironment extends Environment {

	public TestEnvironment() {
		super();
		addEntity(new Box(this));
		gravity = new Vector2D(0, 0.1f);
	}
}
