package com.example.voucher.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldExtractor {

	public static <T> List<T> extract(T object) throws IllegalAccessException {
		Class<?> cls = object.getClass();
		List<T> objects = new ArrayList<>();

		for (Field field : cls.getDeclaredFields()) {
			field.setAccessible(true);
			Object value = field.get(object);
			if (value != null && field.getType().isAssignableFrom(value.getClass())) {
				objects.add((T)value);
			}
		}
		return objects;
	}

}
