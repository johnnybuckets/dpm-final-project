package com.dpm.team4.detection;

import java.util.ArrayList;

import com.dpm.team4.util.NXTConstants;

public class Detection extends Thread {
	// Objects listening to detection events
	private ArrayList<DetectionListener> listenerList;

	/**
	 * Manages the sensors by filtering and processing the data.
	 */
	public Detection() {
		// Make this a background thread
		this.setDaemon(true);
	}

	/**
	 * Run detection loop.
	 */
	@Override
	public void run() {
		// TODO Filter and send events to all relevant listeners
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
	 * Send event to all relevant listeners
	 * 
	 * @param evt the detection event
	 */
	public void dispatchDetectionEvent(DetectionEvent evt) {
		for (DetectionListener listener : listenerList) {
			if (listener.getDetectionType() == evt.getType()) {
				listener.detectionOccurred(evt);
			}
		}
	}
}
