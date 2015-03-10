package com.dpm.team4.util;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class NXTConstants {// TODO: set up constants
	/*
	 * Motor bindings
	 */
	public static final NXTRegulatedMotor LEFT_MOTOR = Motor.A;
	public static final NXTRegulatedMotor RIGHT_MOTOR = Motor.C;
	public static final NXTRegulatedMotor FIRING_MOTOR = Motor.B;

	/*
	 * Sensor Bindings
	 */
	public static final SensorPort LEFT_LS = SensorPort.S1;
	public static final SensorPort RIGHT_LS = SensorPort.S2;
	public static final SensorPort LEFT_US = SensorPort.S3;
	public static final SensorPort RIGHT_US = SensorPort.S4;

	/*
	 * Field dimensions
	 */
	public static final double TILE = 0;// TODO: was 30.48

	/*
	 * Robot dimensions
	 */
	public static final double WIDTH = 0;
	public static final double RIGHT_RADIUS = 0;// TODO was 2.115
	public static final double LEFT_RADIUS = 0;// TODO was 2.115

	/*
	 * Thread loop interval
	 */
	public static final long TEMP_PERIOD = 0;// TODO remove this later
	public static final long CONTROLLER_PERIOD = 0;
	public static final long DISPLAY_PERIOD = 0;
	public static final long ODOMETER_PERIOD = 0;
	public static final long NAVIGATION_PERIOD = 0;
	public static final long DETECTION_PERIOD = 0;

	/*
	 * Odometer Constants
	 */
	// TODO

	/*
	 * Navigation Constants
	 */
	// TODO

	/*
	 * Detection Constants
	 */
	// TODO

	/*
	 * Localization Constants
	 */
	// TODO

	/*
	 * Ballistics constants
	 */
	public static final long BALLISTICS_DELAY = 0;
	public static final int FIRING_ACCELERATION = 0;
	public static final int FIRING_SPEED = 0;
}
