package com.prgrms.vouchermanagement.voucher.controller;

import java.util.Locale;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.prgrms.vouchermanagement.voucher.VoucherType;


public class Str2VoucherTypeConverter implements Converter<String, VoucherType> {
	public Str2VoucherTypeConverter() {
		System.out.println("Im here");
	}

	@Override
	public VoucherType convert(String source) {
		return VoucherType.from(source.toUpperCase());
	}
}
