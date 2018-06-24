package no.helgeby.gamedev.testenv;

import no.helgeby.gamedev.engine.Engine;
import no.helgeby.gamedev.engine.Environment;

public class EngineTest {

	public static void main(String[] args) {
		try {
			Environment rootEnvironment = new TestEnvironment();

			Engine engine = new Engine(rootEnvironment);
			engine.start();
			Thread.sleep(10000);
			engine.stop();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
