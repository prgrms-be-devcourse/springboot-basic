package com.programmers.order.manager;

import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.programmers.order.domain.FixedAmountVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.type.VoucherType;

@Component
public class FixVoucherManager implements VoucherManager {

	private static final Logger log = LoggerFactory.getLogger(FixVoucherManager.class);

	private static final Pattern LIMIT_NUMERIC_PATTERN = Pattern.compile("^[0-9]{1,9}");
	private static final int MAXIMUM_FIX = 100_000_000;
	private static final int MINIMUM_FIX = 1;
	private static final String NOT_DECISION = "";

	private final Input input;
	private final Output output;

	public FixVoucherManager(Input input, Output output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public Voucher create() {
		boolean isReEnter = true;
		String fixPrice = NOT_DECISION;

		while (isReEnter) {
			fixPrice = input.read(BasicMessage.FIX_VOUCHER_SELECT_MESSAGE);

			if (!this.isValidPrice(fixPrice)) {
				log.info("error : {}", ErrorMessage.CLIENT_ERROR);
				output.write(ErrorMessage.CLIENT_ERROR);
				continue;
			}

			isReEnter = false;
		}

		return new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(fixPrice));
	}

	@Override
	public VoucherType getType() {
		return VoucherType.FIX_VOUCHER;
	}

	private boolean isValidPrice(String fixPrice) {
		return LIMIT_NUMERIC_PATTERN.matcher(fixPrice).matches() && Integer.parseInt(fixPrice) <= MAXIMUM_FIX
				&& Integer.parseInt(fixPrice) >= MINIMUM_FIX;
	}
}
