package com.programmers.springbootbasic.domain.voucher.application;

import static com.programmers.springbootbasic.exception.ErrorCode.NOT_FOUND_VOUCHER;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.programmers.springbootbasic.common.TimeGenerator;
import com.programmers.springbootbasic.domain.TestTimeGenerator;
import com.programmers.springbootbasic.domain.TestVoucherIdGenerator;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherIdGenerator;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.CreateVoucherRequest;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.UpdateVoucherRequest;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.VoucherResponse;
import com.programmers.springbootbasic.exception.exceptionClass.VoucherException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

class VoucherServiceTest {

    @InjectMocks
    private VoucherService voucherService;
    @Mock
    private VoucherRepository voucherRepository;
    private VoucherIdGenerator idGenerator;
    private TimeGenerator timeGenerator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.idGenerator = new TestVoucherIdGenerator();
        ReflectionTestUtils.setField(voucherService, "idGenerator", idGenerator);
        this.timeGenerator = new TestTimeGenerator();
        ReflectionTestUtils.setField(voucherService, "timeGenerator", timeGenerator);
    }

    @DisplayName("유효한 정보의 바우처를 생성 할 수 있다.")
    @Test
    void success_create() {
        // given
        CreateVoucherRequest request = CreateVoucherRequest.of(VoucherTypeEnum.FIXED, 10);
        when(voucherRepository.save(any(Voucher.class))).thenReturn(
            new Voucher(idGenerator.generate(), request.getVoucherType(),
                request.getBenefitValue(), timeGenerator.now()));

        // when
        var result = voucherService.create(request);

        // then
        assertEquals(result, idGenerator.generate());
    }

    @DisplayName("모든 바우처를 조회 할 수 있다.")
    @Test
    void success_findAll() {
        // given
        when(voucherRepository.findAll()).thenReturn(
            List.of(
                new Voucher(idGenerator.generate(), VoucherTypeEnum.FIXED.getVoucherType(10), 10,
                    timeGenerator.now()),
                new Voucher(idGenerator.generate(), VoucherTypeEnum.PERCENT.getVoucherType(10), 10,
                    timeGenerator.now())
            )
        );

        // when
        var result = voucherService.findAll();

        // then
        assertEquals(2, result.size());
        for (Object item : result) {
            assertTrue(item instanceof VoucherResponse);
        }
    }

    @DisplayName("존재하는 바우처를 id로 조회 할 수 있다.")
    @Test
    void success_findById() {
        // given
        var voucher = new Voucher(idGenerator.generate(), VoucherTypeEnum.FIXED.getVoucherType(10),
            10, timeGenerator.now());
        when(voucherRepository.findById(idGenerator.generate())).thenReturn(
            java.util.Optional.of(voucher));

        // when
        var result = voucherService.findById(voucher.getId());

        // then
        assertEquals(result.getId(), voucher.getId());
    }

    @DisplayName("존재하지 않는 바우처를 id로 조회 하려 하면 예외를 발생한다.")
    @Test
    void fail_findById() {
        // given
        when(voucherRepository.findById(any())).thenReturn(java.util.Optional.empty());

        // when && then
        assertThatThrownBy(() -> voucherService.findById(idGenerator.generate()))
            .isInstanceOf(VoucherException.class)
            .hasMessage(NOT_FOUND_VOUCHER.getMessage());
    }

    @DisplayName("존재하는 바우처를 id로 삭제 할 수 있다.")
    @Test
    void success_deleteById() {
        // given
        var voucher = new Voucher(idGenerator.generate(), VoucherTypeEnum.FIXED.getVoucherType(10),
            10, timeGenerator.now());
        when(voucherRepository.findById(voucher.getId())).thenReturn(Optional.of(voucher));
        when(voucherRepository.deleteById(voucher.getId())).thenReturn(1);

        // when && then
        assertThatCode(() -> voucherService.deleteById(voucher.getId()))
            .doesNotThrowAnyException();
    }

    @DisplayName("존재하지 않는 바우처를 id로 삭제 하려 하면 예외를 발생한다.")
    @Test
    void fail_deleteById() {
        // given
        when(voucherRepository.findById(any())).thenReturn(java.util.Optional.empty());

        // when && then
        assertThatThrownBy(() -> voucherService.deleteById(idGenerator.generate()))
            .isInstanceOf(VoucherException.class)
            .hasMessageContaining(NOT_FOUND_VOUCHER.getMessage());
    }

    @DisplayName("존재하는 바우처를 유효한 데이터로 수정 할 수 있다.")
    @Test
    void success_update() {
        // given
        var voucher = new Voucher(idGenerator.generate(), VoucherTypeEnum.FIXED.getVoucherType(10),
            10, timeGenerator.now());
        UpdateVoucherRequest request = new UpdateVoucherRequest(VoucherTypeEnum.PERCENT, 10);
        when(voucherRepository.findById(voucher.getId())).thenReturn(
            java.util.Optional.of(voucher));
        when(voucherRepository.update(any(Voucher.class))).thenReturn(1);

        // when
        var result = voucherService.update(voucher.getId(), request);

        // then
        assertEquals(result, voucher.getId());
    }

    @DisplayName("존재하지 않는 바우처를 유효한 데이터로 수정 하려 하면 예외를 발생한다.")
    @Test
    void fail_update_voucherNotFound() {
        // given
        UpdateVoucherRequest request = new UpdateVoucherRequest(VoucherTypeEnum.PERCENT, 10);
        when(voucherRepository.findById(any())).thenReturn(java.util.Optional.empty());

        // when && then
        assertThatThrownBy(() -> voucherService.update(idGenerator.generate(), request))
            .isInstanceOf(VoucherException.class)
            .hasMessageContaining(NOT_FOUND_VOUCHER.getMessage());
    }

}
