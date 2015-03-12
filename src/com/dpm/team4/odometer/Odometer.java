package com.dpm.team4.odometer;

import lejos.nxt.NXTRegulatedMotor;
import com.dpm.team4.detection.DetectionEvent;
import com.dpm.team4.detection.DetectionListener;
import com.dpm.team4.detection.DetectionType;
import com.dpm.team4.util.NXTConstants;
import com.dpm.team4.util.NXTMath;

public class Odometer extends Thread implements DetectionListener {
	// For mutual exclusion
	private Object lock;

	// Motors
	private NXTRegulatedMotor left;
	private NXTRegulatedMotor right;

	// Horizontal displacement
	private double x;
	// Vertical displacement
	private double y;
	// Bearing
	private double theta;

	// Previous tachometer readings
	private int leftPreviousTacho;
	private int rightPreviousTacho;

	/**
	 * Keeps track of current position and angle of the robot according to the wheel tachometers.
	 */
	public Odometer() {
		// Initialize objects
		lock = new Object();
		left = NXTConstants.LEFT_MOTOR;
		right = NXTConstants.RIGHT_MOTOR;

		// Initialize variables
		x = 0.0;
		y = 0.0;
		theta = 0.0;
		leftPreviousTacho = 0;
		rightPreviousTacho = 0;

		// Initialize motor tachometers
		left.resetTachoCount();
		right.resetTachoCount();
	}

	/**
	 * Periodically updates the position.
	 */
	@Override
	public void run() {
		long updateStart, updateEnd;

		while (true) {
			updateStart = System.currentTimeMillis();

			// Start Update
			double dL = calculateLeftDistance();
			double dR = calculateRightDistance();
			double dTheta = calculateTheta(dL, dR);
			double dC = calculateArcLength(dL, dR);

			// update position
			synchronized (lock) {
				x += dC * Math.sin(theta + dTheta / 2.0);
				y += dC * Math.cos(theta + dTheta / 2.0);
				theta += dTheta;
				theta = setBound(theta);
			}
			// End Update

			// ensure that this only runs once every period
			updateEnd = System.currentTimeMillis();
			if (updateEnd - updateStart < NXTConstants.ODOMETER_PERIOD) {
				try {
					Thread.sleep(NXTConstants.ODOMETER_PERIOD - (updateEnd - updateStart));
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
	 * Calculate left distance traveled.
	 */
	private double calculateLeftDistance() {
		int leftAngle = left.getTachoCount();
		int dLeftAngle = leftAngle - this.leftPreviousTacho;
		double leftDistance = NXTMath.convertRotationsToDistance(NXTConstants.LEFT_RADIUS, dLeftAngle);
		this.leftPreviousTacho = leftAngle;

		return leftDistance;
	}

	/**
	 * Calculate right distance traveled.
	 */
	private double calculateRightDistance() {
		int rightAngle = right.getTachoCount();
		int dRightAngle = rightAngle - this.rightPreviousTacho;
		double rightDistance = NXTMath.convertRotationsToDistance(NXTConstants.RIGHT_RADIUS, dRightAngle);
		this.rightPreviousTacho = rightAngle;

		return rightDistance;
	}

	/**
	 * Calculate current robot angle.
	 */
	private double calculateTheta(double leftDistance, double rightDistance) {
		return ((leftDistance - rightDistance) / NXTConstants.WIDTH);
	}

	/**
	 * Set bound for the robot angle.
	 */
	private double setBound(double theta) {
		if (theta > 2.0 * Math.PI) {
			theta = theta - (2.0 * Math.PI);
		} else if (theta < 0) {
			theta = theta + (2.0 * Math.PI);
		}
		return theta;
	}

	/**
	 * Calculate the arc length traveled.
	 */
	private double calculateArcLength(double leftDistance, double rightDistance) {
		return ((leftDistance + rightDistance) / 2.0);
	}

	/**
	 * Update odometer according to detection event.
	 */
	@Override
	public void handleDetection(DetectionEvent evt) {
		// TODO Auto-generated method stub
	}

	/**
	 * Detect only light sensor events.
	 */
	@Override
	public DetectionType getDetectionType() {
		return DetectionType.LS;
	}

	public void getPosition(double[] position) {
		synchronized (lock) {
			position[0] = x;
			position[1] = y;
			position[2] = theta;
		}
	}

	public void setPosition(double[] position) {
		synchronized (lock) {
			x = position[0];
			y = position[1];
			theta = position[2];
		}
	}

	public void getPosition(double[] position, boolean[] update) {
		synchronized (lock) {
			if (update[0])
				position[0] = x;
			if (update[1])
				position[1] = y;
			if (update[2])
				position[2] = theta;
		}
	}

	public void setPosition(double[] position, boolean[] update) {
		synchronized (lock) {
			if (update[0])
				x = position[0];
			if (update[1])
				y = position[1];
			if (update[2])
				theta = position[2];
		}
	}

	public double getX() {
		double result;

		synchronized (lock) {
			result = x;
		}

		return result;
	}

	public void setX(double x) {
		synchronized (lock) {
			this.x = x;
		}
	}

	public double getY() {
		double result;

		synchronized (lock) {
			result = y;
		}

		return result;
	}

	public void setY(double y) {
		synchronized (lock) {
			this.y = y;
		}
	}

	public double getTheta() {
		double result;

		synchronized (lock) {
			result = theta;
		}

		return result;
	}

	public void setTheta(double theta) {
		synchronized (lock) {
			this.theta = theta;
		}
	}
}
