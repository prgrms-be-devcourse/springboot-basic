package team.marco.voucher_management_system;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.marco.voucher_management_system.common.StdIOTest;
import team.marco.voucher_management_system.util.Console;

class VoucherManagementSystemApplicationTest extends StdIOTest {
    private static final String BEAN_OVERRIDING = "--spring.main.allow-bean-definition-overriding=true";
    private static final String ACTIVE_PROD = "--spring.profiles.active=prod";
    private static final String SELECT_APPLICATION_MESSAGE = """
            === 실행할 애플리케이션을 선택해주세요. ===
            0. Console Application
            1. Web Application""";
    private static final String UNSUPPORTED_APPLICATION_MESSAGE = "사용할 수 없는 애플리케이션 입니다.";

    private String[] args;

    @BeforeEach
    void setup() {
        args = new String[0];

        /*
         * To avoid exception
         * BeanDefinitionOverrideException : Cause By DataSource from TestJdbcConfiguration.class
         * NoUniqueBeanDefinitionException : Cause By memoryVoucherRepository, jdbcVoucherRepository
         */
        addProgramArgument(BEAN_OVERRIDING, ACTIVE_PROD);
        Console.close();
    }

    @Test
    @DisplayName("애플리케이션 선택 화면을 출력한다.")
    void testSelectApplication() {
        // given
        setStdin("0");

        // when
        VoucherManagementSystemApplication.main(args);

        // then
        String stdout = getStdout();

        assertThat(stdout).startsWith(SELECT_APPLICATION_MESSAGE);
    }

    @Test
    @DisplayName("잘못된 입력일 경우 메시지를 출력한다.")
    void testReselectApplication() {
        // given
        setStdin("\n", "IT CANT BE COMMAND INPUT", "0", "exit");

        // when
        VoucherManagementSystemApplication.main(args);

        // then
        String stdout = getStdout();

        assertThat(stdout).contains(UNSUPPORTED_APPLICATION_MESSAGE);
    }

    @Test
    @DisplayName("콘솔 애플리케이션을 실행할 수 있다.")
    void testRunConsoleApplication() {
        // given
        setStdin("\n", "0", "exit");

        // when
        VoucherManagementSystemApplication.main(args);

        // then
        String stdout = getStdout();

        assertThat(stdout).contains("""
                === 쿠폰 관리 프로그램 ===
                exit: 프로그램 종료
                create: 쿠폰 생성
                list: 쿠폰 목록 조회
                blacklist: 블랙 리스트 유저 조회
                customer: 고객 관리 메뉴
                wallet: 지갑 관리 메뉴""");
    }

    @Test
    @DisplayName("콘솔 애플리케이션을 종료할 수 있다.")
    void testCloseConsoleApplication() {
        // given
        setStdin("\n", "0", "exit");

        // when
        VoucherManagementSystemApplication.main(args);

        // then
        String stdout = getStdout();

        assertThat(stdout).contains("프로그램이 종료되었습니다.");
    }

    @Test
    @DisplayName("웹 애플리케이션을 실행할 수 있다.")
    void testRunWebApplication() {
        // given
        setStdin("1");

        // when
        VoucherManagementSystemApplication.main(args);

        // then
        String stdout = getStdout();

        assertThat(stdout).contains("t.m.v.web_app.WebApplication -- Started WebApplication");
    }

    private void addProgramArgument(String... args) {
        this.args = ArrayUtils.addAll(this.args, args);
    }
}
