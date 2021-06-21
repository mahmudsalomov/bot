package com.example.mit.repository;


import com.example.mit.model.Product;
import com.example.mit.model.projection.ru.NameRuProduct;
import com.example.mit.model.projection.uz_kril.NameOzProduct;
import com.example.mit.model.projection.uz_lat.NameUzProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "select id, name_uz,brand_id,category_id,cost,description_uz,main_image from product where brand_id=:brand_id and category_id=:category_id",nativeQuery = true)
    List<NameUzProduct> allUzByBrandId(@Param("brand_id") Long brand_id,@Param("category_id") Integer category_id);

    @Query(value = "select id, name_oz,brand_id,category_id,cost,description_oz,main_image  from product where brand_id=:brand_id and category_id=:category_id",nativeQuery = true)
    List<NameOzProduct> allOzByBrandId(@Param("brand_id") Long brand_id,@Param("category_id") Integer category_id);

    @Query(value = "select id, name_ru,brand_id,category_id,cost,description_ru,main_image from product where brand_id=:brand_id and category_id=:category_id",nativeQuery = true)
    List<NameRuProduct> allRuByBrandId(@Param("brand_id") Long brand_id,@Param("category_id") Integer category_id);

    @Query(value = "select id, name_oz,brand_id,category_id,cost,description_oz, main_image from product where category_id=:category_id",nativeQuery = true)
    List<NameOzProduct> allOzByCategoryId(@Param("category_id") Long category_id);

    @Query(value = "select id, name_uz,brand_id,category_id,cost,description_uz, main_image from product where category_id=:category_id",nativeQuery = true)
    List<NameUzProduct> allUzByCategoryId(@Param("category_id") Long category_id);

    @Query(value = "select id, name_ru,brand_id,category_id,cost,description_ru, main_image from product where category_id=:category_id",nativeQuery = true)
    List<NameRuProduct> allRuzByCategoryId(@Param("category_id") Long category_id);

    @Query(value = "select id, name_oz,brand_id,category_id,cost,description_oz,main_image from product where id=:id",nativeQuery = true)
    NameOzProduct getOzById(Long id);

    List<Product> findAllByCategoryId(Long id);

}
