package com.programmers.voucher.controller;

public class ControllerPower {

	private static boolean runStatus = true;

	private ControllerPower() {
	}

	public static void stop() {
		runStatus = false;
	}

	public static boolean isRunning() {
		return runStatus;
	}
}
