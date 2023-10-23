package com.prgrms.vouchermanager.domain;

import com.prgrms.vouchermanager.controller.VoucherController;
import com.prgrms.vouchermanager.exception.EmptyListException;
import com.prgrms.vouchermanager.io.ConsolePrint;
import com.prgrms.vouchermanager.repository.BlacklistFileRepository;
import com.prgrms.vouchermanager.repository.VoucherFileRepository;
import com.prgrms.vouchermanager.service.VoucherService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CommandHandlerTest {
    private final String filePathV = "src/main/resources/voucher_list.csv";
    private final String filePathC = "src/main/resources/customer_blacklist.csv";
    VoucherFileRepository voucherRepository = new VoucherFileRepository(filePathV);
    BlacklistFileRepository customerRepository = new BlacklistFileRepository(filePathC);
    VoucherService service = new VoucherService(voucherRepository, customerRepository);
    private VoucherController controller = new VoucherController(service);
    private CommandHandler handler = new CommandHandler(new ConsolePrint(), controller);
    private InputStream origin;

    @BeforeEach
    public void setUp() {
        // Save the original System.in to restore it after the test
        origin = System.in;
    }

    @AfterEach
    void afterEach() throws IOException {
        System.setIn(origin);

        BufferedWriter bw = new BufferedWriter(new FileWriter(filePathV));

        bw.write("""
                70754a2f-d87d-4f69-af71-1d4bfe855e28,fixed,300
                626b8d5d-3940-4a0d-a3e4-fe6b297e8ad0,percent,20
                8213dfa7-d577-4bb5-86d6-0159b3383f0e,fixed,20000""");
        bw.close();

        bw = new BufferedWriter(new FileWriter(filePathC));

        bw.write("""
                1,카에데하라 카즈하,1999
                2,야에 미코,1994
                3,라이덴 쇼군,1994""");
        bw.close();
    }

//    @Test
//    @DisplayName("create 명령어 입력 시")
//    void getCommandCreate() {
//        String command = "create";
//        InputStream in = new ByteArrayInputStream(command.getBytes());
//        System.setIn(in);
//
//        Command commandEnum = handler.getCommand();
//        assertThat(commandEnum).isEqualTo(Command.CREATE);
//    }
//
//    @Test
//    @DisplayName("list 명령어 입력 시")
//    void getCommandList() {
//        String command = "list";
//        InputStream in = new ByteArrayInputStream(command.getBytes());
//        System.setIn(in);
//
//
//
//        Command commandEnum = handler.getCommand();
//        assertThat(commandEnum).isEqualTo(Command.LIST);
//    }
//
//    @Test
//    @DisplayName("exit 명령어 입력 시")
//    void getCommandExit() {
//        String command = "exit";
//        InputStream in = new ByteArrayInputStream(command.getBytes());
//        System.setIn(in);
//
//        Command commandEnum = handler.getCommand();
//        assertThat(commandEnum).isEqualTo(Command.EXIT);
//    }
//
//    @Test
//    @DisplayName("blacklist 명령어 입력 시")
//    void getCommandBlacklist() {
//        String command = "blacklist";
//        InputStream in = new ByteArrayInputStream(command.getBytes());
//        System.setIn(in);
//
//        Command commandEnum = handler.getCommand();
//        assertThat(commandEnum).isEqualTo(Command.BLACKLIST);
//    }
//
//    @Test
//    @DisplayName("적절하지 않은 명령어 입력")
//    void getCommandError() {
//        String command = "hello";
//        InputStream in = new ByteArrayInputStream(command.getBytes());
//        System.setIn(in);
//
//        assertThrows(NotCorrectCommand.class, handler::getCommand);
//    }

    @Test
    @DisplayName("listExecute 빈 list 반환 시 예외")
    void listExecute() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePathV));
        bw.write("");
        bw.close();

        voucherRepository = new VoucherFileRepository(filePathV);
        customerRepository = new BlacklistFileRepository(filePathC);
        service = new VoucherService(voucherRepository, customerRepository);
        controller = new VoucherController(service);
        handler = new CommandHandler(new ConsolePrint(), controller);

        assertThrows(EmptyListException.class, handler::listExecute);
    }

//    @Test
//    @DisplayName("exit 실행 시 프로그램 종료")
//    void exitExecute() {
//
//    }

    @Test
    @DisplayName("blacklist가 빈 list 반환할 때 예외 발생")
    void blackListExecute() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePathC));
        bw.write("");
        bw.close();

        voucherRepository = new VoucherFileRepository(filePathV);
        customerRepository = new BlacklistFileRepository(filePathC);
        service = new VoucherService(voucherRepository, customerRepository);
        controller = new VoucherController(service);
        handler = new CommandHandler(new ConsolePrint(), controller);

        assertThrows(EmptyListException.class, handler::blackListExecute);
    }

//    @Test
//    @DisplayName("바우처 타입에 따라 discount 값 얻기 - fixed")
//    void getVoucherDiscountFixed() {
//        String command = "30000";
//        InputStream in = new ByteArrayInputStream(command.getBytes());
//        System.setIn(in);
//
//        long discount = handler.getVoucherDiscount(VoucherType.FIXED);
//        assertThat(discount).isEqualTo(30000);
//    }
//
//    @Test
//    @DisplayName("바우처 타입에 따라 discount 값 얻기 - percent")
//    void getVoucherDiscountPercent() {
//        String command = "30";
//        InputStream in = new ByteArrayInputStream(command.getBytes());
//        System.setIn(in);
//
//        long discount = handler.getVoucherDiscount(VoucherType.PERCENT);
//        assertThat(discount).isEqualTo(30000);
//    }
//
//    @Test
//    @DisplayName("바우처 타입에 따라 discount 값 얻기 - 형식 틀림")
//    void getVoucherDiscountNotForm() {
//        String command = "gi";
//        InputStream in = new ByteArrayInputStream(command.getBytes());
//        System.setIn(in);
//
//        assertThrows(NotCorrectForm.class, () -> {
//            long discount = handler.getVoucherDiscount(VoucherType.PERCENT);
//        });
//    }
//
//    @Test
//    @DisplayName("바우처 타입에 따라 discount 값 얻기 - 범위 틀림")
//    void getVoucherDiscountNotScope() {
//        String command = "1200";
//        InputStream in = new ByteArrayInputStream(command.getBytes());
//        System.setIn(in);
//
//        assertThrows(NotCorrectScope.class, () -> {
//            long discount = handler.getVoucherDiscount(VoucherType.PERCENT);
//        });
//    }
//
//    @Test
//    @DisplayName("fixed 바우처 타입 얻기")
//    void getVoucherTypeFixed() {
//        String command = "fixed";
//        InputStream in = new ByteArrayInputStream(command.getBytes());
//        System.setIn(in);
//
//        assertThat(handler.getVoucherType()).isEqualTo(VoucherType.FIXED);
//    }
//
//    @Test
//    @DisplayName("percent 바우처 타입 얻기")
//    void getVoucherTypePercent() {
//        String command = "percent";
//        InputStream in = new ByteArrayInputStream(command.getBytes());
//        System.setIn(in);
//
//        assertThat(handler.getVoucherType()).isEqualTo(VoucherType.PERCENT);
//    }
//
//    @Test
//    @DisplayName("바우처 타입 얻기 - 적절하지 않은 형식")
//    void getVoucherTypeError() {
//        String command = "akak";
//        InputStream in = new ByteArrayInputStream(command.getBytes());
//        System.setIn(in);
//
//        assertThrows(NotCorrectForm.class, () -> {
//            handler.getVoucherType();
//        });
//    }
}
