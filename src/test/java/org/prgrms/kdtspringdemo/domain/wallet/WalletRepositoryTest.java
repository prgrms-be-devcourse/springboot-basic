package org.prgrms.kdtspringdemo.domain.wallet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WalletRepositoryTest {

    @Test
    @DisplayName("CustomerId로 바우처를 조회 성공")
    void finddVouchersByCustomerIdSucess() {
    }

    @Test
    @DisplayName("CustomerId로 바우처를 조회 성공[바우쳐가 0개]")
    void findVouchersByCustomerIdSucessNoVoucher() {
    }

    @Test
    @DisplayName("CustomerId로 바우처를 조회 실패[잘못된 ID]")
    void findVouchersByCustomerIdFailWrongId() {
    }

    @Test
    @DisplayName("VoucherId 로 CustomerId 조회")
    void findCustomersByVoucherId() {
    }

    @Test
    @DisplayName("customer에 Voucher 추가")
    void addVoucherToCustomer() {
    }

    @Test
    @DisplayName("customer로부터voucher 제거")
    void deleteVoucherFromCustomer() {
    }
}