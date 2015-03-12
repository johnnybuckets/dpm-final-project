package com.dpm.team4.navigation;

import java.util.ArrayList;
import java.util.Queue;

import lejos.nxt.NXTRegulatedMotor;

import com.dpm.team4.detection.DetectionEvent;
import com.dpm.team4.detection.DetectionListener;
import com.dpm.team4.detection.DetectionType;
import com.dpm.team4.odometer.Odometer;
import com.dpm.team4.util.NXTConstants;
import com.dpm.team4.util.NXTMath;
import com.dpm.team4.util.Vector;

public class Navigation extends Thread implements DetectionListener {
	// For mutual exclusion
	private Object lock;

	// Odometer
	private Odometer odometer;

	// Motors
	private NXTRegulatedMotor left;
	private NXTRegulatedMotor right;

	// Navigation
	private ArrayList<Vector> destinations = null;
	private Vector currentDestination;
	private double[] lastKnownPosition;
	private long lastKnownTime;

	// Obstacle
	private Queue<DetectionEvent> obstacleDetectionQueue = new Queue<>();

	public Navigation(Odometer odometer) {
		// Initialize Objects
		lock = new Object();
		this.odometer = odometer;
		left = NXTConstants.LEFT_MOTOR;
		right = NXTConstants.RIGHT_MOTOR;
		destinations = new ArrayList<>();
		currentDestination = null;

		// Initialize primitives
		lastKnownPosition = new double[3];
		lastKnownTime = System.currentTimeMillis();
	}

	@Override
	public void run() {
		long updateStart, updateEnd;

		while (true) {
			updateStart = System.currentTimeMillis();

			// Start Update
			if (destinations.size() > 0 || currentDestination != null) {
				// Get odometer readings
				odometer.getPosition(lastKnownPosition);
				lastKnownTime = System.currentTimeMillis();

				// Update current destination if necessary
				synchronized (lock) {
					if (currentDestination == null) {
						currentDestination = destinations.remove(0);
					}
				}

				// evaluate distance from destination
				if (currentDestination.distance(lastKnownPosition[0], lastKnownPosition[1]) > NXTConstants.NAVIGATION_ERROR) {
					// TODO: keep navigating
				} else {
					// Wait for next loop to travel to next point
					left.stop();
					right.stop();
					synchronized (lock) {
						currentDestination = null;
					}
				}
			}
			// End Update

			// ensure that this only runs once every period
			updateEnd = System.currentTimeMillis();
			if (updateEnd - updateStart < NXTConstants.NAVIGATION_PERIOD) {
				try {
					Thread.sleep(NXTConstants.NAVIGATION_PERIOD - (updateEnd - updateStart));
				} catch (InterruptedException e) {
					/*
					 * There is nothing to be done here because it is not expected that this thread will be interrupted
					 * by another thread
					 */
				}
			}
		}
	}

	/**
	 * Travel to the desired x and y coordinates (cm) by first turning then traveling a given distance.
	 * 
	 * @param x horizontal coordinate in cm
	 * @param y vertical coordinate in cm
	 * @param returnImmediately if true the function will not wait for the actions to complete
	 */
	public void travelTo(double x, double y, boolean returnImmediately) {
		// Update position if necessary
		if (System.currentTimeMillis() - lastKnownTime > NXTConstants.NAVIGATION_PERIOD) {
			odometer.getPosition(lastKnownPosition);
			lastKnownTime = System.currentTimeMillis();
		}

		// Get the desired bearing
		double dx = x - lastKnownPosition[0];
		double dy = y - lastKnownPosition[1];
		double theta = Math.atan2(dx, dy);

		// Turn to the bearing using a minimal angle
		turnTo(theta, false);

		// Travel the necessary distance
		travelDistance(NXTMath.vectorDistance(x, y, lastKnownPosition[0], lastKnownPosition[1], theta),
				returnImmediately);
	}

