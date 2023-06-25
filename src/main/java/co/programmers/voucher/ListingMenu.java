package co.programmers.voucher;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.programmers.voucher.Repository.VoucherRepository;

@Service
public class ListingMenu implements Launcher {
	private static final ListingMenu LISTING_MENU_LAUNCHER = new ListingMenu();
	private static VoucherRepository REPOSITORY;

	private ListingMenu() {
	}

	@Autowired
	private ListingMenu(VoucherRepository REPOSITORY) {
		ListingMenu.REPOSITORY = REPOSITORY;
	}

	public static Launcher getInstance() {
		return LISTING_MENU_LAUNCHER;
	}

	@Override
	public void run(Map<String, Object> requestBody) {
		//TODO
	}

	@Override
	public Map<String, Object> getRequestBody() {
		return Map.of();
	}
}
