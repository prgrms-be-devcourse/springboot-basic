package org.prgrms.kdt;

import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.assignments.CreateVoucher;
import org.prgrms.kdt.assignments.VoucherData;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ComponentScan( basePackages = {"org.kdt.prgrms.assignments", "org.kdt.prgrms.voucher", "org.kdt.prgrms.configuration"})
class KdtApplicationTests {

	@Autowired
	CreateVoucher createVoucher;

	@Autowired
	VoucherService voucherService;

	@Test
	@DisplayName("생성하고자 하는 바우처의 타입이 같아야한다")
	void creaVoucherTest() {
		var voucherData = new VoucherData(1,20);
		var voucher = createVoucher.create(voucherData);
		assertThat(voucher.getClass(), is(FixedAmountVoucher.class));
	}

}
