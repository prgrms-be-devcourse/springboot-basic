package com.programmers.application.repository.voucher;

import com.programmers.application.domain.voucher.Voucher;
import com.programmers.application.domain.voucher.VoucherFactory;
import com.programmers.application.dto.request.RequestFactory;
import com.programmers.application.dto.request.VoucherCreationRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
//@Rollback(value = false)
class JdbcVoucherRepositoryTest {

    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    @DisplayName("옳바른 바우처 타입과 할인양 입력 시, save()를 실행하면 테스트가 성공한다.")
    @ParameterizedTest
    @CsvSource(value = {"fixed, 100", "percent, 10"})
    void save(String voucherType, long discountAmount) {
        //given
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(voucherType, discountAmount);
        Voucher voucher = VoucherFactory.createVoucher(voucherCreationRequest);

        //when
        Voucher savedVoucher = jdbcVoucherRepository.save(voucher);

        //then
        Assertions.assertThat(voucher).isEqualTo(savedVoucher);
    }
}
