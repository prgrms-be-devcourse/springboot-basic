package com.example.voucher.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldExtractor {

	public static List<Object> extract (Object object) throws IllegalAccessException {
		Class<?> cls = object.getClass();
		List<Object> objects = new ArrayList<>();

		for (Field field : cls.getDeclaredFields()) {
			field.setAccessible(true); // 접근 가능하게 설정 (private 필드에 접근하기 위해)
			Object value = field.get(object); // 필드 값 가져오기
			objects.add(value);
		}
		return objects;
	}

}
