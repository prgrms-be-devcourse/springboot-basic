package co.programmers.voucher.entity;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Voucher {
	private final int expirationPeriod = 180;
	private final DiscountStrategy discountStrategy;
	private final String name;
	private final String description;
	private final int id;
	private final LocalDateTime createdAt = LocalDateTime.now();
	private final LocalDateTime expiredAt;

	public Voucher(int id, String name, String description, DiscountStrategy discountStrategy) throws
			IllegalArgumentException {
		this.discountStrategy = discountStrategy;
		this.name = name;
		this.description = description;
		this.id = id;
		expiredAt = createdAt.plusDays(expirationPeriod);
		validate();
	}

	private void validate() throws IllegalArgumentException {
		int descriptionMaxLength = 100;
		StringBuffer exceptionMessage = new StringBuffer();

		if (name == null || name.isBlank() || name.isEmpty()) {
			exceptionMessage.append("Empty value for Name.\n");
		}

		if (description == null || description.isBlank() || description.isEmpty()) {
			exceptionMessage.append("Empty value for description.\n");
		}
		if (description.length() > descriptionMaxLength) {
			exceptionMessage.append("Description must have up to ");
			exceptionMessage.append(descriptionMaxLength);
			exceptionMessage.append(" characters.\n");
			exceptionMessage.append("It is currently ");
			exceptionMessage.append(description.length());
			exceptionMessage.append(" characters.\n");
		}
		if (exceptionMessage.length() > 0) {
			throw new IllegalArgumentException(exceptionMessage.toString());
		}
	}

	public Map<String, Object> extractFields(String... fieldNames) {
		Map<String, Object> extractedFields = new ConcurrentHashMap<>();
		try {
			Class<?> clazz = this.getClass();
			Field[] fields = clazz.getDeclaredFields();
			List<String> fieldNamesList = Arrays.asList(fieldNames);

			for (Field field : fields) {
				if (fieldNames.length == 0 || fieldNamesList.contains(field.getName())) {
					extractedFields.put(field.getName(), field.get(this));
				}
			}
		} catch (IllegalAccessException illegalAccessException) {
			illegalAccessException.printStackTrace();
		}
		return extractedFields;
	}
}
