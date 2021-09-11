package org.programmers.kdt.voucher.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.programmers.kdt.voucher.FixedAmountVoucher;
import org.programmers.kdt.voucher.Voucher;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcVoucherRepositoryTest {
	List<Voucher> list = new ArrayList<>();

	DataSource dataSource = DataSourceBuilder.create()
			.url("jdbc:mysql://localhost:3306/order_mgmt")
			.username("root")
			.password("root1234!")
			.type(HikariDataSource .class)
			.build();
	VoucherRepository voucherRepository = new JdbcVoucherRepository(new NamedParameterJdbcTemplate(dataSource), "test_vouchers");


	@AfterAll
	void cleanup() {
		for (Voucher voucher : list) {
			voucherRepository.deleteVoucher(voucher);
		}
	}

	@Test
	@Order(1)
	@DisplayName("새 바우처 추가하기")
	void insert() {
		Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000);
		Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 2000);
		Voucher voucher3 = new FixedAmountVoucher(UUID.randomUUID(), 3000);

		list.add(voucher1);
		list.add(voucher2);
		list.add(voucher3);

		assertThat(voucherRepository.insert(voucher1)).isEqualTo(voucher1);
		assertThat(voucherRepository.insert(voucher2)).isEqualTo(voucher2);
		assertThat(voucherRepository.insert(voucher3)).isEqualTo(voucher3);
	}

	@Test
	@Order(2)
	@DisplayName("ID로 바우처 찾기")
	void findById() {
		Voucher voucher = list.get(0);
		Voucher temp = new FixedAmountVoucher(UUID.randomUUID(), 9999);
		assertThat(voucherRepository.findById(voucher.getVoucherId())).isEqualTo(Optional.of(voucher));
		assertThat(voucherRepository.findById(temp.getVoucherId())).isEmpty();
	}


	@Test
	@Order(3)
	@DisplayName("전체 바우처 조회하기")
	void findAll() {
		List<Voucher> allVoucher = voucherRepository.findAll();
		assertThat(allVoucher).contains(list.get(0));
		assertThat(allVoucher).contains(list.get(1));
		assertThat(allVoucher).contains(list.get(2));
	}

	@Test
	@Order(4)
	@DisplayName("바우처 삭제하기")
	void deleteVoucher() {
		Voucher voucher = list.get(0);
		assertThat(voucherRepository.findById(voucher.getVoucherId())).isPresent();
		voucherRepository.deleteVoucher(voucher);
		assertThat(voucherRepository.findById(voucher.getVoucherId())).isEmpty();
	}
}
