package no.helgeby.gamedev.engine;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Environment {
	public static final int MIN_WIDTH = 5;
	public static final int MIN_HEIGHT = 5;
	public static final int DEFAULT_WIDTH = 1920;
	public static final int DEFAULT_HEIGHT = 1080;

	protected final boolean isRoot;

	protected Vector2D position;

	protected int width;
	protected int height;

	protected Vector2D gravity;

	protected BufferedImage image;
	protected Graphics surface;

	protected long idSequence;

	// Sorted by z-order. Move entities up or down to alter z-order.
	protected List<Entity> entities;

	protected final Environment parent;

	public Environment() {
		parent = null;
		isRoot = true;
		idSequence = 0;
		init();
	}

	public Environment(Environment parent) {
		this.parent = parent;
		isRoot = false;
		this.idSequence = parent.idSequence;
		init();
	}

	private void init() {
		entities = new ArrayList<>();

		width = DEFAULT_WIDTH;
		height = DEFAULT_HEIGHT;

		gravity = new Vector2D(0, 0.1f);

		initImageBuffer();
	}

	private void initImageBuffer() {
		image = new BufferedImage(width, height, TYPE_INT_ARGB);
		surface = image.createGraphics();
	}

	public void tick() {
		for (Entity e : entities) {
			e.tick();
		}
	}

	public void draw() {
		BufferedImage buffer = new BufferedImage(width, height, TYPE_INT_ARGB);
		Graphics2D graphics = buffer.createGraphics();
		for (Entity e : entities) {
			e.draw(graphics);
		}
		//surface.drawImage(buffer, 0, 0, null);
		surface.drawImage(buffer, 0, 0, Color.BLACK, null);
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
		entity.id = newEntityId();
	}

	public long newEntityId() {
		// Pre-increment so first ID is 1. The current sequence value remains
		// at the last ID.
		return ++idSequence;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public static Environment createRootEnvironment() {
		return new Environment();
	}

	public Image getImage() {
		return image;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void resize(int width, int height) {
		if (width < MIN_WIDTH || height < MIN_HEIGHT) {
			return;
		}
		this.width = width;
		this.height = height;
		initImageBuffer();
		moveEntitiesOutsideContainer();
	}

	public void move(int x, int y) {
		int deltaX = x - (int) position.x;
		int deltaY = y - (int) position.y;
		Vector2D distance = new Vector2D(deltaX * -1, deltaY * -1);
		for (Entity e : entities) {
			e.move(distance);
		}
		// Move outside entities back into the container.
		moveEntitiesOutsideContainer();
	}

	private void moveEntitiesOutsideContainer() {
		for (Entity e : entities) {
			boolean changed = false;
			float x = e.position.x;
			float y = e.position.y;

			if (x > width) {
				x = width - e.width - MIN_WIDTH;
				changed = true;
			}
			if (x < 0) {
				x = 0;
				changed = true;
			}
			if (y > height) {
				y = height - e.height - MIN_HEIGHT;
				changed = true;
			}
			if (y < 0) {
				y = 0;
				changed = true;
			}
			if (changed) {
				e.position = new Vector2D(x, y);
			}
		}
	}

}
