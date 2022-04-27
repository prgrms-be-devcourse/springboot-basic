package org.prgrms.vouchermanager.domain.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ProductTest {

    @Nested
    @DisplayName("Product 생성자 테스트")
    class create {
        @Test
        void Product_생성_성공() {
            Product product = new Product(UUID.randomUUID(), "productName", 1000L, 10);

            assertThat(product.getId()).isNotNull();
            assertThat(product.getName()).isEqualTo("productName");
            assertThat(product.getPrice()).isEqualTo(1000);
            assertThat(product.getStock()).isEqualTo(10);
            assertThat(product.getStatus()).isEqualTo(ProductStatus.FOR_SALE);
        }

        @Test
        void id가_null이면_예외를_던진다() {
            assertThatNullPointerException().isThrownBy(() -> new Product(null, "productName", 1000L, 10));
        }

        @ParameterizedTest
        @NullAndEmptySource
        void name이_공백이면_예외를_던진다(String name) {
            assertThatIllegalArgumentException().isThrownBy(() -> new Product(UUID.randomUUID(), name, 1000L, 10));
        }

        @Test
        void price가_음수면_예외를_던진다() {
            assertThatIllegalArgumentException().isThrownBy(() -> new Product(UUID.randomUUID(), "productName", -1000L, 10));
        }

        @Test
        void stock이_음수면_예외를_던진다() {
            assertThatIllegalArgumentException().isThrownBy(() -> new Product(UUID.randomUUID(), "productName", 1000L, -1));
        }

        @Test
        void stock이_0이면_status를_SOLD_OUT으로_설정한다() {
            Product product = new Product(UUID.randomUUID(), "productName", 1000, 0);

            assertThat(product.getStatus()).isEqualTo(ProductStatus.SOLD_OUT);
        }

        @Test
        void stock이_양수이면_status를_FOR_SALE로_설정한다() {
            Product product = new Product(UUID.randomUUID(), "productName", 1000, 1);

            assertThat(product.getStatus()).isEqualTo(ProductStatus.FOR_SALE);
        }
    }

}