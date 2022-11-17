package org.prgrms.kdt.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BlackCustomerManagerTest {

    private final BlackCustomerManager blackCustomerManager = new BlackCustomerManager();

    @Test
    @DisplayName("Csv 파일에서 블랙리스트 고객을 읽어올 수 있다.")
    void blacklist() {
        // given
        List<Customer> customers = List.of(
                new Customer("1", "바퀴벌레"),
                new Customer("2", "곱등이"),
                new Customer("3", "돈벌레"),
                new Customer("4", "연가시"),
                new Customer("5", "미세먼지"),
                new Customer("6", "공기청정기"),
                new Customer("7", "기차"),
                new Customer("8", "차표"),
                new Customer("9", "표범"),
                new Customer("10", "범인"),
                new Customer("11", "인간"),
                new Customer("12", "간장"),
                new Customer("13", "장난꾸러기"),
                new Customer("14", "기차"),
                new Customer("15", "차표"),
                new Customer("16", "표범"),
                new Customer("17", "범인"),
                new Customer("18", "인간"),
                new Customer("19", "간장"),
                new Customer("20", "장난꾸러기")
        );

        // when
        List<Customer> actual = blackCustomerManager.findAll();

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(customers);

    }

}