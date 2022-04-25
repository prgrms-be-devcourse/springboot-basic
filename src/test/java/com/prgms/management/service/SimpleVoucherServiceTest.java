package com.prgms.management.service;

import com.prgms.management.common.exception.DeleteFailException;
import com.prgms.management.common.exception.FindFailException;
import com.prgms.management.common.exception.SaveFailException;
import com.prgms.management.voucher.model.PercentDiscountVoucher;
import com.prgms.management.voucher.model.Voucher;
import com.prgms.management.voucher.model.VoucherType;
import com.prgms.management.voucher.repository.VoucherRepository;
import com.prgms.management.voucher.service.SimpleVoucherService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleVoucherServiceTest {

    @Mock
    private VoucherRepository voucherRepository;

    @InjectMocks
    private SimpleVoucherService voucherService;

    @DisplayName("addVoucher() : 바우처 저장 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class AddVoucherTest {
        @DisplayName("성공 : 레포지토리 단에서 바우처가 정상적으로 저장되었습니다.")
        @Test
        void addSuccess() {
            // given
            Voucher voucher = new PercentDiscountVoucher(20);
            when(voucherRepository.save(any(Voucher.class))).thenReturn(voucher);
            // when
            Voucher result = voucherService.addVoucher(voucher);
            // then
            assertThat(result.getFigure(), is(voucher.getFigure()));
            verify(voucherRepository, only()).save(voucher);
        }

        @DisplayName("실패 : 레포지토리 단에서 저장에 실패한 경우 SaveFailException 예외가 발생합니다.")
        @Test
        void addFail() {
            // given
            Voucher voucher = new PercentDiscountVoucher(20);
            when(voucherRepository.save(any(Voucher.class))).thenThrow(new SaveFailException());
            // when, then
            assertThrows(SaveFailException.class, () -> voucherService.addVoucher(voucher));
            verify(voucherRepository, only()).save(voucher);
        }
    }

    @DisplayName("findVouchers() : 바우처 목록 조회 테스트")
    @Nested
    class FindVouchersTest {
        @DisplayName("성공 : type, start, end가 null인 경우 전체 고객 리스트가 반환됩니다.")
        @Test
        void findAllSuccess() {
            // given
            List<Voucher> expectedList = new ArrayList<>();
            when(voucherRepository.findAll()).thenReturn(expectedList);
            // when
            List<Voucher> resultList = voucherService.findVouchers(null, null, null);
            // then
            assertThat(resultList, equalTo(expectedList));
            verify(voucherRepository, only()).findAll();
        }

        @DisplayName("성공 : start, end 만 주어진 경우 날짜에 따른 고객 리스트가 반환됩니다.")
        @Test
        void findByDateSuccess() {
            // given
            Timestamp start = Timestamp.valueOf(LocalDateTime.now());
            Timestamp end = Timestamp.valueOf(LocalDateTime.now());
            List<Voucher> expectedList = new ArrayList<>();
            when(voucherRepository.findByDate(any(Timestamp.class), any(Timestamp.class))).thenReturn(expectedList);
            // when
            List<Voucher> resultList = voucherService.findVouchers(null, start, end);
            // then
            assertThat(resultList, equalTo(expectedList));
            verify(voucherRepository, only()).findByDate(start, end);
        }

        @DisplayName("성공 : type만 주어진 경우 타입에 따른 고객 리스트가 반환됩니다.")
        @Test
        void findByTypeSuccess() {
            // given
            List<Voucher> expectedList = new ArrayList<>();
            when(voucherRepository.findByType(any(VoucherType.class))).thenReturn(expectedList);
            // when
            List<Voucher> resultList = voucherService.findVouchers(VoucherType.FIXED, null, null);
            // then
            assertThat(resultList, equalTo(expectedList));
            verify(voucherRepository, only()).findByType(VoucherType.FIXED);
        }

        @DisplayName("성공 : type, start, end이 모두 주어진 경우 전체 고객 리스트가 반환됩니다.")
        @Test
        void findByTypeAndDateSuccess() {
            // given
            Timestamp start = Timestamp.valueOf(LocalDateTime.now());
            Timestamp end = Timestamp.valueOf(LocalDateTime.now());
            List<Voucher> expectedList = new ArrayList<>();
            when(voucherRepository.findByTypeAndDate(any(VoucherType.class), any(Timestamp.class),
                any(Timestamp.class))).thenReturn(expectedList);
            // when
            List<Voucher> resultList = voucherService.findVouchers(VoucherType.FIXED, start, end);
            // then
            assertThat(resultList, equalTo(expectedList));
            verify(voucherRepository, times(1)).findByTypeAndDate(VoucherType.FIXED, start, end);
        }

        @DisplayName("실패 : type, start, end가 null이지만 조회에 실패한 경우 FindFailException 예외가 발생합니다.")
        @Test
        void findAllFail() {
            // given
            when(voucherRepository.findAll()).thenThrow(new FindFailException());
            // when, then
            assertThrows(FindFailException.class, () -> voucherService.findVouchers(null, null, null));
            verify(voucherRepository, only()).findAll();
        }

        @DisplayName("실패 : start, end만 주어졌지만 조회에 실패한 경우 FindFailException 예외가 발생합니다.")
        @Test
        void findByDateFail() {
            // given
            Timestamp start = Timestamp.valueOf(LocalDateTime.now());
            Timestamp end = Timestamp.valueOf(LocalDateTime.now());
            when(voucherRepository.findByDate(any(Timestamp.class), any(Timestamp.class))).thenThrow(new FindFailException());
            // when, then
            assertThrows(FindFailException.class, () -> voucherService.findVouchers(null, start, end));
            verify(voucherRepository, only()).findByDate(start, end);
        }

        @DisplayName("실패 : type만 주어졌지만 조회에 실패한 경우 FindFailException 예외가 발생합니다.")
        @Test
        void findByTypeFail() {
            // given
            when(voucherRepository.findByType(any(VoucherType.class))).thenThrow(new FindFailException());
            // when, then
            assertThrows(FindFailException.class, () -> voucherService.findVouchers(VoucherType.FIXED, null, null));
            verify(voucherRepository, only()).findByType(VoucherType.FIXED);
        }

        @DisplayName("실패 : type, start, end이 모두 주어졌지만 조회에 실패한 경우 FindFailException 예외가 발생합니다.")
        @Test
        void findByTypeAndDateFail() {
            // given
            Timestamp start = Timestamp.valueOf(LocalDateTime.now());
            Timestamp end = Timestamp.valueOf(LocalDateTime.now());
            when(voucherRepository.findByTypeAndDate(any(VoucherType.class), any(Timestamp.class),
                any(Timestamp.class))).thenThrow(new FindFailException());
            // when, then
            assertThrows(FindFailException.class, () -> voucherService.findVouchers(VoucherType.FIXED, start, end));
            verify(voucherRepository, times(1)).findByTypeAndDate(VoucherType.FIXED, start, end);
        }
    }

    @DisplayName("findVoucherById() : ID로 바우처 조회 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class FindVoucherByIdTest {
        @DisplayName("성공 : 레포지토리 단에서 아이디 조회에 성공한 경우 ID에 따른 바우처를 반환합니다.")
        @Test
        void findSuccess() {
            // given
            Voucher voucher = new PercentDiscountVoucher(20);
            when(voucherRepository.findById(any(UUID.class))).thenReturn(voucher);
            // when
            Voucher result = voucherService.findVoucherById(voucher.getId());
            // then
            assertThat(result.getId(), is(voucher.getId()));
            verify(voucherRepository, only()).findById(voucher.getId());
        }

        @DisplayName("실패 : 레포지토리 단에서 아이디 조회에 실패한 경우 FindFailException 예외가 발생합니다.")
        @Test
        void findFail() {
            // given
            UUID undefinedId = UUID.randomUUID();
            when(voucherRepository.findById(any(UUID.class))).thenThrow(new FindFailException());
            // when, then
            assertThrows(FindFailException.class, () -> voucherService.findVoucherById(undefinedId));
            verify(voucherRepository, only()).findById(undefinedId);
        }
    }

    @DisplayName("removeVoucherById() : ID로 바우처 삭제 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class RemoveVoucherByIdTest {
        @DisplayName("성공 : 레포지토리 단에서 아이디 삭제에 성공한 경우 반환값이 없습니다.")
        @Test
        void removeSuccess() {
            // given
            Voucher voucher = new PercentDiscountVoucher(20);
            doNothing().when(voucherRepository).removeById(voucher.getId());
            // when
            voucherService.removeVoucherById(voucher.getId());
            // then
            verify(voucherRepository, only()).removeById(voucher.getId());
        }

        @DisplayName("실패 : 레포지토리 단에서 아이디 삭제에 실패한 경우 DeleteFailException 예외가 발생합니다.")
        @Test
        void removeFail() {
            // given
            UUID undefinedId = UUID.randomUUID();
            doThrow(new DeleteFailException()).when(voucherRepository).removeById(undefinedId);
            // when, then
            assertThrows(DeleteFailException.class, () -> voucherService.removeVoucherById(undefinedId));
            verify(voucherRepository, only()).removeById(undefinedId);
        }
    }
}