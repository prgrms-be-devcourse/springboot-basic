package com.example.voucherproject.wallet;

import com.example.voucherproject.TestRepositoryConfig;
import com.example.voucherproject.wallet.model.Wallet;
import com.example.voucherproject.wallet.repository.WalletJdbcRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.voucherproject.common.Helper.WalletHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@SpringJUnitConfig(classes = TestRepositoryConfig.class)
@DisplayName("Wallet Test")
class WalletJdbcRepositoryTest {

    @Autowired
    WalletJdbcRepository walletJdbcRepository;

    @Nested
    @DisplayName("INSERT TEST")
    class InsertVoucherTest {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 10})
        @DisplayName("[insert:성공] 지갑 N개 정상 저장")
        void basicInsertTest(int n) {
            List<Wallet> wallets = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                wallets.add(walletJdbcRepository.insert(makeWallet()));
            }
            var retrievedWallets = walletJdbcRepository.findAll();
            assertThat(retrievedWallets).usingRecursiveFieldByFieldElementComparator().containsAll(wallets);
            assertThat(retrievedWallets.size()).isEqualTo(n);
        }
    }

    @Nested
    @DisplayName("FIND TEST")
    class FindVoucherTest {

        @Test
        @DisplayName("[findById:성공] 지갑의 ID로 해당하는 지갑 조회 가능")
        void findByIdTest(){
            var wallet = walletJdbcRepository.insert(makeWallet());
            var searchedWallet = walletJdbcRepository.findById(wallet.getId());
            assertThat(searchedWallet.get()).usingRecursiveComparison().isEqualTo(wallet);
        }
        @Test
        @DisplayName("[findById:실패] 존재하지 않는 지갑의 ID는 지갑 조회 불가능")
        void findByIdFailTest(){
            UUID noWalletId = UUID.randomUUID();
            var searchedWallet = walletJdbcRepository.findById(noWalletId);
            assertThat(searchedWallet.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("[findByIds:성공] 유저와 바우처 ID로 지갑 조회 가능")
        void findByIdsTest() {
            UUID userId = UUID.randomUUID();
            UUID voucherId = UUID.randomUUID();
            var wallet = makeWalletTwoIds(userId, voucherId);
            walletJdbcRepository.insert(wallet);
            var retrievedWallet = walletJdbcRepository.findByIds(userId, voucherId);
            assertThat(retrievedWallet.get()).usingRecursiveComparison().isEqualTo(wallet);
        }

        @Test
        @DisplayName("[findByUserId:성공] 유저 ID로 지갑 다수 조회 가능")
        void findAllByUserIdTest() {
            walletJdbcRepository.insert(makeWallet());
            walletJdbcRepository.insert(makeWallet());
            walletJdbcRepository.insert(makeWallet());

            UUID userId = UUID.randomUUID();
            var walletTarget1 = makeWalletTwoIds(userId, UUID.randomUUID());
            var walletTarget2 = makeWalletTwoIds(userId, UUID.randomUUID());

            walletJdbcRepository.insert(walletTarget1);
            walletJdbcRepository.insert(walletTarget2);

            var searchedWallets = walletJdbcRepository.findAllByUserId(userId);

            assertThat(searchedWallets).usingRecursiveFieldByFieldElementComparator()
                    .contains(walletTarget1, walletTarget2);
        }
        @Test
        @DisplayName("[findByVoucherId:성공] 유저 ID로 지갑 다수 조회 가능")
        void findAllByVoucherIdTest() {
            walletJdbcRepository.insert(makeWallet());
            walletJdbcRepository.insert(makeWallet());
            walletJdbcRepository.insert(makeWallet());

            UUID voucherId = UUID.randomUUID();
            var walletTarget1 = makeWalletTwoIds(UUID.randomUUID(), voucherId);
            var walletTarget2 = makeWalletTwoIds(UUID.randomUUID(), voucherId);
            walletJdbcRepository.insert(walletTarget1);
            walletJdbcRepository.insert(walletTarget2);

            var searchedWallets = walletJdbcRepository.findAllByVoucherId(voucherId);

            assertThat(searchedWallets).usingRecursiveFieldByFieldElementComparator()
                    .contains(walletTarget1, walletTarget2);
        }

    }

    @Nested
    @DisplayName("DELETE TEST")
    class DeleteVoucherTest {
        @Test
        @DisplayName("[deleteById:성공] 지갑 ID로 해당하는 지갑 삭제 가능")
        void deleteByIdTest() {

            var wallet = makeWallet();
            walletJdbcRepository.insert(wallet);
            walletJdbcRepository.deleteById(wallet.getId());

            var wallets = walletJdbcRepository.findAll();
            assertThat(wallets).usingRecursiveFieldByFieldElementComparator().doesNotContain(wallet);
        }
        @Test
        @DisplayName("[deleteAll:성공] 모든 지갑 삭제후 Repository 개수는 0개")
        void deleteAllTest() {
            walletJdbcRepository.insert(makeWallet());
            walletJdbcRepository.insert(makeWallet());
            walletJdbcRepository.insert(makeWallet());
            assertThat(walletJdbcRepository.count()).isEqualTo(3);
            walletJdbcRepository.deleteAll();
            assertThat(walletJdbcRepository.count()).isEqualTo(0);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3})
    @DisplayName("COUNT TEST")
    void countTest(int n){
        List<Wallet> wallets = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            wallets.add(walletJdbcRepository.insert(makeWallet()));
        }
        assertThat(walletJdbcRepository.count()).isEqualTo(n);
    }

    @AfterEach
    void cleanUpAfterEach() {
        walletJdbcRepository.deleteAll();
    }
    @BeforeEach
    void cleanUpBeforeEach() {
        walletJdbcRepository.deleteAll();
    }
}