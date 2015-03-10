package com.dpm.team4.detection;

public class DetectionEvent {
	private final DetectionType type;
	private final long timestamp;
	private final DetectionSource source;
	private final int value;

	public DetectionEvent(DetectionType type, long timestamp, DetectionSource source, int value) {
		this.type = type;
		this.timestamp = timestamp;
		this.source = source;
		this.value = value;
	}

	public DetectionType getType() {
		return type;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public DetectionSource getSource() {
		return source;
	}

	public int getValue() {
		return value;
	}
}