	/**
	 * Travel a desired distance in cm.
	 * 
	 * @param distance in cm
	 * @param returnImmediately if true the function will not wait for the actions to complete
	 */
	public void travelDistance(double distance, boolean returnImmediately) {
		// Convert desired distance to rotations
		double leftRotations = NXTMath.convertDistanceToRotations(NXTConstants.LEFT_RADIUS, distance);
		double rightRotations = NXTMath.convertDistanceToRotations(NXTConstants.RIGHT_RADIUS, distance);
		// Rotate the motors without blocking the thread
		this.left.rotate((int) Math.round(leftRotations), true);
		this.right.rotate((int) Math.round(rightRotations), returnImmediately);
	}

	/**
	 * Turn to the given heading using a minimal angle of rotation.
	 * 
	 * @param angle in radians
	 * @param returnImmediately if true the function will not wait for the actions to complete
	 */
	public void turnTo(double angle, boolean returnImmediately) {
		// Update position if necessary
		if (System.currentTimeMillis() - lastKnownTime > NXTConstants.NAVIGATION_PERIOD) {
			odometer.getPosition(lastKnownPosition);
			lastKnownTime = System.currentTimeMillis();
		}

		// Turn using minimal angle
		turnAngle(NXTMath.minimalAngle(angle - lastKnownPosition[2]), returnImmediately);
	}

	/**
	 * Turn absolutely by the given amount.
	 * 
	 * @param angle in radians
	 * @param returnImmediately if true the function will not wait for the actions to complete
	 */
	public void turnAngle(double angle, boolean returnImmediately) {
		// Convert angle to degrees
		angle = NXTMath.toDegrees(angle);
		// Get number of rotations
		double leftRotations = NXTMath.convertAngleToRotations(NXTConstants.LEFT_RADIUS, NXTConstants.WIDTH, angle);
		double rightRotations = NXTMath.convertAngleToRotations(NXTConstants.RIGHT_RADIUS, NXTConstants.WIDTH, angle);
		// Rotate the motors without blocking the thread
		this.left.rotate((int) Math.round(leftRotations), true);
		this.right.rotate((int) -Math.round(rightRotations), false);
	}

	/**
	 * Travel at a certain speed.
	 * 
	 * @param speed in degrees/sec
	 */
	public void travelForward(float speed) {
		// Set speed for both motors
		this.left.setSpeed(speed);
		this.right.setSpeed(speed);

		// Start traveling
		this.left.forward();
		this.right.forward();
	}

	/**
	 * Travel while setting left and right motors speeds simultaneously.
	 * 
	 * @param leftSpeed in degrees/sec
	 * @param rightSpeed in degrees/sec
	 */
	public void travelSpeeds(float leftSpeed, float rightSpeed) {
		// Set speed for both motors
		this.left.setSpeed(leftSpeed);
		this.right.setSpeed(rightSpeed);

		// Start traveling
		this.left.forward();
		this.right.forward();
	}

	/**
	 * Rotate in place.
	 * 
	 * @param speed rotation speed in degrees/sec
	 */
	public void rotate(float speed) {
		// Set speed for both motors
		this.left.setSpeed(speed);
		this.right.setSpeed(-speed);

		// Start traveling
		this.left.forward();
		this.right.forward();
	}

	/**
	 * Stop the robot as soon as possible.
	 * 
	 * @param returnImmediately if true the function will not wait for the actions to complete
	 */
	public void stop(boolean returnImmediately) {
		this.left.stop(true);
		this.right.stop(returnImmediately);
	}

	/**
	 * Update navigation according to detection event.
	 */
	@Override
	public void handleDetection(DetectionEvent evt) {
		obstacleDetectionQueue.push(evt);
	}

	/**
	 * Detect only ultrasonic sensor events.
	 */
	@Override
	public DetectionType getDetectionType() {
		return DetectionType.US;
	}

	/**
	 * Add a vector or vectors to the list of destinations.
	 * 
	 * @param destinations one more more Vector objects
	 */
	public void addDestination(Vector... destinations) {
		for (Vector d : destinations) {
			this.destinations.add(d);
		}
	}

}
