package no.helgeby.gamedev.testenv;

import no.helgeby.gamedev.engine.Environment;
import no.helgeby.gamedev.engine.Vector2D;

public class BouncingBoxEnvironment extends Environment {

	public BouncingBoxEnvironment() {
		super();
		for (int i = 0; i < 100; i++) {
			addEntity(new BouncingBox(this));
		}
//		gravity = new Vector2D(0, 0);
		gravity = new Vector2D(0, 0.02f);
//		gravity = new Vector2D(0, 0.5f);
	}
}
