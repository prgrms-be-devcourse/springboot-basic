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
import com.programmers.order.manager.store.StoreManager;
import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.type.VoucherType;

@Component
public class FixVoucherManager implements VoucherManager {

	private static final Logger log = LoggerFactory.getLogger(FixVoucherManager.class);

	private static final Pattern LIMIT_NUMERIC_PATTERN = Pattern.compile("^[0-9]{1,9}");
	private static final int MAXIMUM_FIX = 100_000_000;
	private static final int MINIMUM_FIX = 1;

	private final Input input;
	private final Output output;
	private final StoreManager storeManager;

	public FixVoucherManager(Input input, Output output, StoreManager storeManager) {
		this.input = input;
		this.output = output;
		this.storeManager = storeManager;
	}

	@Override
	public Voucher create() {
		while (true) {
			String fixPrice = input.read(BasicMessage.FIX_VOUCHER_SELECT_MESSAGE);

			if (this.isValidPrice(fixPrice)) {
				log.info("error : {}", ErrorMessage.CLIENT_ERROR);
				continue;
			}

			FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(fixPrice));

			return storeManager.saveVoucher(fixedVoucher);
		}
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
