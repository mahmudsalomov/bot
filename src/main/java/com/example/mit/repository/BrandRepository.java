package com.example.mit.repository;


import com.example.mit.model.ProductBrand;
import com.example.mit.model.projection.uz_lat.NameUzBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<ProductBrand,Long> {
    @Query(value = "", nativeQuery = true)
    List<NameUzBrand> findNameUz(Integer productId);

}
