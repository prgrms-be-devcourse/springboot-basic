//package com.programmers.voucher.repository.loader;
//
//import com.programmers.voucher.voucher.Voucher;
//import org.ini4j.Wini;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Profile;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@Profile("test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@SpringBootTest
//class ListLoaderTest {
//    @Autowired
//    private Wini wini;
//    @Autowired
//    private Loader loader;
//
//    @Test
//    @DisplayName("로드 후 파일과 메모리에 저장된 바우처의 개수가 같아야 한다.")
//    void loadingTest1() {
//
//        int savedVoucherNum = wini.entrySet().size();
//        Map<UUID, Voucher> store = new HashMap<>();
//
//        loader.load(store);
//
//        assertEquals(savedVoucherNum, store.size());
//    }
//}