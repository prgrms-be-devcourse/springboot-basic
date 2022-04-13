package com.programmers.order.manager;

import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.programmers.order.domain.PercentDiscountVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.manager.store.StoreManager;
import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.type.VoucherType;

@Component
public class PercentVoucherManager implements VoucherManager {

	private static final Logger log = LoggerFactory.getLogger(PercentVoucherManager.class);

	private static final Pattern LIMIT_NUMERIC_PATTERN = Pattern.compile("^[0-9]{1,3}");
	private static final int LIMIT_PERCENT = 100;
	private static final int MINIMUM_PERCENT = 1;

	private final Input input;
	private final Output output;

	public PercentVoucherManager(Input input, Output output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public Voucher create() {
		while (true) {
			String percent = input.read(BasicMessage.FIX_VOUCHER_SELECT_MESSAGE);

			if (!this.isValidPercent(percent)) {
				log.info("error : {}", ErrorMessage.CLIENT_ERROR);
				continue;
			}

			return new PercentDiscountVoucher(UUID.randomUUID(),
					Long.parseLong(percent));
		}
	}

	@Override
	public VoucherType getType() {
		return VoucherType.PERCENT_VOUCHER;
	}

	private boolean isValidPercent(String percent) {
		return LIMIT_NUMERIC_PATTERN.matcher(percent).matches() && Integer.parseInt(percent) <= LIMIT_PERCENT
				&& Integer.parseInt(percent) >= MINIMUM_PERCENT;
	}
}
