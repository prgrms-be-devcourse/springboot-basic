package org.prgrms.kdt;

import org.prgrms.kdt.order.property.OrderProperties;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.JDBCVoucherRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class KdtApplication {

	public static void main(String[] args) {
		SpringApplication.run(KdtApplication.class, args);

	}

}
