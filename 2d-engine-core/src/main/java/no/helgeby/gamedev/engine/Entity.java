package no.helgeby.gamedev.engine;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Comparable<Entity> {

	public enum Edge {
		NONE, TOP, BOTTOM, LEFT, RIGHT,

	}

	private static final float DEFAULT_MAX_SPEED = 100;

	protected long id;

	protected Vector2D position;
	protected Vector2D velocity;
	protected float maxSpeed;
	protected int width;
	protected int height;

	protected KeyListener keyListener;
	protected MouseListener mouseListener;

	protected Environment parent;
	protected Entity touchingEntity;

	public Entity(Environment parent) {
		if (parent == null) {
			throw new IllegalArgumentException("Parameter parent is required.");
		}
		this.parent = parent;
		position = new Vector2D();
		velocity = new Vector2D();
		maxSpeed = DEFAULT_MAX_SPEED;
		width = 0;
		height = 0;
	}

	public void tick() {
		doMovement();
	}

	protected void doMovement() {
		position = position.add(velocity);

		// Accelerate.
		Vector2D newVelocity = velocity.add(parent.gravity);
		if (newVelocity.length() > maxSpeed) {
			// Clamp to max speed.
			newVelocity = newVelocity.clampPartial(maxSpeed);
		}
		velocity = newVelocity;
	}

	public void draw(Graphics g) {
		// Do nothing. Default entity is transparent.
	}

	public Edge touchingEdge() {
		if (position.x + velocity.x < 0) {
			return Entity.Edge.LEFT;
		}
		if (position.x + velocity.x > parent.getWidth() - width) {
			return Entity.Edge.RIGHT;
		}
		if (position.y + velocity.y < 0) {
			return Entity.Edge.TOP;
		}
		if (position.y + velocity.y > parent.getHeight() - height) {
			return Entity.Edge.BOTTOM;
		}
		return Entity.Edge.NONE;
	}

	public Edge touchingOther() {
		// Clone the entity list.
		List<Entity> entities = new ArrayList<>(parent.getEntities());
		for (Entity e : parent.getEntities()) {
			if (e == this) {
				// Skip self.
				continue;
			}
			if (e.touchingEntity != null && e.touchingEntity.id == id) {
				// Skip other entity touching this. We handle touch here.
				continue;
			}
			Edge edge = touches(e);
			if (edge != Edge.NONE) {
				entities.remove(e);
				touchingEntity = e;
				return edge;
			}
		}
		touchingEntity = null;
		return Edge.NONE;
	}

	public Edge touches(Entity other) {
		// TODO: Something is wrong here. Not detecting left/right collisions properly.
		float top = position.y + velocity.y;
		float bottom = position.y + height + velocity.y;
		float left = position.x + velocity.x;
		float right = position.x + width + velocity.x;

		float otherTop = other.position.y + other.velocity.y;
		float otherBottom = other.position.y + other.height + other.velocity.y;
		float otherLeft = other.position.x + other.velocity.x;
		float otherRight = other.position.x + other.width + other.velocity.x;

		boolean touchingTop = otherBottom > top && otherTop < bottom;
		boolean touchingBottom = otherTop < bottom && otherBottom > top;
		boolean touchingLeft = otherRight > left && otherLeft < right;
		boolean touchingRight = otherLeft < right && otherRight > left;


		if (touchingTop && (touchingLeft || touchingRight)) {
			return Edge.TOP;
		}
		if (touchingBottom && (touchingLeft || touchingRight)) {
			return Edge.BOTTOM;
		}
		if (touchingLeft && (touchingTop || touchingBottom)) {
			return Edge.LEFT;
		}
		if (touchingRight && (touchingTop || touchingBottom)) {
			return Edge.RIGHT;
		}

		return Edge.NONE;
	}

	public double getSpeed() {
		return velocity.length();
	}

	public long getId() {
		return id;
	}

	public int compareTo(Entity other) {
		return Long.compare(id, other.id);
	}

	public int hasCode() {
		return Long.hashCode(id);
	}

	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!(other instanceof Entity)) {
			return false;
		}
		Entity otherEntity = (Entity) other;
		return id == otherEntity.id;
	}

	public void move(Vector2D distance) {
		position = position.add(distance);
	}

	public void push(Vector2D distance) {
		velocity = velocity.add(distance);
	}
}
