package com.ws101.longcop.dayday.EcommerceApi.Repository;

import com.ws101.longcop.dayday.EcommerceApi.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface providing direct abstraction access layers
 * targeting persistent entities within the MySQL data tables.
 * @author Longcop, Antonio Jr. N.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Custom query finder to isolate product entities matching specific categories.
     * Spring Data JPA parses this method name automatically to create the SQL statement.
     */
    List<Product> findByCategoryIgnoreCase(String category);
}