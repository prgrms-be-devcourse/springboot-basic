package com.programmers.order.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.type.MenuType;
import com.programmers.order.type.VoucherType;

@Component
public class VoucherController {
	private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

	private final Input input;
	private final Output output;

	public VoucherController(Input input, Output output) {
		this.input = input;
		this.output = output;
	}

	public void start() throws IOException {
		while (true) {
			try {
				String menu = input.read(BasicMessage.INIT);
				MenuType menuType = MenuType.getMenuType(menu);

				switch (menuType) {
					case CREATE -> {
						String voucher = input.read(BasicMessage.VOUCHER_SELECT);
						VoucherType voucherType = VoucherType.getVoucherType(voucher);

						// todo : refactoring 필요 추상 팩토리 메소드로 관리 할 예정
						switch (voucherType) {
							case FIX_VOUCHER -> {

							}
							case PERCENT_VOUCHER -> {

							}
							case NONE -> {
								output.write(ErrorMessage.CLIENT_ERROR);
								continue;
							}
						}

					}
					case LIST -> {

					}
					case EXIT -> {
						output.write(BasicMessage.EXIT);

						return;
					}
					case NONE -> {
						output.write(ErrorMessage.CLIENT_ERROR);
						continue;
					}
				}

				output.write(BasicMessage.NEW_LINE);
			} catch (IOException exception) {
				exception.printStackTrace();
				logger.warn("{}", ErrorMessage.INTERNAL_PROGRAM_ERROR);
			}
		}

	}
}
