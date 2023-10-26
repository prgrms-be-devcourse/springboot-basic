package com.prgrms.vouchermanagement.core.voucher.repository.memory;

import com.prgrms.vouchermanagement.core.voucher.domain.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class MemoryVoucherRepositoryTest {

    @Configuration
    static class Config {

        @Bean
        public MemoryVoucherRepository memoryVoucherRepository() {
            return new MemoryVoucherRepository();
        }
    }

    @BeforeEach
    public void setUp() {
        memoryVoucherRepository.deleteAll();
    }


    @Autowired
    private MemoryVoucherRepository memoryVoucherRepository;

    @DisplayName("Voucher를 추가하면 id값이 할당되고, 저장소에 추가된다.")
    @Test
    void save() {
        // given
        Voucher voucher = new Voucher("sujin", 1000, "fixed");

        // when
        memoryVoucherRepository.save(voucher);

        // then
        assertAll(
                () -> assertThat(voucher.getVoucherID().isEmpty(), is(false)),
                () -> assertThat(memoryVoucherRepository.findAll().isEmpty(), is(false))
        );
    }

    @DisplayName("동시에 여러 개의 Voucher가 저장소에 추가될 수 있다.")
    @Test
    void saveWithMulitThread() throws InterruptedException, ExecutionException {
        // given
        int threadCnt = 100;
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<String>> ans = getCallables(threadCnt);
        Set<String> callableExSet = new HashSet<>();

        // when
        List<Future<String>> futures = executorService.invokeAll(ans);
        executorService.shutdown();
        for (Future<String> future : futures) {
            callableExSet.add(future.get());
        }

        // then
        assertThat(callableExSet.size(), is(threadCnt));
    }

    private List<Callable<String>> getCallables(int threadCnt) {
        List<Callable<String>> callables = new ArrayList<>();
        for (int i=0; i<threadCnt; i++) {
            callables.add(() -> {
                Voucher voucher = new Voucher("sujin", 100, "fixed");
                Voucher savedVoucher = memoryVoucherRepository.save(voucher);
                return savedVoucher.getVoucherID();
            });
        }
        return callables;
    }

    @DisplayName("Voucher 전체 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        // given
        Voucher voucher1 = new Voucher("voucher1", 1000, "fixed");
        Voucher voucher2 = new Voucher("voucher2", 50, "rate");

        Voucher savedVoucher1 = memoryVoucherRepository.save(voucher1);
        Voucher savedVoucher2 = memoryVoucherRepository.save(voucher2);

        // when
        List<Voucher> voucherList = memoryVoucherRepository.findAll();

        // then
        assertAll(
                () -> assertThat(voucherList.size(), is(2)),
                () -> assertThat(voucherList.contains(savedVoucher1), is(true)),
                () -> assertThat(voucherList.contains(savedVoucher2), is(true))
        );
    }

}