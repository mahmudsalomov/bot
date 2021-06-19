package com.example.mit.repository;


import com.example.mit.model.ProductCategory;
import com.example.mit.model.projection.uz_lat.NameUzCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<ProductCategory,Long> {
    List<ProductCategory> findAllByParentIdIsNull();
    @Query(value = "select name_uz,id,temp_id,parent_id from product_category where parent_id IS NULL and temp_id IS NOT NULL",nativeQuery = true)
    List<NameUzCategory> findAllNameUz();

    Optional<ProductCategory> findByTempId(Long temp_id);

    @Query(value = "select name_uz,id,temp_id,parent_id from product_category where parent_id=(select temp_id from product_category where id=:id)",
            nativeQuery = true)
    List<NameUzCategory> findChildCategories(@Param("id") Integer id);

    @Query(value = "select name_uz,id,temp_id,parent_id from product_category where temp_id=(select parent_id from product_category where id=:id)",nativeQuery = true)
    List<NameUzCategory> findParentId(@Param("id") Integer id);
}
