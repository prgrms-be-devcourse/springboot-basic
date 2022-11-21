package com.programmers.voucher.console.run;

public class AppPower {

	private static boolean runStatus = true;

	private AppPower() {
	}

	public static void stop() {
		runStatus = false;
	}

	public static boolean isRunning() {
		return runStatus;
	}
}
