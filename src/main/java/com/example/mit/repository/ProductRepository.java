package com.example.mit.repository;


import com.example.mit.model.Product;
import com.example.mit.model.projection.uz_lat.NameUzProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "select name_uz,id,category_id from product where category_id=:category_id",nativeQuery = true)
    List<NameUzProduct> allByCategoryId(@Param("category_id") Integer category_id);


    List<Product> findAllByCategoryId(Long id);

}
