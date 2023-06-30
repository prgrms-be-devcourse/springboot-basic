package com.example.voucher.utils.lineAggregator;

import java.util.List;

import com.example.voucher.utils.FieldExtractor;

public class SpaceLineAggregator implements LineAggregator {

	@Override
	public String aggregate(Object object) {
		StringBuilder sb = new StringBuilder("");

		try {
			List<Object> objects = FieldExtractor.extract(object);
			int lastIdx = objects.size() - 1;

			for (int i = 0; i < objects.size(); i++) {
				sb.append(objects.get(i));
				sb.append(i != lastIdx ? " " : "");
			}
			sb.append("\n");
		} catch (IllegalAccessException e) {

		}

		return sb.toString();
	}

}
