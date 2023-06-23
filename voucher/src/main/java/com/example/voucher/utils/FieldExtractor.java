package com.example.voucher.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldExtractor {

	public static List<Object> extract(Object object) throws IllegalAccessException {
		Class<?> cls = object.getClass();
		List<Object> objects = new ArrayList<>();

		for (Field field : cls.getDeclaredFields()) {
			field.setAccessible(true);
			Object value = field.get(object);
			objects.add(value);
		}
		return objects;
	}

}
