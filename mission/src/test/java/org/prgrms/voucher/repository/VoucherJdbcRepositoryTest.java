package org.prgrms.voucher.repository;

import org.junit.jupiter.api.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoucherJdbcRepositoryTest {

    @Nested
    @Order(1)
    @DisplayName("Repository save 메서드는")
    class DescribeCreate {

        @Nested
        @Order(1)
        @DisplayName("save 기능을 테스트 할 때 바우처 객체를 null로 받으면")
        class ContextReceiveFixedVoucherTypeAndWrongValue {

            @Test
            @DisplayName("잘못된 인자 예외를 던진다.")
            void itIllegalArgumentExceptionThrow() {

            }
        }

        @Nested
        @Order(2)
        @DisplayName("save 기능을 테스트 할 때 알 수 없는 ID의 바우처 객체를 인자로 받으면")
        class ContextReceiveWrongVoucher {

            @Test
            @DisplayName("잘못된 바우처 예외를 반환한다.")
            void itReturnWrongVoucherException() {

            }
        }

        @Nested
        @Order(3)
        @DisplayName("save 기능을 테스트 할 때 새로 생성된 바우처(id = null) 객체를 인자로 받으면")
        class ContextReceiveNullVoucherType {

            @Test
            @DisplayName("데이터 베이스 저장소에 저장하고 저장한 바우처를 반환한다.")
            void itVoucherSaveAndReturn() {

            }
        }

        @Nested
        @Order(4)
        @DisplayName("save 기능을 테스트 할 때 이미 있는 ID의 바우처 객체를 인자로 받으면")
        class ContextReceiveDuplicateId {

            @Test
            @DisplayName("해당 바우처를 업데이트 한다.")
            void itUpdateThisVoucher() {

            }
        }
    }

    @Nested
    @Order(2)
    @DisplayName("Repository findAll 메서드는")
    class DescribeFindAllMethod {

        @Nested
        @DisplayName("호출이 되면")
        class ContextCallThis {

            @Test
            @DisplayName("저장소의 바우처 정보를 리스트로 반환한다.")
            void itReturnVoucherList() {

            }
        }
    }
}