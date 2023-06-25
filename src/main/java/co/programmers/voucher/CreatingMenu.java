package co.programmers.voucher;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.programmers.voucher.Repository.VoucherRepository;
import co.programmers.voucher.Voucher.DiscountStrategy;
import co.programmers.voucher.Voucher.DiscountTypeGenerator;
import co.programmers.voucher.Voucher.Voucher;
import co.programmers.voucher.Voucher.VoucherBody;

@Service
public class CreatingMenu implements Launcher {

	private static final CreatingMenu CREATING_MENU_LAUNCHER = new CreatingMenu();
	private static VoucherRepository REPOSITORY;

	private CreatingMenu() {
	}

	@Autowired
	private CreatingMenu(VoucherRepository REPOSITORY) {
		CreatingMenu.REPOSITORY = REPOSITORY;
	}

	public static CreatingMenu getInstance() {
		return CREATING_MENU_LAUNCHER;
	}

	@Override
	public void run(Map<String, Object> demandedData) {
		//TODO : DiscountTypeGenerator exception 처리
		String discountType = (String)demandedData.get("type");
		DiscountStrategy discountStrategy = DiscountTypeGenerator.of(discountType);
		Voucher voucher = new Voucher((String)demandedData.get("name"),
				(String)demandedData.get("description"),
				Integer.parseInt((String)demandedData.get("amount")),
				discountStrategy);
		REPOSITORY.save(voucher);

	}

	@Override
	public Map<String, Object> getRequestBody() {
		return VoucherBody.get();
	}
}
