package com.prgrms.vouchermanagement.core.voucher.repository.file;

import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageTest {

    private FileStorage fileStorage;

    private Path tempFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        tempFilePath = Files.createTempFile("testFileStorage", ".json");
        String initString = "[]";
        Files.write(tempFilePath, initString.getBytes());
        Files.write(tempFilePath, initString.getBytes());
        fileStorage = new FileStorage(tempFilePath.toString());
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFilePath);
    }

    /**
     * 조회
     */
    @DisplayName("파일을 읽으면 VoucherVO를 리스트 형태로 반환한다.")
    @Test
    void readFile() {
        // given
        VoucherVO voucherVO1 = new VoucherVO(UUID.randomUUID().toString(), "voucher1", 10, VoucherType.FIXED);
        VoucherVO voucherVO2 = new VoucherVO(UUID.randomUUID().toString(), "voucher2", 10, VoucherType.RATE);
        VoucherVO voucherVO3 = new VoucherVO(UUID.randomUUID().toString(), "voucher3", 10, VoucherType.FIXED);

        fileStorage.saveFile(voucherVO1);
        fileStorage.saveFile(voucherVO2);
        fileStorage.saveFile(voucherVO3);

        // when
        List<VoucherVO> voucherVOList = fileStorage.readFile();
        int a = 1;

        // then

        assertAll(
                () -> assertThat(voucherVOList.size(), is(3)),
                () -> assertThat(voucherVOList.containsAll(List.of(voucherVO1, voucherVO2, voucherVO3)), is(true))
        );
    }

    /**
     * 추가
     */

    @DisplayName("파일에 VoucherVO를 추가할 수 있다.")
    @Test
    void saveFile() {
        // given
        VoucherVO voucherVO = new VoucherVO(UUID.randomUUID().toString(), "voucher1", 10, VoucherType.FIXED);

        // when
        fileStorage.saveFile(voucherVO);
        List<VoucherVO> voucherVOList = fileStorage.readFile();
        int a = 1;

        // then
        assertThat(voucherVOList.contains(voucherVO), is(true));
    }

    @DisplayName("여러 스레드가 동시에 saveFile 메서드를 호출해도 모든 VoucherVO가 안전하게 저장된다.")
    @Test
    void saveFileWithMultiThread() throws InterruptedException, ExecutionException {
        // given
        int threadCnt = 100;
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<String>> callables = getSaveCallables(threadCnt);
        Set<String> callableExSet = new HashSet<>();

        // when
        List<Future<String>> futures = executorService.invokeAll(callables);
        executorService.shutdown();
        for (Future<String> future : futures) {
            callableExSet.add(future.get());
        }

        // then
        assertThat(callableExSet.size(), is(threadCnt));
    }

    private List<Callable<String>> getSaveCallables(int threadCnt) {
        List<Callable<String>> callables = new ArrayList<>();
        for(int i=0; i<threadCnt; i++) {
            callables.add(() -> {
                VoucherVO voucherVO = new VoucherVO(UUID.randomUUID().toString(), "name", 50, VoucherType.FIXED);
                fileStorage.saveFile(voucherVO);
                return voucherVO.getVoucherID();
            });
        }
        return callables;
    }

    /**
     * 삭제
     */

    @DisplayName("파일에서 VoucherVO를 삭제할 수 있다.")
    @Test
    void deleteFile() {
        // given
        VoucherVO voucherVO1 = new VoucherVO(UUID.randomUUID().toString(), "voucher1", 10, VoucherType.FIXED);
        VoucherVO voucherVO2 = new VoucherVO(UUID.randomUUID().toString(), "voucher2", 10, VoucherType.RATE);
        VoucherVO voucherVO3 = new VoucherVO(UUID.randomUUID().toString(), "voucher3", 10, VoucherType.FIXED);

        fileStorage.saveFile(voucherVO1);
        fileStorage.saveFile(voucherVO2);
        fileStorage.saveFile(voucherVO3);

        // when
        fileStorage.deleteFile(voucherVO1);
        List<VoucherVO> voucherVOList = fileStorage.readFile();

        // then
        assertAll(
                () -> assertThat(voucherVOList.size(), is(2)),
                () -> assertThat(voucherVOList.containsAll(List.of(voucherVO2, voucherVO3)), is(true))
        );
    }

    @DisplayName("여러 스레드가 동시에 deleteFile 메서드를 호출해도 모든 VoucherVO가 안전하게 삭제된다.")
    @Test
    void deleteFileWithMultiThread() throws InterruptedException {
        // given
        int threadCnt = 100;
        String uuid = UUID.randomUUID().toString();
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<Void>> callables = getDeleteCallables(threadCnt, uuid);

        VoucherVO voucherVO = new VoucherVO(uuid, "name", 10, VoucherType.FIXED);
        fileStorage.saveFile(voucherVO);

        // when
        executorService.invokeAll(callables);
        executorService.shutdown();
        List<VoucherVO> voucherVOList = fileStorage.readFile();

        // then
        assertThat(voucherVOList.isEmpty(), is(true));
    }

    private List<Callable<Void>> getDeleteCallables(int threadCnt, String uuid) {
        List<Callable<Void>> callables = new ArrayList<>();
        VoucherVO bookVO = new VoucherVO(uuid, "name", 10, VoucherType.FIXED);
        for(int i=0; i<threadCnt; i++) {
            callables.add(() -> {
                fileStorage.deleteFile(bookVO);
                return null;
            });
        }
        return callables;
    }
}