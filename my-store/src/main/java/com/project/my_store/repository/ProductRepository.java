package com.project.my_store.repository;

import com.project.my_store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryName(String category);  //'Name' we are going to find by name of category,findBy indicates a query operation and CategoryName is the property to query (resolves to category.name)
                                                        //This follows Spring Data JPA's naming convention to automatically generate queries
    List<Product> findByBrand(String brand);           //category is a class and brand is an object, hence the 'Name' specification in one and not other

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand, String name);

}

