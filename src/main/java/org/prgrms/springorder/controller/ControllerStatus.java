package org.prgrms.springorder.controller;

public class ControllerStatus {

	private boolean running;

	public ControllerStatus() {
		this.running = true;
	}

	public void stop() {
		running = false;
	}

	public boolean isRunning() {
		return running;
	}
}
