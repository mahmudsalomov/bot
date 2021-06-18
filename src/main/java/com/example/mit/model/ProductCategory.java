package com.example.mit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product_category")
public class ProductCategory {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "temp_id")
    private Long tempId;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_oz")
    private String nameOz;

    @Column(name = "name_ru")
    private String nameRu;

    @Type(type = "jsonb")
    @Column(name = "description_uz",columnDefinition = "jsonb")
    private String descriptionUz;

    @Type(type = "jsonb")
    @Column(name = "description_oz",columnDefinition = "jsonb")
    private String descriptionOz;

    @Type(type = "jsonb")
    @Column(name = "description_ru",columnDefinition = "jsonb")
    private String descriptionRu;

    @Column(name = "slug")
    private String slug;

    @Column(name = "lft")
    private Long lft;

    @Column(name = "rgt")
    private Long rgt;

    @Column(name = "depth")
    private Long depth;

    @Column(name = "image")
    private String image;

//    @Type(type = "jsonb")
    @Column(name = "option_group",columnDefinition = "jsonb")
    private String optionGroup;

//    @Type(type = "jsonb")
    @Column(name = "meta_json_uz",columnDefinition = "jsonb")
    private String metaJsonUz;

//    @Type(type = "jsonb")
    @Column(name = "meta_json_oz",columnDefinition = "jsonb")
    private String metaJsonOz;

//    @Type(type = "jsonb")
    @Column(name = "meta_json_ru",columnDefinition = "jsonb")
    private String metaJsonRu;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

//    @Type(type = "jsonb")
    @Column(name = "filters",columnDefinition = "jsonb")
    private String filters;

    @Column(name = "status")
    private Long status;

    @Column(name = "is_main")
    private Long isMain;

    @Column(name = "mobile_image")
    private String mobileImage;

    @Column(name = "remote_id")
    private String remoteId;


}
