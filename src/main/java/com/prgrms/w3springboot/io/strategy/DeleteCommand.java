package com.prgrms.w3springboot.io.strategy;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prgrms.w3springboot.io.Input;
import com.prgrms.w3springboot.io.Output;
import com.prgrms.w3springboot.voucher.service.VoucherService;

public class DeleteCommand implements CommandStrategy {
	private static final Logger logger = LoggerFactory.getLogger(CreateCommand.class);

	@Override
	public boolean execute(Input input, Output output, VoucherService voucherService) {
		output.printDeleteChoice();
		UUID voucherId = UUID.fromString(input.input());

		try {
			voucherService.deleteVoucher(voucherId);
			output.printDeleteDone();
		} catch (NoSuchElementException e) {
			output.printInvalidMessage();
			logger.error("{} - voucher ID : {}", e.getMessage(), voucherId);
		}

		return true;
	}
}
