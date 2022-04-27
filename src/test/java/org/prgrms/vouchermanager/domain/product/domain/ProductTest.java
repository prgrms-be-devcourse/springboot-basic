package org.prgrms.vouchermanager.domain.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Nested
    @DisplayName("Product 생성자 테스트")
    class create{
        @Test
        void Product_생성_성공() {
        }

        @Test
        void id가_null이면_예외를_던진다() {
        }

        @Test
        void name이_공백이면_예외를_던진다() {
        }

        @Test
        void price가_음수면_예외를_던진다() {
        }

        @Test
        void stock이_음수면_예외를_던진다() {
        }

        @Test
        void stock이_0이면_status를_SOLD_OUT으로_설정한다() {
        }

        @Test
        void stock이_양수이면_status를_FOR_SALE로_설정한다() {

        }
    }
}