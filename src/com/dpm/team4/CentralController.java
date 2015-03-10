package com.dpm.team4;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;

public class CentralController implements ButtonListener {
	private boolean continueLoop = true;

	/**
	 * Central hub for controlling the robot's current actions.
	 */
	public CentralController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Default runtime procedure. Can be overridden during tests.
	 */
	public void run() {
		int buttonChoice;
		do {
			buttonChoice = Button.waitForAnyPress();
		} while (buttonChoice != Button.ID_ENTER && buttonChoice != Button.ID_ESCAPE);

		if (buttonChoice == Button.ID_ENTER) {
			Button.ESCAPE.addButtonListener(this);
			while(continueLoop) {
				// TODO
			}
		}
		System.exit(0);
	}

	/**
	 * Start the main thread if robot is run normally (not testing).
	 * 
	 * @param args Command line arguments (Not Applicable)
	 */
	public static void main(String[] args) {
		CentralController controller = new CentralController();
		controller.run();
	}

	/**
	 * If button is pressed after listener is binded, stop loop. Binds to ESCAPE button.
	 */
	@Override
	public void buttonPressed(Button b) {
		continueLoop = false;
	}

	@Override
	public void buttonReleased(Button b) {
		// Do nothing
	}
}
