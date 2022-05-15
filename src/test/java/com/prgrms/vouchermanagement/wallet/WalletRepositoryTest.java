package com.prgrms.vouchermanagement.wallet;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.vouchermanagement.voucher.domain.Voucher;

@SpringBootTest
@Transactional
public class WalletRepositoryTest {

	@Autowired
	WalletJdbcRepository walletRepository;

	private UUID existingCustomerId1;
	private UUID existingCustomerId2;
	private UUID existingVoucherId1;
	private UUID existingVoucherId2;

	@BeforeEach
	public void setup() {
		existingCustomerId1 = UUID.fromString("acb8ea46-c64b-11ec-8ac0-86fc60ea758b");
		existingCustomerId2 = UUID.fromString("98bcb05e-c64b-11ec-8ac0-86fc60ea758b");
		existingVoucherId1 = UUID.fromString("a3a65384-c5e8-11ec-8ac0-86fc60ea758b");
		existingVoucherId2 = UUID.fromString("b981ec04-c5e8-11ec-8ac0-86fc60ea758b");
	}

	@Test
	@DisplayName("해당 커스토머가 가진 바우처 리스트를 받아올 수 있다")
	public void given_customer_when_findAllVouchers_thenAllVouchersFetched() {
		List<UUID> voucherIdList = walletRepository.findAllVouchersOfCustomer(existingCustomerId1)
			.stream()
			.map(v -> v.getVoucherId())
			.collect(Collectors.toList());

		assertThat(voucherIdList, contains(existingVoucherId1, existingVoucherId2));
	}

	@Test
	@DisplayName("해당 커스토머가 갖지 않은 바우처는 findAllVouchers 에 포함되어있지 않다 ")
	public void given_customer_when_tryToFindNewVoucherInVouchersFoundByCallingFindAllVouchers_then_fail() {
		UUID randomVoucherId = UUID.randomUUID();

		List<UUID> voucherIdList = walletRepository.findAllVouchersOfCustomer(existingCustomerId1)
			.stream()
			.map(v -> v.getVoucherId())
			.collect(Collectors.toList());

		assertThat(voucherIdList, not(hasItem(randomVoucherId)));
	}

	@Test
	@DisplayName("customerId 와 voucherId 를 받아 테이블에 넣는다")
	public void given_customerAndVoucher_when_save_thenSuccess() {
		int insertCnt = walletRepository.insertRecord(existingCustomerId2, existingVoucherId2);

		assertThat(insertCnt, is(1));

		Voucher voucher1 = walletRepository.findVoucherOfCustomer(existingCustomerId2, existingVoucherId2)
			.get();// 이거로 찾아진다.

		assertThat(voucher1.getVoucherId(), is(existingVoucherId2));
	}

	@Test
	@DisplayName("존재하지 않는 커스토머-바우처에 대한 정보 삭제 요청 시 예외가 발생하지는 않는다")
	public void try_givenVoucher_when_tryDeleteVoucher_thenFail() {
		UUID randomCustomer = UUID.randomUUID();
		UUID randomVoucher = UUID.randomUUID();

		walletRepository.deleteRecord(randomCustomer, randomVoucher);
	}

	@Test
	@DisplayName("존재하 커스토머-바우처에 대한 정보 삭제 요청 시 하나의 row 가 삭제된다")
	public void try_givenVoucher_when_tryDeleteVoucher_thenOneRowReturned() {
		long deletedCnt = walletRepository.deleteRecord(existingCustomerId1, existingVoucherId1);

		assertThat(deletedCnt, is(1L));
	}

	@Test
	@DisplayName("존재하지 않는 커스토머-바우처에 대하여  findVoucherOfCustomer 호출 결과 empty Optional 을 리턴한다")
	public void given_notExistingCustomerVoucher_when_findVoucherOfCustomer_then_returnEmptyOptional() {
		UUID customerId = UUID.randomUUID();
		UUID voucherId = UUID.randomUUID();

		Optional<Voucher> voucherOfCustomer = walletRepository.findVoucherOfCustomer(customerId, voucherId);

		assertThat(voucherOfCustomer.isPresent(), is(false));
	}

	@Test
	@DisplayName("존재하는 커스토머-바우처에 대하여  findVoucherOfCustomer 호출 결과 값이 들어있는 Optional 을 리턴한다")
	public void given_existingCustomerVoucher_when_compareIdOfExistingCustomerWithFoundVoucherOfCustomer_thenSame() {
		Optional<Voucher> voucherOfCustomer = walletRepository.findVoucherOfCustomer(existingCustomerId1,
			existingVoucherId1);
		Voucher voucher = voucherOfCustomer.get();

		assertThat(voucher.getVoucherId(), is(existingVoucherId1));
	}
}
