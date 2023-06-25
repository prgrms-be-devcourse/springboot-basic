package co.programmers.voucher.Voucher;

import java.time.LocalDateTime;

//TODO : bean validation 추가
public class Voucher {
	private static int voucherCnt;
	private final int id;
	private final String name;
	private final String description;
	private final LocalDateTime createdAt;
	private final LocalDateTime expiredAt;
	private final int expirationPeriod = 180;
	private final int amount;
	private final DiscountStrategy discountStrategy;

	public Voucher(String name, String description, int amount, DiscountStrategy discountStrategy) {
		this.name = name;
		this.description = description;
		this.amount = amount;
		this.discountStrategy = discountStrategy;
		id = voucherCnt++;
		createdAt = LocalDateTime.now();
		expiredAt = createdAt.plusDays(expirationPeriod);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
