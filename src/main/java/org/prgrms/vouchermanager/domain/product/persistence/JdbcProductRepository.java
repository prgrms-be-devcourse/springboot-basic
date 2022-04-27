package org.prgrms.vouchermanager.domain.product.persistence;

import org.prgrms.vouchermanager.domain.product.domain.Product;
import org.prgrms.vouchermanager.domain.product.domain.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcProductRepository implements ProductRepository {
    @Override
    public Product insert(Product product) {
        return null;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public void delete(Product product) {

    }
}
