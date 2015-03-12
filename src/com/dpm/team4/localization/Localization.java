package com.dpm.team4.localization;

import com.dpm.team4.navigation.Navigation;
import com.dpm.team4.odometer.Odometer;

public class Localization {
	// Odometer
	private Odometer odometer;
	
	// Navigation
	private Navigation navigation;
	
	// Sensors
	// TODO reference all sensors here
	
	/**
	 * Determine the robots initial position and angle using the light and ultrasonic sensors.
	 */
	public Localization(Odometer odometer, Navigation navigation) {
		this.odometer = odometer;
		this.navigation = navigation;
	}

	/**
	 * Perform a full localization routine.
	 */
	public void doLocalization() {
		// TODO implementation: do US localization, move to a grid line, do LS localization
	}
}
