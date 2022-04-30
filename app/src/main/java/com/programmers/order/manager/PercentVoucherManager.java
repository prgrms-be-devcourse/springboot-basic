package com.programmers.order.manager;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.programmers.order.domain.PercentDiscountVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.domain.constraint.VoucherConstraint;
import com.programmers.order.dto.VoucherDto;
import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.type.VoucherType;

@Component
public class PercentVoucherManager implements VoucherManager {

	private static final Logger log = LoggerFactory.getLogger(PercentVoucherManager.class);

	private static final Pattern LIMIT_NUMERIC_PATTERN = Pattern.compile("^[0-9]{1,3}");
	private static final String NOT_DECISION = "";

	private final Input input;
	private final Output output;

	public PercentVoucherManager(Input input, Output output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public Voucher create() {
		boolean isReEnter = true;
		String percent = NOT_DECISION;

		while (isReEnter) {
			percent = input.read(BasicMessage.Voucher.PERCENT_VOUCHER_SELECT_MESSAGE);

			if (!this.isValidPercent(percent)) {
				log.info("error : {}", ErrorMessage.CLIENT_ERROR);
				output.write(ErrorMessage.CLIENT_ERROR);
				continue;
			}

			isReEnter = false;
		}

		return PercentDiscountVoucher.create(Long.parseLong(percent));
	}

	@Override
	public VoucherType getType() {
		return VoucherType.PERCENT_VOUCHER;
	}

	@Override
	public Voucher resolve(VoucherDto.Resolver resolver) {
		return PercentDiscountVoucher.build(resolver);
	}

	private boolean isValidPercent(String percent) {
		return LIMIT_NUMERIC_PATTERN.matcher(percent).matches() &&
				!VoucherConstraint.PERCENT_VOUCHER.isViolate(Long.parseLong(percent));
	}
}
