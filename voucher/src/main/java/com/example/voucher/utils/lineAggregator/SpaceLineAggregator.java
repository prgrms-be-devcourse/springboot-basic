package com.example.voucher.utils.lineAggregator;

import java.util.List;

import com.example.voucher.utils.FieldExtractor;

public class SpaceLineAggregator implements LineAggregator {

	@Override
	public String aggregate(Object object) {
		StringBuilder sb = new StringBuilder("");
		try {
			List<Object> objects = FieldExtractor.extract(object);
			for (int i = 0; i < objects.size(); i++) {
				if (i == objects.size() - 1) {
					sb.append(object).append("\n");
				} else {
					sb.append(object).append(" ");
				}
			}
		} catch (IllegalAccessException e) {

		}

		return sb.toString();
	}

}
