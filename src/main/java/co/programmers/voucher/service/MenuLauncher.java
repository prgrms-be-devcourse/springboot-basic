package co.programmers.voucher.service;

import java.util.Map;

public class MenuLauncher {
	private static Launcher launcher;

	public static Launcher from(String menu) throws IllegalArgumentException {
		switch (menu.toLowerCase()) {
			case ("create"):
				launcher = CreatingMenuService.getInstance();
				break;
			case ("list"):
				launcher = ListingMenuService.getInstance();
				break;
			case ("exit"):
				launcher = newEmptyInstance();
				break;
			default:
				throw new IllegalArgumentException("* Invalid input for menu *");
		}
		return launcher;
	}

	private static Launcher newEmptyInstance() {
		return new Launcher() {
			@Override
			public void run(Map<String, Object> demandedData) {
			}

			@Override
			public Map<String, Object> getRequestBody() {
				return Map.of();
			}
		};
	}
}
