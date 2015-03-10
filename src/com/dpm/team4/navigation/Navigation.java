package com.dpm.team4.navigation;

import com.dpm.team4.detection.DetectionEvent;
import com.dpm.team4.detection.DetectionListener;
import com.dpm.team4.detection.DetectionType;
import com.dpm.team4.util.NXTConstants;

public class Navigation extends Thread implements DetectionListener {

	public Navigation() {
		// TODO Auto-generated constructor stub
	}

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
			if (updateEnd - updateStart < NXTConstants.TEMP_PERIOD) {
				try {
					Thread.sleep(NXTConstants.TEMP_PERIOD - (updateEnd - updateStart));
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
	 * Update navigation according to detection event.
	 */
	@Override
	public void detectionOccurred(DetectionEvent evt) {
		// TODO Auto-generated method stub
	}

	/**
	 * Detect only ultrasonic sensor events.
	 */
	@Override
	public DetectionType getDetectionType() {
		return DetectionType.US;
	}

}
