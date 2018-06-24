package no.helgeby.gamedev.testenv;

import java.awt.Color;
import java.awt.Graphics;

import no.helgeby.gamedev.engine.Entity;
import no.helgeby.gamedev.engine.Environment;
import no.helgeby.gamedev.engine.Vector2D;

public class Box extends Entity {

	private boolean surfaceDirty;

	public Box(Environment parent) {
		super(parent);

		// Start at top center.
		position = new Vector2D();
		velocity = new Vector2D(1f, 0);
		
		final int MIN_SIZE = 5;
		final int MAX_SIZE = 50;
		width = (int) Math.random() * MAX_SIZE + MIN_SIZE;
		height = (int) Math.random() * MAX_SIZE + MIN_SIZE;
		maxSpeed = 50;

		// Mark for update.
		surfaceDirty = true;
	}

	@Override
	public void tick() {
		super.tick();
		if (position.y > parent.getHeight() - height - velocity.y && velocity.y >= 0) {
			velocity = new Vector2D(velocity.x, -velocity.y + 2);
		}

		//System.out.println("Box is falling: " + position + "\tVelocity: " + velocity + "\tSpeed: " + velocity.length());
		//System.out.println("Speed: " + velocity.length());
	}

	public void draw(Graphics g) {
		if (!surfaceDirty) {
			return;
		}
		int red = (int) (Math.random() * 256);
		int green = (int) (Math.random() * 256);
		int blue = (int) (Math.random() * 256);
		g.setColor(new Color(red, green, blue));
		g.drawRect((int) position.x, (int) position.y, width, height);
		//g.drawString("I'm a box.", (int) position.x, (int) position.y);
		surfaceDirty = true;
	}
}
