package org.prgrms.vouchermanager.domain.product.persistence;

import org.prgrms.vouchermanager.domain.product.domain.Product;
import org.prgrms.vouchermanager.domain.product.domain.ProductRepository;
import org.prgrms.vouchermanager.domain.product.domain.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkState;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final Logger log = LoggerFactory.getLogger(JdbcProductRepository.class);
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> productRowMapper = (resultSet, rowNum) -> {
        UUID id = UUIDBytesToUUID(resultSet.getBytes("product_id"));
        String name = resultSet.getString("name");
        long price = resultSet.getLong("price");
        long stock = resultSet.getLong("stock");
        ProductStatus status = ProductStatus.valueOf(resultSet.getString("status"));
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return Product.bind(id, name, price, stock, status, createdAt);
    };

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private UUID UUIDBytesToUUID(byte[] customer_id) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(customer_id);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Product insert(Product product) {
        int theNumberOfRowsAffected = jdbcTemplate.update("INSERT INTO products(product_id, name, price, stock, status, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?, ?, ?)",
                product.getId().toString().getBytes(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getStatus().toString(),
                Timestamp.valueOf(product.getCreatedAt()));

        checkState(theNumberOfRowsAffected == 1, "잘못된 삽입입니다.");
        return product;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM products WHERE product_id = UUID_TO_BIN(?)",
                    productRowMapper,
                    id.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            log.error(MessageFormat.format("Product findById: {0} 반환 결과가 1개 행이 아닙니다.", id));
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM products WHERE name = ?",
                    productRowMapper,
                    name));
        } catch (EmptyResultDataAccessException e) {
            log.error(MessageFormat.format("Product findByName: {0} 반환 결과가 1개 행이 아닙니다.", name));
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products", productRowMapper);
    }

    @Override
    public void delete(Product product) {
        int theNumberOfRowsDeleted = jdbcTemplate.update("DELETE FROM products WHERE product_id = UUID_TO_BIN(?)",
                product.getId().toString().getBytes());

        if (theNumberOfRowsDeleted != 1) {
            log.error(MessageFormat.format("productId : {0} 반환 결과가 1개 행이 아닙니다.", product.getId()));
            throw new IllegalArgumentException(MessageFormat.format("customerId : {0} 반환 결과가 1개 행이 아닙니다. {1}개의 행이 삭제 되었습니다.", product.getId(), theNumberOfRowsDeleted));
        }
    }

    @Override
    public void deleteAll() {
        int theNumberOfRowsDeleted = jdbcTemplate.update("DELETE FROM products");
        log.info("Product : 전부 {} 행이 삭제되었습니다", theNumberOfRowsDeleted);
    }
}
