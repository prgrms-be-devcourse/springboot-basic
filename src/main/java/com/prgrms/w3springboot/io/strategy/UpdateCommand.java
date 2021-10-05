package com.prgrms.w3springboot.io.strategy;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prgrms.w3springboot.io.Input;
import com.prgrms.w3springboot.io.Output;
import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.voucher.service.VoucherService;

public class UpdateCommand implements CommandStrategy {
	private static final Logger logger = LoggerFactory.getLogger(UpdateCommand.class);

	@Override
	public boolean execute(Input input, Output output, VoucherService voucherService) {
		output.printUpdateVoucherChoice();
		String voucherId = input.input();
		output.printUpdateAmountChoice();
		String updateAmount = input.input();

		try {
			Voucher updatedVoucher = voucherService.updateVoucher(UUID.fromString(voucherId),
				Long.parseLong(updateAmount));
			output.printUpdatedVoucher(updatedVoucher);
		} catch (IllegalArgumentException e) {
			output.printInvalidMessage();
			logger.error("{} - voucherId : {}, newAmount : {}", e.getMessage(), voucherId, updateAmount);
		} catch (NoSuchElementException e) {
			output.printInvalidMessage();
			logger.error("{} - voucherId : {}", e.getMessage(), voucherId);
		}

		return true;
	}
}
