package no.helgeby.gamedev.testenv;

import java.awt.Color;
import java.awt.Graphics;

import no.helgeby.gamedev.engine.Entity;
import no.helgeby.gamedev.engine.Environment;
import no.helgeby.gamedev.engine.Vector2D;

public class BouncingBox extends Entity {

	private boolean surfaceDirty;
	private Color color;
	private int initialX;
	private int initialY;
	private int fontSize;

	public BouncingBox(Environment parent) {
		super(parent);

		final int MIN_SIZE = 5;
		final int MAX_SIZE = 20;

		width = (int) (Math.random() * MAX_SIZE) + MIN_SIZE;
		height = (int) (Math.random() * MAX_SIZE) + MIN_SIZE;

		initialX = (int) (Math.random() * parent.getWidth());
		initialY = (int) (Math.random() * parent.getHeight());
		if (initialX > parent.getWidth() - width) {
			initialX -= width;
		}
		if (initialY > parent.getHeight() - height) {
			initialY -= height;
		}
		position = new Vector2D(initialX, initialY);

		maxSpeed = 30;
		final int maxInitialSpeed = 5;

		// Set a random velocity in any direction.
		float deltaX = (float) (Math.random() * maxInitialSpeed * 3) - maxInitialSpeed;
		float deltaY = (float) (Math.random() * maxInitialSpeed * 2) - maxInitialSpeed;
		velocity = new Vector2D();
		velocity = new Vector2D(deltaX, deltaY);

		// Set a random color.
		int red = (int) (Math.random() * 256);
		int green = (int) (Math.random() * 256);
		int blue = (int) (Math.random() * 256);
		color = new Color(red, green, blue);

		// Mark for update.
		surfaceDirty = true;
	}

	@Override
	public void tick() {
		super.tick();
		Edge edge = touchingEdge();
		if (edge == Edge.NONE) {
			// Touches not always correct because other entities may already
			// have been moved during the same tick.
//			edge = touchingOther();
		}
		switch (edge) {
			case TOP:
				velocity = new Vector2D(velocity.x, velocity.y * -1);
				break;
			case BOTTOM:
				velocity = new Vector2D(velocity.x, velocity.y * -1);
				break;
			case LEFT:
				velocity = new Vector2D(velocity.x * -1, velocity.y);
				break;
			case RIGHT:
				velocity = new Vector2D(velocity.x * -1, velocity.y);
				break;
			default:
				// Don't change velocity.
		}
		//velocity = new Vector2D(velocity.x * 0.999f, velocity.y * 0.999f);
	}

	public void draw(Graphics g) {
		if (!surfaceDirty) {
			return;
		}
		final int velocityLineScale = 10;
		int x = (int)position.x;
		int y = (int)position.y;
		int deltaX = (int)(velocity.x * velocityLineScale);
		int deltaY = (int)(velocity.y * velocityLineScale);
		int lineX = x + width / 2;
		int lineY = y + height / 2;
		int ovalSize = 10;

//		g.setColor(Color.WHITE);
		g.setColor(color);
		g.drawRect(x, y, width, height);

		g.setColor(Color.RED);
		g.drawLine(lineX, lineY, lineX + deltaX, lineY + deltaY);

		g.setColor(Color.YELLOW);
		g.fillOval(lineX + deltaX - (ovalSize / 2), lineY + deltaY - (ovalSize / 2), ovalSize, ovalSize);

//		g.setColor(Color.GREEN);
//		fontSize = (int) g.getFont().getSize2D();
//		printText("id: " + id, 1, g);
//		printText("speed: " + velocity.length(), 1, g);
//		printText("pos: " + position.toFormattedString(), 2, g);
//		printText("vel: " + velocity.toFormattedString(), 3, g);
//		if (touchingEntity != null) {
//			printText("e: " + touchingEntity.getId(), 4, g);
//		}

		// We only need to draw the box once. The buffer is re-used.
		surfaceDirty = true;
	}

	private void printText(String text, int line, Graphics g) {
		g.drawString(text, (int) position.x + 2, (int) position.y + fontSize * line);
	}
}
