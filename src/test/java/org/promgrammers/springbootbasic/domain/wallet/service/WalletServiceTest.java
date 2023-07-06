package org.promgrammers.springbootbasic.domain.wallet.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.repository.impl.JdbcCustomerRepository;
import org.promgrammers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.repository.impl.JdbcVoucherRepository;
import org.promgrammers.springbootbasic.domain.wallet.dto.request.CreateWalletRequest;
import org.promgrammers.springbootbasic.domain.wallet.dto.response.WalletListResponse;
import org.promgrammers.springbootbasic.domain.wallet.dto.response.WalletResponse;
import org.promgrammers.springbootbasic.domain.wallet.repository.impl.JdbcWalletRepository;
import org.promgrammers.springbootbasic.exception.BusinessException;
import org.promgrammers.springbootbasic.exception.repository.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class WalletServiceTest {

    @Autowired
    private WalletService walletService;
    @Autowired
    private JdbcWalletRepository walletRepository;
    @Autowired
    JdbcCustomerRepository customerRepository;
    @Autowired
    JdbcVoucherRepository voucherRepository;

    private Voucher voucher;

    private Customer customer;

    @BeforeEach
    void setUp() {
        voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        customer = new Customer(UUID.randomUUID(), "HONG");
        voucherRepository.insert(voucher);
        customerRepository.save(customer);
    }

    @Test
    @DisplayName("지갑 저장 성공")
    void createWalletSuccessTest() throws Exception {

        //given
        CreateWalletRequest createWalletRequest = new CreateWalletRequest(voucher.getVoucherId(), customer.getCustomerId());

        //when
        WalletResponse walletResponse = walletService.create(createWalletRequest);

        //then
        assertNotNull(walletResponse.walletId());
        assertThat(walletResponse.customer().customerId()).isEqualTo(customer.getCustomerId());
        assertThat(walletResponse.voucher().voucherId()).isEqualTo(voucher.getVoucherId());
    }

    @Test
    @DisplayName("전체 지갑 조회 성공")
    void findAllWalletsSuccessTest() {

        //given
        List<CreateWalletRequest> walletRequests = List.of(
                new CreateWalletRequest(voucher.getVoucherId(), customer.getCustomerId()),
                new CreateWalletRequest(voucher.getVoucherId(), customer.getCustomerId())
        );
        walletRequests.forEach(walletService::create);


        //when
        WalletListResponse walletListResponse = walletService.findAll();

        //then
        assertNotNull(walletListResponse);
        assertThat(walletListResponse.walletList().size()).isEqualTo(2);
        List<WalletResponse> walletResponses = walletListResponse.walletList();

        for (int i = 0; i < walletRequests.size(); i++) {
            CreateWalletRequest walletRequest = walletRequests.get(i);
            WalletResponse walletResponse = walletResponses.get(i);

            assertThat(walletRequest.voucherId()).isEqualTo(walletResponse.voucher().voucherId());
            assertThat(walletRequest.customerId()).isEqualTo(walletResponse.customer().customerId());
        }
    }

    @Test
    @DisplayName("모든 지갑 조회 - 지갑 없음")
    void testFindAllWallets_NoWalletsExist() {

        //when -> Then
        assertThrows(BusinessException.class, () -> walletService.findAll());
    }

    @Test
    @DisplayName("지갑ID로 조회 성공 - 지갑 존재")
    void findWalletByIdSuccessTest() throws Exception {

        //given
        CreateWalletRequest walletRequest = new CreateWalletRequest(voucher.getVoucherId(), customer.getCustomerId());
        WalletResponse createdWallet = walletService.create(walletRequest);

        //when
        WalletResponse foundWallet = walletService.findById(createdWallet.walletId());

        //then
        assertThat(createdWallet.walletId()).isEqualTo(foundWallet.walletId());
        assertThat(walletRequest.voucherId()).isEqualTo(foundWallet.voucher().voucherId());
        assertThat(walletRequest.customerId()).isEqualTo(foundWallet.customer().customerId());
    }

    @Test
    @DisplayName("지갑ID로 조회 실패 - 존재하지 않는 지갑")
    void findWalletByIdFailTest() throws Exception {

        //when -> then
        assertThrows(BusinessException.class, () -> walletService.findById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("고객ID로 지갑 조회 성공 - 존재하는 지갑")
    void findWalletsByCustomerIdSuccessTest() throws Exception {

        //given
        List<CreateWalletRequest> walletRequests = List.of(
                new CreateWalletRequest(voucher.getVoucherId(), customer.getCustomerId()),
                new CreateWalletRequest(voucher.getVoucherId(), customer.getCustomerId())
        );
        walletRequests.forEach(walletService::create);

        //when
        WalletListResponse walletListResponse = walletService.findAllWalletByCustomerId(customer.getCustomerId());

        //then
        assertNotNull(walletListResponse.walletList());
        assertThat(walletListResponse.walletList().size()).isEqualTo(2);

    }

    @Test
    @DisplayName("고객ID로 지갑 조회 실패 - 지갑 없음")
    void findAllWalletsByCustomerIdFailTest() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();

        //when -> then
        assertThrows(BusinessException.class, () -> walletService.findAllWalletByCustomerId(customerId));
    }

    @Test
    @DisplayName("바우처ID로 지갑 조회 성공 - 존재하는 지갑")
    void findWalletsByVoucherIdSuccessTest() throws Exception {

        //given
        List<CreateWalletRequest> walletRequests = List.of(
                new CreateWalletRequest(voucher.getVoucherId(), customer.getCustomerId()),
                new CreateWalletRequest(voucher.getVoucherId(), customer.getCustomerId())
        );
        walletRequests.forEach(walletService::create);

        //when
        WalletListResponse walletListResponse = walletService.findAllWalletByVoucherId(voucher.getVoucherId());

        //then
        assertNotNull(walletListResponse.walletList());
        assertThat(walletListResponse.walletList().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("바우처ID로 지갑 조회 실패 - 지갑 없음")
    void findAllWalletsByVoucherIdFailTest() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();

        //when -> then
        assertThrows(BusinessException.class, () -> walletService.findAllWalletByCustomerId(voucherId));
    }

    @Test
    @DisplayName("지갑 전체 삭제 성공")
    void DeleteAllWalletsSuccessTest() {

        //given
        List<CreateWalletRequest> walletRequests = List.of(
                new CreateWalletRequest(voucher.getVoucherId(), customer.getCustomerId()),
                new CreateWalletRequest(voucher.getVoucherId(), customer.getCustomerId())
        );
        walletRequests.forEach(walletService::create);

        // when -> then
        assertDoesNotThrow(() -> walletService.deleteAll());
    }

    @Test
    @DisplayName("지갑 ID로 삭제 - 지갑 존재")
    void testDeleteWalletById_WalletExists() {

        // given
        CreateWalletRequest walletRequest = new CreateWalletRequest(voucher.getVoucherId(), customer.getCustomerId());
        WalletResponse createdWallet = walletService.create(walletRequest);

        // when
        walletService.deleteById(createdWallet.walletId());

        // then
        assertThrows(BusinessException.class, () -> walletService.findById(createdWallet.walletId()));
    }

    @Test
    @DisplayName("지갑 ID로 삭제 - 지갑 존재하지 않음")
    void testDeleteWalletById_WalletNotExists() {

        // when & then
        assertThrows(EntityNotFoundException.class, () -> walletService.deleteById(UUID.randomUUID()));
    }
}