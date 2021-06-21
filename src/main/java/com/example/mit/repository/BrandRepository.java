package com.example.mit.repository;


import com.example.mit.model.ProductBrand;
import com.example.mit.model.projection.NameBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<ProductBrand,Long> {
    @Query(value = "select distinct prb.id, prb.name from product pr join product_brand prb on pr.brand_id=prb.id and pr.category_id=:catId", nativeQuery = true)
    List<NameBrand> findNameUz(Integer catId);


}
