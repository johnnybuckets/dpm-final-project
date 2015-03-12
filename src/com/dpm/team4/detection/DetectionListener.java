package com.dpm.team4.detection;

import java.util.EventListener;

public interface DetectionListener extends EventListener {
	public void handleDetection(DetectionEvent evt);

	public DetectionType getDetectionType();
}
