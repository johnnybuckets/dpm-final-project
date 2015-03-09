package com.dpm.team4.ballistics;

import com.dpm.team4.util.NXTConstants;

import lejos.nxt.NXTRegulatedMotor;

public class Ballistics{
	// Motor
	private NXTRegulatedMotor motor;

	public Ballistics() {
		motor = NXTConstants.FIRING_MOTOR;
		
		motor.setSpeed(NXTConstants.FIRING_SPEED);
		motor.setAcceleration(NXTConstants.FIRING_ACCELERATION);
	}
	
	public void shoot(){
		motor.rotate(360);
	}
	
	public void shootAll(int n){
		while(n>0) {
			n--;
			this.shoot();
				try {
					Thread.sleep(NXTConstants.BALLISTICS_DELAY);
				} catch (InterruptedException e) {
					// there is nothing to be done here because it is not
					// expected that this thread will be interrupted by
					// another thread
				}
			
		}
	}
}
