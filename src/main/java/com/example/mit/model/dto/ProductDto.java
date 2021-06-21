package com.example.mit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;

    private Long brandId;

    private Integer categoryId;

    private String cost;

    private String description;

    private String mainImage;

    private String name;

    private String price;

    private Long quantity;

    private String slug;

    private Long status;

}
