package com.example.mit.repository;


import com.example.mit.model.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<ProductBrand,Long> {

}
