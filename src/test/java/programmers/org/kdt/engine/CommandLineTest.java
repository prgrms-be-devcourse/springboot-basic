package programmers.org.kdt.engine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import programmers.org.kdt.engine.customer.CustomerService;
import programmers.org.kdt.engine.io.BlackList;
import programmers.org.kdt.engine.io.ConsoleIO;
import programmers.org.kdt.engine.voucher.VoucherProperties;
import programmers.org.kdt.engine.voucher.VoucherService;
import programmers.org.kdt.engine.voucher.repository.VoucherRepository;
import programmers.org.kdt.engine.voucher.type.VoucherStatus;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringJUnitConfig
@ActiveProfiles("local")
class CommandLineTest {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineTest.class);

    @Configuration
    @ComponentScan(basePackages = {"programmers.org.kdt.engine", "programmers.org.kdt.configuration"})
    static class Config{
    }

    @Autowired
    ApplicationContext context;

    @Autowired
    VoucherService voucherService;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherProperties voucherProperties;

    @Autowired
    CustomerService customerService;

     static class ConsoleIOStub extends ConsoleIO {
         @Override
         public String input(String message) {
             logger.info(MessageFormat.format("input : {0}", message));
             return message;
         }
         @Override
         public void print(String message) {logger.info(
             MessageFormat.format("print : {0}", message));
         }
    }
    ConsoleIO consoleIO = new ConsoleIOStub();
    CommandLine commandLine = new CommandLine(consoleIO, voucherService, customerService);


    @Nested
    @DisplayName("1. CommandLine Configure test")
    class ConfigurationTest {
        @Test
        @DisplayName("1.1. Create applicationContext")
        public void testApplicationContext() {
            assertThat(context, notNullValue());
        }

        @Test
        @DisplayName("1.2. Get VoucherRepository to Bean")
        public void testVoucherRepositoryCreation() {
            var bean = context.getBean(VoucherRepository.class);
            assertThat(bean, notNullValue());
        }

        @Test
        @DisplayName("1.3. Get VoucherProperties to Bean")
        public void testGetVoucherProperties() {
            var bean = context.getBean(VoucherProperties.class);
            assertThat(bean, notNullValue());
        }

        @Test
        @DisplayName("1.4. Create CommandLine")
        public void testCommandLine() {
            var consoleIO = new ConsoleIOStub();
            var commandLine = new CommandLine(consoleIO, voucherService, customerService);
            assertThat(commandLine, notNullValue());
        }
    }

    @Nested
    @DisplayName("2. BlackList")
    class BlackListTest {
        @Test
        @DisplayName("2.1. Get BlackList")
        public void testGetBlackList() {
            var blackList = new BlackList()
                .getBlackList(voucherProperties.getBlackListFile());
            assertThat(blackList.isEmpty(), is(false));
        }
    }

    @Nested
    @DisplayName("3. RunCreate")
    class RunCreateTest {
        @Nested
        @DisplayName("3.1. Test Get VoucherStatus from user")
        class testGetVoucherStatus {
            @Test
            @DisplayName("3.1.1. input FixedAmountVoucher")
            public void testVoucherStatusInput1() {
                VoucherStatus string1 = VoucherStatus.fromString(
                    "FixedAmountVoucher");
                assertThat(string1, is(VoucherStatus.FIXEDAMOUNTVOUCHER));
            }

            @Test
            @DisplayName("3.1.2. input FixedAmountVoucher")
            public void testVoucherStatusInput2() {
                VoucherStatus string2 = VoucherStatus.fromString(
                    "PercentDiscountVoucher");
                assertThat(string2, is(VoucherStatus.PERCENTDISCOUNTVOUCHER));
            }

            @Test
            @DisplayName("3.1.3. input NULL")
            public void testVoucherStatusInput3() {
                VoucherStatus string3 = VoucherStatus.fromString(
                    "Null");
                assertThat(string3, is(VoucherStatus.NULL));
            }

            @Test
            @DisplayName("3.1.4. input not exist status")
            public void testVoucherStatusInput4() {
                VoucherStatus string4 = VoucherStatus.fromString("not exist status");
                assertThat(string4, is(VoucherStatus.NULL));
            }

            @Test
            @DisplayName("3.1.5. input UpperCase")
            public void testVoucherStatusInput5() {
                VoucherStatus string5 = VoucherStatus.fromString(
                    "FixedAmountVoucher".toUpperCase());
                assertThat(string5, is(VoucherStatus.FIXEDAMOUNTVOUCHER));
            }

            @Test
            @DisplayName("3.1.6. input LowerCase")
            public void testVoucherStatusInput6() {
                VoucherStatus string6 = VoucherStatus.fromString(
                    "PercentDiscountVoucher".toLowerCase());
                assertThat(string6, is(VoucherStatus.PERCENTDISCOUNTVOUCHER));
            }
        }

        @Nested
        @DisplayName("3.2. Test Get Voucher Value from user")
        class testGetVoucherValue {
            Method voucherValueMethod;
            //var voucherValueMok = mock(commandLine.GetVoucherValue.class);

            {
                try {
                    voucherValueMethod = commandLine.getClass()
                        .getDeclaredMethod("getVoucherValue", String.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                voucherValueMethod.setAccessible(true);
            }


            @Test
            @DisplayName("3.2.1. Input integer type")
            public void testVoucherValueInteger() {
                Object ret = null;
                try {
                    ret = voucherValueMethod.invoke(commandLine, "10");
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                assertThat(ret, is(10L));
            }

            @Test
            @DisplayName("3.2.2. Input minus integer type")
            public void testVoucherValueMinusInteger() {
                Object ret = null;
                try {
                    ret = voucherValueMethod.invoke(commandLine, "-10");
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                assertThat(ret, is(-10L));
            }

            @Test
            @DisplayName("3.2.3. Input zero type")
            public void testVoucherValueZero() {
                Object ret = null;
                try {
                    ret = voucherValueMethod.invoke(commandLine, "0");
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                assertThat(ret, is(0L));
            }

            @Test
            @DisplayName("3.2.3. Input float type")
            public void testVoucherValueFloat() {
                Object ret = null;
                try {
                    ret = voucherValueMethod.invoke(commandLine, "1.1");
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                assertThat(ret, is(nullValue()));
            }

            @Test
            @DisplayName("3.2.4. Input float type")
            public void testVoucherValueString() {
                Object ret = null;
                try {
                    ret = voucherValueMethod.invoke(commandLine, "zero");
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                assertThat(ret, is(nullValue()));
            }
        }
    }
}