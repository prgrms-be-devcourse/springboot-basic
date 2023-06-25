package co.programmers.voucher.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.programmers.voucher.repository.VoucherRepository;
import co.programmers.voucher.view.OutputView;

@Service
public class ListingMenuService implements Launcher {
	private static final ListingMenuService LISTING_MENU_LAUNCHER = new ListingMenuService();
	private static VoucherRepository REPOSITORY;
	private static OutputView outputView;

	private ListingMenuService() {
	}

	@Autowired
	private ListingMenuService(VoucherRepository REPOSITORY, OutputView outputView) {
		ListingMenuService.REPOSITORY = REPOSITORY;
		ListingMenuService.outputView = outputView;
	}

	public static Launcher getInstance() {
		return LISTING_MENU_LAUNCHER;
	}

	@Override
	public void run(Map<String, Object> requestBody) throws IOException {
		List<Map<String, Object>> inquiredResult = REPOSITORY.inquire();
		outputView.println(inquiredResult.toString());
	}

	@Override
	public Map<String, Object> getRequestBody() {
		return Map.of();
	}
}
