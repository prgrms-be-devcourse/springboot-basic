package org.weekly.weekly.customer.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("!dev")
@Repository
public class JdbcCustomerRepository implements CustomerRepository{

}
