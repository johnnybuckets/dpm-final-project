package com.dpm.team4.util;

public class Vector {
	public static final Vector XUNIT = new Vector(1, 0);
	public static final Vector YUNIT = new Vector(0, 1);

	private double x;
	private double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector(Vector v) {
		this(v.getX(), v.getY());
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double length() {
		return Math.sqrt(this.lengthSquared());
	}

	public double lengthSquared() {
		return (x * x) + (y * y);
	}

	public Vector unit() {
		double l = this.length();
		return new Vector(this.x / l, this.y / l);
	}

	public Vector scale(double a) {
		return new Vector(this.x * a, this.y * a);
	}

	public Vector add(double x, double y) {
		return new Vector(this.x + x, this.y + y);
	}

	public Vector add(Vector v) {
		return new Vector(this.x + v.getX(), this.y + v.getY());
	}

	public Vector subtract(double x, double y) {
		return new Vector(this.x - x, this.y - y);
	}

	public Vector subtract(Vector v) {
		return new Vector(this.x - v.getX(), this.y - v.getY());
	}

	public double dot(double x, double y) {
		return ((this.x * x) + (this.y * y));
	}

	public double dot(Vector v) {
		return ((this.x * v.getX()) + (this.y * v.getY()));
	}

	public double distance(double x, double y) {
		double dx = this.x - x;
		double dy = this.y - y;
		return Math.sqrt((dx * dx) + (dy * dy));
	}

	public double angle() {
		return Math.atan2(x, y);
	}
}
