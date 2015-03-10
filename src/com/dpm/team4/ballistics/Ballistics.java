package com.dpm.team4.ballistics;

import lejos.nxt.NXTRegulatedMotor;

import com.dpm.team4.util.NXTConstants;

public class Ballistics {
	// Motor
	private NXTRegulatedMotor motor;

	/**
	 * Wrapper class for our projectile launcher.
	 */
	public Ballistics() {
		// Bind motor
		motor = NXTConstants.FIRING_MOTOR;
		// Set default speed and acceleration
		motor.setSpeed(NXTConstants.FIRING_SPEED);
		motor.setAcceleration(NXTConstants.FIRING_ACCELERATION);
	}

	/**
	 * Shoot one projectile
	 */
	public void shoot() {
		motor.rotate(360);
	}

	/**
	 * Shoot all projectiles in the loader
	 * 
	 * @param n number of projectiles in the loader
	 */
	public void shootAll(int n) {
		while (n > 0) {
			n--;
			this.shoot();
			try {
				Thread.sleep(NXTConstants.BALLISTICS_DELAY);
			} catch (InterruptedException e) {
				/*
				 * There is nothing to be done here because it is not expected that this thread will be interrupted by
				 * another thread
				 */
			}

		}
	}
}
