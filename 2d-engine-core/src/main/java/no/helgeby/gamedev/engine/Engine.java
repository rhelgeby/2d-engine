package no.helgeby.gamedev.engine;

import java.util.Timer;
import java.util.TimerTask;

import no.helgeby.gamedev.testenv.EngineEventListener;

public class Engine {

	private static final int DEFAULT_TICK_RATE = 30;
	private Timer gameTimer;
	private TimerTask gameRunner;
	private boolean running;
	/** Ticks per second. */
	private long frameCount;
	private int tickRate;
	private long frameRate;
	private Vector2D position;
	private Environment rootEnvironment;
	private EngineEventListener eventListener;

	public Engine(Environment rootEnvironment) {
		tickRate = DEFAULT_TICK_RATE;
		gameTimer = new Timer("gameTimer");
		gameRunner = new Runner();
		position = new Vector2D();
		this.rootEnvironment = rootEnvironment;
		rootEnvironment.position = position;
	}

	public void start() {
		running = true;
		frameRate = 1000 / tickRate;
		gameTimer.scheduleAtFixedRate(gameRunner, 0, frameRate);
	}

	public void stop() {
		running = false;
		gameTimer.cancel();
	}

	private class Runner extends TimerTask {

		@Override
		public void run() {
			long frameStart = System.currentTimeMillis();
			frameCount++;
			//System.out.println("Frame: " + frameCount);
			rootEnvironment.tick();
			rootEnvironment.draw();
			if (eventListener != null) {
				eventListener.onPostDraw(Engine.this);
			}
			long frameEnd = System.currentTimeMillis();
			long frameTime = frameEnd - frameStart;
			if (frameTime > frameRate) {
				System.out.println("Frame " + frameCount + " took too long: " + frameTime + " ms");
			}
		}
	}

	public Environment getEnvironment() {
		return rootEnvironment;
	}
	
	public void setEventListener(EngineEventListener eventListener) {
		this.eventListener = eventListener;
	}

	public void resize(int width, int height) {
		rootEnvironment.resize(width, height);
//		System.out.println("Resizing to " + width + " x " + height);
	}

	public long getFrameCount() {
		return frameCount;
	}

	public void initPosition(int x, int y) {
		position = new Vector2D(x, y);
		rootEnvironment.position = position;
	}

	public void move(int x, int y) {
		rootEnvironment.move(x, y);
		position = new Vector2D(x, y);
		rootEnvironment.position = position;
//		System.out.println("Moving to " + x + ", " + y);
	}
}
