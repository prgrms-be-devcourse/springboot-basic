package com.example.voucher.utils.lineAggregator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.voucher.domain.FixedAmountVoucher;
import com.example.voucher.domain.PercentDiscountVoucher;
import com.example.voucher.domain.Voucher;

class SpaceLineAggregatorTest {

	LineAggregator lineAggregator;

	@BeforeEach
	void setup() {
		lineAggregator = new SpaceLineAggregator();
	}

	@Test
	void aggregate() {
		List<Voucher> vouchers = new ArrayList<>();
		vouchers.add(new FixedAmountVoucher(UUID.randomUUID(), 30L));
		vouchers.add(new PercentDiscountVoucher(UUID.randomUUID(), 10L));

		for (Voucher voucher : vouchers) {
			System.out.println(lineAggregator.aggregate(voucher));
		}

	}
}