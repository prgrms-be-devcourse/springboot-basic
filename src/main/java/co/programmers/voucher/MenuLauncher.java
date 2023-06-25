package co.programmers.voucher;

import java.util.Map;

public class MenuLauncher {
	private MenuLauncher() {
	}

	public static Launcher from(String menu) {
		Launcher launcher = new Launcher() {
			@Override
			public void run(Map<String, Object> demandedData) {
			}

			@Override
			public Map<String, Object> getRequestBody() {
				return Map.of();
			}
		};
		switch (menu.toLowerCase()) {
			case ("create"):
				launcher = CreatingMenu.getInstance();
				break;
			case ("list"):
				launcher = ListingMenu.getInstance();
				break;
			case ("exit"):
				break;
			default:
				throw new IllegalArgumentException("* Invalid input is entered for menu *");
		}
		return launcher;
	}
}
