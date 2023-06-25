package co.programmers.voucher.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.programmers.voucher.entity.DiscountStrategy;
import co.programmers.voucher.entity.DiscountTypeGenerator;
import co.programmers.voucher.entity.Voucher;
import co.programmers.voucher.entity.VoucherBody;
import co.programmers.voucher.repository.VoucherRepository;

@Service
public class CreatingMenuService implements Launcher {

	private static final CreatingMenuService CREATING_MENU_LAUNCHER = new CreatingMenuService();
	private static VoucherRepository REPOSITORY;
	private static int voucherCnt;

	private CreatingMenuService() {
	}

	@Autowired
	private CreatingMenuService(VoucherRepository REPOSITORY) {
		CreatingMenuService.REPOSITORY = REPOSITORY;
	}

	public static CreatingMenuService getInstance() {
		return CREATING_MENU_LAUNCHER;
	}

	@Override
	public void run(Map<String, Object> demandedData) throws IllegalArgumentException {
		String discountType = (String)demandedData.get("type");
		DiscountStrategy discountStrategy = DiscountTypeGenerator.of(discountType,
				(Integer.parseInt((String)demandedData.get("amount"))));
		Voucher voucher = new Voucher(++voucherCnt,
				(String)demandedData.get("name"),
				(String)demandedData.get("description"),
				discountStrategy);
		REPOSITORY.save(voucher);

	}

	@Override
	public Map<String, Object> getRequestBody() {
		return VoucherBody.get();
	}
}
