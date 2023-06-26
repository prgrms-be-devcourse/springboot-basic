package co.programmers.voucher.entity;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Voucher {
	private final int expirationPeriod = 180;

	private final DiscountStrategy discountStrategy;

	private final int discountAmount;
	private final String name;
	private final String description;
	private final int id;
	private final LocalDateTime createdAt = LocalDateTime.now();
	private final LocalDateTime expiredAt;

	public Voucher(int id, String name, String description, DiscountStrategy discountStrategy,
			int discountAmount) throws
			IllegalArgumentException {
		this.discountStrategy = discountStrategy;
		this.name = name;
		this.description = description;
		this.id = id;
		this.discountAmount = discountAmount;
		expiredAt = createdAt.plusDays(expirationPeriod);
		validate();
	}

	private void validate() throws IllegalArgumentException {
		int descriptionMaxLength = 100;
		int nameMaxLength = 20;
		StringBuffer exceptionMessage = new StringBuffer();

		if (name == null || name.isBlank() || name.isEmpty()) {
			exceptionMessage.append("Empty value for Name.\n");
		} else if (name.length() > nameMaxLength) {
			exceptionMessage.append(
					MessageFormat.format("Name must have up to {0} characters.\n", nameMaxLength));
		}
		if (description == null || description.isBlank() || description.isEmpty()) {
			exceptionMessage.append("Empty value for description.\n");
		} else if (description.length() > descriptionMaxLength) {
			exceptionMessage.append(
					MessageFormat.format("Description must have up to {0} characters.\n", descriptionMaxLength));
			exceptionMessage.append(
					MessageFormat.format("It is currently {0} characters.\n", description.length()));
		}
		if (exceptionMessage.length() > 0) {
			throw new IllegalArgumentException(exceptionMessage.toString());
		}
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	public String getCreatedAt() {
		return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public String getExpiredAt() {
		return expiredAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public DiscountStrategy getDiscountStrategy() {
		return discountStrategy;
	}

	public int getDiscountAmount() {
		return discountAmount;
	}

}
