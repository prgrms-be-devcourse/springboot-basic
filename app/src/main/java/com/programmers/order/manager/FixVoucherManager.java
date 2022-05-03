package com.programmers.order.manager;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.programmers.order.domain.FixedAmountVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.domain.constraint.VoucherConstraint;
import com.programmers.order.dto.VoucherDto;
import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.message.LogMessage;
import com.programmers.order.type.VoucherType;

@Component
public class FixVoucherManager implements VoucherManager {

	private static final Logger log = LoggerFactory.getLogger(FixVoucherManager.class);

	private static final Pattern LIMIT_NUMERIC_PATTERN = Pattern.compile("^[0-9]{1,9}");
	private static final String NOT_DECISION = "";

	private final Input input;
	private final Output output;

	public FixVoucherManager(Input input, Output output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public Voucher create() {
		boolean isRunnable = true;
		String fixPrice = NOT_DECISION;

		while (isRunnable) {
			fixPrice = input.read(BasicMessage.Voucher.FIX_VOUCHER_SELECT_MESSAGE);

			if (!this.isValidPrice(fixPrice)) {
				log.info(LogMessage.InfoLogMessage.getPrefix(), LogMessage.InfoLogMessage.NOT_VALID_INPUT);
				output.write(ErrorMessage.CLIENT_ERROR);
				continue;
			}

			isRunnable = false;
		}

		return FixedAmountVoucher.create(Long.parseLong(fixPrice));
	}

	@Override
	public VoucherType getType() {
		return VoucherType.FIX_VOUCHER;
	}

	@Override
	public Voucher resolve(VoucherDto.Resolver resolver) {
		return FixedAmountVoucher.build(resolver);
	}

	private boolean isValidPrice(String fixPrice) {
		return LIMIT_NUMERIC_PATTERN.matcher(fixPrice).matches() &&
				!VoucherConstraint.FIX_VOUCHER.isViolate(Long.parseLong(fixPrice));
	}
}
