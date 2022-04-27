package org.prgrms.vouchermanager.domain.product.persistence;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.product.domain.Product;
import org.prgrms.vouchermanager.domain.product.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class JdbcProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    DataSource dataSource;

    @BeforeEach
    void boforeEach() {
        productRepository.deleteAll();
    }

    private void compareProduct(Product actual, Product expected) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        assertThat(actual.getStock()).isEqualTo(expected.getStock());
        assertThat(actual.getStatus()).isEqualTo(expected.getStatus());
        assertThat(actual.getCreatedAt()).isEqualTo(expected.getCreatedAt());
    }

    @Test
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @DisplayName("insert 할 수 있다")
    void insert() {
        Product product = Product.create("tea", 1000, 10);

        productRepository.insert(product);
        Product foundProduct = productRepository.findById(product.getId()).get();

        compareProduct(product, foundProduct);
    }

    @Test
    @DisplayName("id로 Product를 조회할 수 있다")
    void findById() {
        Product product = Product.create("iceCream", 500, 1000);
        UUID id = product.getId();
        productRepository.insert(product);

        Product foundProduct = productRepository.findById(id).get();

        compareProduct(foundProduct, product);
    }

    @Test
    @DisplayName("name으로 조회할 수 있다.")
    void findByName() {
        Product product = Product.create("coffeeBean", 5000, 10);
        String name = product.getName();
        productRepository.insert(product);

        Product foundProduct = productRepository.findByName(name).get();

        compareProduct(foundProduct, product);
    }

    @Test
    @DisplayName("저장된 모든 Product를 조회할 수 있다.")
    void findAll() {
        List<Product> productList = new ArrayList<>();
        IntStream.range(1, 10).forEach(i -> productList.add(productRepository.insert(Product.create("name" + i, i, i))));

        List<Product> found = productRepository.findAll();

        assertThat(found.size()).isEqualTo(productList.size());
        assertThat(found).containsAll(productList);
    }

    @Test
    @DisplayName("삭제할 수 있다.")
    void delete() {
        Product product = Product.create("tea", 1000, 10);
        productRepository.insert(product);

        productRepository.delete(product);
        Optional<Product> found = productRepository.findById(product.getId());

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("products 테이블의 모든 테이더를 삭제할 수 있다.")
    void deleteAll() {
        List<Product> productList = new ArrayList<>();
        IntStream.range(1, 10).forEach(i -> productList.add(productRepository.insert(Product.create("name" + i, i, i))));

        productRepository.deleteAll();
        List<Product> found = productRepository.findAll();

        assertThat(found.size()).isEqualTo(0);
    }

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.vouchermanager.domain.product"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/test_order_mgmt")
                    .username("root")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }
}