package com.dpm.team4.util;

public class NXTMath {
	/**
	 * Convert radians to degrees.
	 * 
	 * @param angle
	 * @return angle in degrees
	 */
	public static double toDegrees(double angle) {
		return (180.0 / Math.PI) * angle;
	}

	/**
	 * Convert degrees to radians.
	 * 
	 * @param angle
	 * @return angle in radians
	 */
	public static double toRadians(double angle) {
		return (Math.PI / 180.0) * angle;
	}

	/**
	 * Convert a desired travel distance into rotations in degrees.
	 * 
	 * @param radius wheel radius
	 * @param distance desired distance
	 * @return number of wheel rotations needed to travel the distance
	 */
	public static int convertDistanceToRotations(double radius, double distance) {
		return (int) Math.round(toDegrees(distance / radius));
	}

	/**
	 * Convert a desired turn angle into rotations in degrees.
	 * 
	 * @param radius wheel radius
	 * @param width robot width
	 * @param angle desired rotation angle in degrees
	 * @return number of wheel rotations needed for turn
	 */
	public static int convertAngleToRotations(double radius, double width, double angle) {
		return convertDistanceToRotations(radius, toRadians(width * angle / 2.0));
	}

	/**
	 * Convert rotational degrees to distance traveled.
	 * 
	 * @param radius wheel radius
	 * @param rotations current rotations completed in degrees
	 * @return distance traveled
	 */
	public static double convertRotationsToDistance(double radius, double rotations) {
		return radius * toRadians(rotations);
	}

	/**
	 * Determine if angle is minimal or not..
	 * 
	 * @param dTheta in radians
	 * @return minimal angle or dTheta if it is already minimal
	 */
	public static double minimalAngle(double dTheta) {
		if (dTheta < -Math.PI) {
			return dTheta + (Math.PI * 2.0);
		} else if (dTheta > Math.PI) {
			return dTheta - (Math.PI * 2.0);
		} else {
			return dTheta;
		}
	}

	/**
	 * Euclidean distance from current position to destination.
	 * 
	 * @param xDestination
	 * @param yDestination
	 * @param xCurrent
	 * @param yCurrent
	 * @return Euclidean distance between two points
	 */
	public static double euclideanDistance(double xDestination, double yDestination, double xCurrent, double yCurrent) {
		return Math.sqrt((xDestination * xCurrent) + (yDestination * yCurrent));
	}

	/**
	 * Vector distance from current position to destination.
	 * 
	 * @param xDestination
	 * @param yDestination
	 * @param xCurrent
	 * @param yCurrent
	 * @param theta
	 * @return vector distance between two points
	 */
	public static double vectorDistance(double xDestination, double yDestination, double xCurrent, double yCurrent,
			double theta) {
		Vector r = new Vector(Math.sin(theta), Math.cos(theta));
		Vector d = new Vector(xDestination - xCurrent, yDestination - yCurrent);
		return d.dot(r.unit());
	}
}
