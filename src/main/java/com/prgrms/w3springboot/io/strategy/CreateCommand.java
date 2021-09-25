package com.prgrms.w3springboot.io.strategy;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prgrms.w3springboot.io.Input;
import com.prgrms.w3springboot.io.Output;
import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.voucher.VoucherType;
import com.prgrms.w3springboot.voucher.service.VoucherService;

public class CreateCommand implements CommandStrategy {
	private static final Logger logger = LoggerFactory.getLogger(CreateCommand.class);

	@Override
	public boolean execute(Input input, Output output, VoucherService voucherService) {
		boolean flag = true;
		while (flag) {
			output.printTypeChoice();
			String voucherType = input.input();
			output.printDiscountAmountChoice();
			String discountAmount = input.input();

			Voucher createdVoucher;
			try {
				createdVoucher = voucherService.createVoucher(UUID.randomUUID(), Long.parseLong(discountAmount),
					VoucherType.of(voucherType), LocalDateTime.now());
				output.printCreatedVoucher(createdVoucher);
				flag = false;
			} catch (NumberFormatException e) {
				logger.error("잘못된 형식을 입력받았습니다. - input : {}", discountAmount);
			} catch (IllegalArgumentException e) {
				output.printInvalidMessage();
				logger.error("{} - type : {}, amount : {}", e.getMessage(), voucherType, discountAmount);
			}
		}

		return true;
	}
}
