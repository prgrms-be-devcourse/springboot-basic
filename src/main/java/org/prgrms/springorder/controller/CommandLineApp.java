package org.prgrms.springorder.controller;

import static org.prgrms.springorder.domain.Message.*;
import static org.prgrms.springorder.domain.customer.CustomerType.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import org.prgrms.springorder.controller.customer.BlackListController;
import org.prgrms.springorder.controller.customer.CustomerController;
import org.prgrms.springorder.controller.dto.CustomerRequestDto;
import org.prgrms.springorder.controller.dto.VoucherResponseDto;
import org.prgrms.springorder.controller.voucher.VoucherController;
import org.prgrms.springorder.controller.wallet.WalletController;
import org.prgrms.springorder.domain.ErrorMessage;
import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.customer.CustomerType;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.prgrms.springorder.exception.NoSuchCommandException;
import org.prgrms.springorder.io.IO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApp {

	private final Logger logger = LoggerFactory.getLogger(CommandLineApp.class);
	private final IO io;
	private final VoucherController voucherController;
	private final CustomerController customerController;
	private final BlackListController blackListController;
	private final WalletController walletController;

	public CommandLineApp(IO io, VoucherController voucherController,
		CustomerController customerController, BlackListController blackListController, WalletController walletController) {
		this.io = io;
		this.voucherController = voucherController;
		this.customerController = customerController;
		this.blackListController = blackListController;
		this.walletController = walletController;
	}

	public void run() {
		ControllerStatus controllerStatus = new ControllerStatus();
		while (controllerStatus.isRunning()) {
			try {
				Command menu = Command.getOrder(io.read(MENU_MESSAGE));
				execute(menu, controllerStatus);
			} catch (RuntimeException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				io.write(e.getMessage());
			}
		}
	}

	private void execute(Command menu, ControllerStatus controllerStatus) {
		switch (menu) {
			case CREATE -> {
				VoucherType voucherType = VoucherType.getVoucherByOrder(io.read(SELECT_VOUCHER_MESSAGE));
				double value = Double.parseDouble(io.read(voucherType.getMessage()));
				voucherController.createVoucher(voucherType, value);
			}
			case JOIN -> {
				UUID customerId = UUID.randomUUID();
				String name = io.read(REQUEST_NAME_MESSAGE);
				String email = io.read(REQUEST_EMAIL_MESSAGE);
				CustomerRequestDto customerRequestDto = new CustomerRequestDto(customerId,name,email,LocalDateTime.now(),
					NORMAL);
				customerController.createCustomer(customerRequestDto);
			}
			case LIST -> io.writeList(voucherController.getVoucherList().stream().map(VoucherResponseDto::toString).collect(
				Collectors.toList()));
			case BLACK_LIST -> io.writeList(blackListController.getBlackList());
			case ALLOCATE -> {
				io.writeList(customerController.getCustomerList());
				UUID customerId = UUID.fromString(io.read(CUSTOMER_ID_MESSAGE));
				io.writeList(voucherController.getVoucherList().stream().map(VoucherResponseDto::toString).collect(
					Collectors.toList()));
				UUID voucherId = UUID.fromString(io.read(VOUCHER_ID_MESSAGE));
				walletController.allocateVoucher(customerId, voucherId);
			}
			case GET -> {
				io.writeList(customerController.getCustomerList());
				UUID customerId = UUID.fromString(io.read(CUSTOMER_ID_MESSAGE));
				io.writeList(walletController.getCustomerVouchers(customerId));
			}

			case DELETE -> {
				io.writeList(customerController.getCustomerList());
				UUID customerId = UUID.fromString(io.read(CUSTOMER_ID_MESSAGE));
				io.writeList(walletController.getCustomerVouchers(customerId));
				UUID voucherId = UUID.fromString(io.read(VOUCHER_ID_MESSAGE));
				walletController.deleteByVoucherId(customerId, voucherId);
			}

			case SEARCH ->{
				io.writeList(voucherController.getVoucherList().stream().map(VoucherResponseDto::toString).collect(
					Collectors.toList()));
				UUID voucherId = UUID.fromString(io.read(VOUCHER_ID_MESSAGE));
				io.write(walletController.findByVoucherId(voucherId).toString());

			}
			case EXIT -> controllerStatus.stop();
			default -> {
				throw new NoSuchCommandException(ErrorMessage.NO_SUCH_COMMAND_MESSAGE);
			}
		}
	}

}
