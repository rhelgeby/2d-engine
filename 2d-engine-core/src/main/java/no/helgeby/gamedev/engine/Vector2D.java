package no.helgeby.gamedev.engine;

/**
 * Immutable 2D vector.
 */
public class Vector2D {

	public final float x;
	public final float y;

	public Vector2D() {
		x = 0;
		y = 0;
	}

	public Vector2D(float deltaX, float deltaY) {
		this.x = deltaX;
		this.y = deltaY;
	}

	public Vector2D(Vector2D vector) {
		this(vector.x, vector.y);
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public Vector2D normalize() {
		float length = length();
		return new Vector2D(x / length, y / length);
	}

	public Vector2D add(Vector2D other) {
		if (other == null) {
			return new Vector2D(this);
		}
		else {
			return add(other.x, other.y);
		}
	}

	public Vector2D add(float x, float y) {
		return new Vector2D(this.x + x, this.y + y);
	}

	public Vector2D multiply(Vector2D other) {
		return multiply(other.x, other.y);
	}

	public Vector2D multiply(float x, float y) {
		return new Vector2D(this.x * x, this.y * y);
	}

	public Vector2D multiply(float factor) {
		return new Vector2D(this.x * factor, this.y * factor);
	}

	public Vector2D clamp(float maxLength) {
		Vector2D normalized = this.normalize();
		return normalized.multiply(maxLength);
	}

	public Vector2D clampPartial(float maxLength) {
		float newX = x;
		float newY = y;
		if (x > maxLength) {
			newX = maxLength;
		}
		if (y > maxLength) {
			newY = maxLength;
		}
		return new Vector2D(newX, newY);
	}

	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	public String toFormattedString() {
		return String.format("%.2f, %.2f", x, y);
	}
}
