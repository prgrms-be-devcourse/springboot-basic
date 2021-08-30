package org.prgrms.kdt.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.common.BaseRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 8:02 오후
 */

class VoucherJdbcRepositoryTest extends BaseRepositoryTest {

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    @Test
    @DisplayName("바우처 저장 테스트")
    void insert() {
        voucherJdbcRepository.insert(Voucher.of(1000L, VoucherType.FIX));

        int count = voucherJdbcRepository.count();

        assertThat(count).isEqualTo(1);
    }

}