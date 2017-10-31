package tools;

public class Vector2 {

	public float x;
	public float y;
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public static Vector2 Zero() {
		return new Vector2(0, 0);
	}
	
	public Vector2 add(Vector2 other) {
		return new Vector2(this.x + other.x, this.y + other.y);
	}
	
	public Vector2 sub(Vector2 other) {
		return new Vector2(this.x - other.x, this.y - other.y);
	}
	
	public Vector2 mult(float k) {
		return new Vector2(x * k, y * k);
	}
	
	public Vector2 mult(Vector2 other) {
		return new Vector2(this.x * other.x, this.y * other.y);
	}
	
	public Vector2 div(Vector2 other) {
		return new Vector2(this.x / other.x, this.y / other.y);
	}
}
