package com.example.voucher.domain;

import static java.lang.System.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.example.voucher.constant.ModeType;

class PercentDiscountVoucherTest {

	@Test
	void discount() {
		Arrays.stream(ModeType.values()).map(o-> o.toString()).forEach(a -> out.println());

	}
}