package com.dpm.team4.util;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class NXTConstants {
	/*
	 * Motor bindings
	 */
	public static final NXTRegulatedMotor LEFT_MOTOR = Motor.A;
	public static final NXTRegulatedMotor RIGHT_MOTOR = Motor.C;
	public static final NXTRegulatedMotor FIRING_MOTOR = Motor.B;
	
	/*
	 * Sensor Bindings
	 */
	public static final SensorPort RANDOM1 = SensorPort.S1;
	public static final SensorPort RANDOM2 = SensorPort.S2;
	public static final SensorPort RANDOM3 = SensorPort.S3;
	public static final SensorPort RANDOM4 = SensorPort.S4;
	
	/*
	 * Thread loop interval
	 */
	public static final long NAVIGATION_PERIOD = 0;
	public static final long ODOMETER_PERIOD = 0;
	
	/*
	 * Field dimensions
	 */
	public static final double TILE = 30.48;// TODO: same tile length?
	public static final double DISTANCE_TOLERANCE = 0.2;
	
	/*
	 * Robot dimensions
	 */
	public static final double WIDTH = 15.952;
	public static final double RIGHT_RADIUS = 2.125;
	public static final double LEFT_RADIUS = 2.125;
	
	/*
	 * Ballistics constants
	 */
	public static final long BALLISTICS_DELAY = 500;
	public static final int FIRING_ACCELERATION = 6000;
	public static final int FIRING_SPEED = 400;
}
