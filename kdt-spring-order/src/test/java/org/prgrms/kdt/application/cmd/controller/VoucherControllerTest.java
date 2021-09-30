package org.prgrms.kdt.application.cmd.controller;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.application.cmd.view.CommandLineView;
import org.prgrms.kdt.common.exception.ExceptionMessage;
import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.prgrms.kdt.customer.repository.JdbcCustomerRepository;
import org.prgrms.kdt.customer.service.CustomerService;
import org.prgrms.kdt.customer.service.CustomerServiceImpl;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.repository.JdbcVoucherRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("database")
class VoucherControllerTest {

    @Configuration
    static class Config{
        @Bean
        public DataSource dataSource(){
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public VoucherRepository voucherRepository(){
            return new JdbcVoucherRepository(namedParameterJdbcTemplate(jdbcTemplate(dataSource())));
        }

        @Bean
        public VoucherService voucherService(){
            return new VoucherService(voucherRepository());
        }

        @Bean
        public CustomerRepository customerRepository(){
            return new JdbcCustomerRepository(namedParameterJdbcTemplate(jdbcTemplate(dataSource())));
        }

        @Bean
        public CustomerService customerService(){
            return new CustomerServiceImpl(customerRepository());
        }
    }

    @Autowired
    VoucherService voucherService;

    @Autowired
    CustomerService customerService;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp(){
        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("create_customer_table.sql"), classPathScript("create_voucher_table.sql"))
                .start();
    }

    @AfterEach
    void afterEach() {
        voucherService.clearAllVouchers();
    }

    @Test
    @DisplayName("유효하지 않은 커맨드가 입력되면 오류 메시지를 출력하고 커맨드를 다시 입력받는다.")
    void testInvalidCommand() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, customerService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("1", "exit");

        //when
        voucherController.start();

        //then
        verify(commandLineViewMock, times(2)).requestCommand();
        verify(commandLineViewMock).showErrorMessage(ExceptionMessage.INVALID_COMMAND.getMessage());
    }

    @Test
    @DisplayName("숫자가 아닌 할인 값을 이용하여 Create 커맨드 실행하면 오류 메시지를 출력하고 커맨드를 다시 입력받는다.")
    void testCreateCommandUsingInvalidDiscountValue() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, customerService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("create_voucher", "exit");
        when(commandLineViewMock.requestVoucherType()).thenReturn("1");
        when(commandLineViewMock.requestDiscountValue()).thenReturn("AAAA");

        //when
        voucherController.start();

        //then
        assertThat(voucherService.getAllVouchers().size(), is(0));

        verify(commandLineViewMock, times(2)).requestCommand();
        verify(commandLineViewMock).requestVoucherType();
        verify(commandLineViewMock).requestDiscountValue();
        verify(commandLineViewMock).showErrorMessage(ExceptionMessage.NUMBER_FORMAT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("Create 커맨드로 FixedVoucher를 생성한다.")
    void testFixedVoucherCreateCommand() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, customerService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("create_voucher", "exit");
        when(commandLineViewMock.requestVoucherType()).thenReturn("1");
        when(commandLineViewMock.requestDiscountValue()).thenReturn("100");

        //when
        voucherController.start();

        //then
        assertThat(voucherService.getAllVouchers().size(), greaterThan(0));

        Voucher newVoucher = voucherService.getAllVouchers().get(0);
        assertThat(newVoucher.getClass(), is(FixedAmountVoucher.class));
        assertThat(newVoucher.discount(200), is(100L));

        verify(commandLineViewMock, times(2)).requestCommand();
        verify(commandLineViewMock).requestVoucherType();
        verify(commandLineViewMock).requestDiscountValue();
    }

    @Test
    @DisplayName("FixedVoucher 생성 시 유효하지 않은 할인 값을 입력하면 오류 메시지를 출력하고 커맨드를 다시 입력받는다.")
    void testFixedVoucherCreateCommandUsingInvalidNumber() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, customerService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("create_voucher", "create_voucher", "create_voucher", "exit");
        when(commandLineViewMock.requestVoucherType()).thenReturn("1");
        when(commandLineViewMock.requestDiscountValue()).thenReturn("-1000", "0", "1000001");

        //when
        voucherController.start();

        //then
        assertThat(voucherService.getAllVouchers().size(), is(0));

        verify(commandLineViewMock, times(4)).requestCommand();
        verify(commandLineViewMock, times(3)).requestVoucherType();
        verify(commandLineViewMock, times(3)).requestDiscountValue();
        verify(commandLineViewMock).showErrorMessage("Amount should be positive");
        verify(commandLineViewMock).showErrorMessage("Amount should not be zero");
        verify(commandLineViewMock).showErrorMessage("Amount should be less than 1000000");
    }

    @Test
    @DisplayName("Create 커맨드로 PercentVoucher를 생성한다.")
    void testPercentVoucherCreateCommand() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, customerService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("create_voucher", "exit");
        when(commandLineViewMock.requestVoucherType()).thenReturn("2");
        when(commandLineViewMock.requestDiscountValue()).thenReturn("10");

        //when
        voucherController.start();

        //then
        assertThat(voucherService.getAllVouchers().size(), greaterThan(0));

        Voucher newVoucher = voucherService.getAllVouchers().get(0);
        assertThat(newVoucher.getClass(), is(PercentDiscountVoucher.class));
        assertThat(newVoucher.discount(200), is(20L));

        verify(commandLineViewMock, times(2)).requestCommand();
        verify(commandLineViewMock).requestVoucherType();
        verify(commandLineViewMock).requestDiscountValue();
    }

    @Test
    @DisplayName("PercentDiscountVoucher 생성 시 유효하지 않은 할인률을 입력하면 오류 메시지를 출력하고 커맨드를 다시 입력받는다.")
    void testPercentVoucherCreateCommandUsingInvalidPercent() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, customerService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("create_voucher", "create_voucher", "exit");
        when(commandLineViewMock.requestVoucherType()).thenReturn("2");
        when(commandLineViewMock.requestDiscountValue()).thenReturn("101", "-10");

        //when
        voucherController.start();

        //then
        assertThat(voucherService.getAllVouchers().size(), is(0));

        verify(commandLineViewMock, times(3)).requestCommand();
        verify(commandLineViewMock, times(2)).requestVoucherType();
        verify(commandLineViewMock, times(2)).requestDiscountValue();
        verify(commandLineViewMock, times(2)).showErrorMessage(ExceptionMessage.INVALID_VOUCHER_PERCENT_RANGE.getMessage());
    }

    @Test
    @DisplayName("List 커맨드를 입력하면 Voucher 리스트를 출력한다.")
    void testListCommand() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, customerService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("list_voucher", "exit");

        //when
        voucherController.start();

        //then
        verify(commandLineViewMock, times(2)).requestCommand();
        verify(commandLineViewMock).showVoucherList(voucherService.getAllVouchers());
    }

    @Test
    @DisplayName("List_Customer 커맨드를 입력하면 Customer 리스트를 출력한다.")
    void testListCustomerCommand() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, customerService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("list_customer", "exit");

        //when
        voucherController.start();

        //then
        verify(commandLineViewMock, times(2)).requestCommand();
        verify(commandLineViewMock).showCustomerList(customerService.getAllCustomers());
    }

    @Test
    @DisplayName("Exit 커맨드를 입력하면 프로그램을 종료한다.")
    void testExitCommand() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, customerService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("exit");

        //when
        voucherController.start();

        //then
        verify(commandLineViewMock).requestCommand();
        verify(commandLineViewMock).close();
    }
}