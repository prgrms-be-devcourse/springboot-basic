package org.prgrms.deukyun.voucherapp.domain.common.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.order.entity.Order;
import org.prgrms.deukyun.voucherapp.domain.order.entity.OrderItem;
import org.prgrms.deukyun.voucherapp.domain.order.repository.MemoryOrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

class MemoryRepositoryTest {

    MemoryOrderRepository memoryRepository;
    Order order;

    @BeforeEach
    void setup() {
        memoryRepository = new MemoryOrderRepository();
        order = dummyOrder();
    }

    private void assertOrder(Order actualOrder, Order expectedOrder) {
        assertThat(actualOrder).isNotNull();
        assertThat(actualOrder.getId()).isNotNull();
        assertThat(actualOrder.getOrderItems()).containsAll(expectedOrder.getOrderItems());
        assertThat(actualOrder.getVoucher()).isEqualTo(expectedOrder.getVoucher());
        assertThat(actualOrder.getOrderStatus()).isEqualTo(expectedOrder.getOrderStatus());
        assertThat(actualOrder.getCustomerId()).isEqualTo(expectedOrder.getCustomerId());
    }

    private Order dummyOrder() {
        return Order.createOrder(UUID.randomUUID(), dummyOrderItems());
    }

    private List<OrderItem> dummyOrderItems() {
        return List.of(new OrderItem(UUID.randomUUID(), 1000L, 20),
                new OrderItem(UUID.randomUUID(), 5000L, 5));
    }

    @Nested
    class insertTest {

        @Test
        void givenOrder_whenCallInsert_thenIdIsSet() {
            //when
            memoryRepository.insert(order);

            //assert
            assertThat(order.getId()).isNotNull();
        }

        @Test
        void givenOrder_whenCallInsert_thenReturnsInsertedOrder() {
            //when
            Order insertedOrder = memoryRepository.insert(order);

            //assert
            assertOrder(insertedOrder, order);
        }

        @Test
        void givenInsertedOrder_whenCallInsertAgainWithSameOrderInstance_thenThrowsIllegalStateException() {
            //setup
            memoryRepository.insert(order);

            //assert throws
            assertThatIllegalStateException()
                    .isThrownBy(() -> memoryRepository.insert(order));
        }
    }

    @Nested
    class findAllTest {

        @Test
        void givenTwoOrderInsertion_whenCallFindAll_thenGivesTwoOrders() {
            //setup
            Order order1 = dummyOrder();
            Order order2 = dummyOrder();
            memoryRepository.insert(order1);
            memoryRepository.insert(order2);

            //when
            List<Order> orders = memoryRepository.findAll();

            //assert
            assertThat(orders).extracting("customerId")
                    .containsExactlyInAnyOrder(order1.getCustomerId(), order2.getCustomerId());
        }
    }

    @Nested
    class findByIdTest {

        UUID id;

        @BeforeEach
        void setup() {
            memoryRepository.insert(order);
        }

        @Test
        void givenIdOfInsertedOrder_whenCallFindById_thenReturnFoundOrderInstance() {
            //setup
            id = order.getId();

            //when
            Optional<Order> foundOrder = memoryRepository.findById(id);

            //assert
            assertThat(foundOrder).isPresent();
            assertOrder(foundOrder.get(), order);
        }

        @Test
        void givenInvalidId_whenCallFindById_thenReturnOptionalEmpty() {
            //setup
            id = UUID.randomUUID();

            //when
            Optional<Order> foundOrder = memoryRepository.findById(id);

            //assert
            assertThat(foundOrder).isNotPresent();
        }
    }
}