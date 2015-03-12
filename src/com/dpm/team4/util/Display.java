package com.dpm.team4.util;

import com.dpm.team4.detection.DetectionEvent;
import com.dpm.team4.detection.DetectionListener;
import com.dpm.team4.detection.DetectionType;

public class Display extends Thread implements DetectionListener {
	/**
	 * Initialize the LCD.
	 */
	public Display() {
		// Make this a background thread
		this.setDaemon(true);
	}

	/**
	 * Update the display with current information on odometry, sensor readings, and actions.
	 */
	@Override
	public void run() {
		long updateStart, updateEnd;

		while (true) {
			updateStart = System.currentTimeMillis();

			// Start Update
			// TODO
			// End Update

			// ensure that this only runs once every period
			updateEnd = System.currentTimeMillis();
			if (updateEnd - updateStart < NXTConstants.DISPLAY_PERIOD) {
				try {
					Thread.sleep(NXTConstants.DISPLAY_PERIOD - (updateEnd - updateStart));
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
	 * Update display with new sensor detection.
	 */
	@Override
	public void handleDetection(DetectionEvent evt) {
		// TODO Auto-generated method stub
	}

	/**
	 * Detect both types of sensors.
	 */
	@Override
	public DetectionType getDetectionType() {
		return DetectionType.BOTH;
	}
}
