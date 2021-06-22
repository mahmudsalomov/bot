package com.example.mit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "temp_id")
    private Long tempId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_oz")
    private String nameOz;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "model")
    private String model;

//    @Type(type = "jsonb")
    @Column(name = "description_uz",columnDefinition = "jsonb")
    private String descriptionUz;

//    @Type(type = "jsonb")
    @Column(name = "description_oz",columnDefinition = "jsonb")
    private String descriptionOz;

//    @Type(type = "jsonb")
    @Column(name = "description_ru",columnDefinition = "jsonb")
    private String descriptionRu;

//    @Type(type = "jsonb")
    @Column(name = "option",columnDefinition = "jsonb")
    private String option;

//    @Type(type = "jsonb")
    @Column(name = "tag",columnDefinition = "jsonb")
    private String tag;

//    @Type(type = "jsonb")
    @Column(name = "main_image",columnDefinition = "jsonb")
    private String mainImage;

//    @Type(type = "jsonb")
    @Column(name = "images",columnDefinition = "jsonb")
    private String images;

//    @Type(type = "jsonb")
    @Column(name = "stat",columnDefinition = "jsonb")
    private String stat;

//    @Type(type = "jsonb")
    @Column(name = "price",columnDefinition = "jsonb")
    private String price;

    @Column(name = "state")
    private Long state;

    @Column(name = "status")
    private Long status;

    @Column(name = "slug")
    private String slug;

//    @Type(type = "jsonb")
    @Column(name = "meta_json_uz",columnDefinition = "jsonb")
    private String metaJsonUz;

//    @Type(type = "jsonb")
    @Column(name = "meta_json_oz",columnDefinition = "jsonb")
    private String metaJsonOz;

//    @Type(type = "jsonb")
    @Column(name = "meta_json_ru",columnDefinition = "jsonb")
    private String metaJsonRu;

//    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private String similar;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "sort")
    private Long sort;

//    @Type(type = "jsonb")
    @Column(name = "installment",columnDefinition = "jsonb")
    private String installment;

//    @Type(type = "jsonb")
    @Column(name = "categories",columnDefinition = "jsonb")
    private String categories;

    @Column(name = "product_group_id")
    private Long productGroupId;

//    @Type(type = "jsonb")
    @Column(name = "zmarket",columnDefinition = "jsonb")
    private String zmarket;

    @Column(name = "remote_id")
    private String remoteId;

//    @Type(type = "jsonb")
    @Column(name = "actual_price",columnDefinition = "jsonb")
    private String actualPrice;

//    @Type(type = "jsonb")
    @Column(name = "old_price",columnDefinition = "jsonb")
    private String oldPrice;

//    @Type(type = "jsonb")
    @Column(name = "cost",columnDefinition = "jsonb")
    private String cost;

    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "is_min")
    private Long isMin;

    @Column(name = "moysklad_hash")
    private String moyskladHash;

//    @Type(type = "jsonb")
    @Column(name = "installment18",columnDefinition = "jsonb")
    private String installment18;

//    @Type(type = "jsonb")
    @Column(name = "video",columnDefinition = "jsonb")
    private String video;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "group_name_id")
    private Long groupNameId;

//    @Type(type = "jsonb")
    @Column(name = "sync",columnDefinition = "jsonb")
    private String sync;

    @Column(name = "merchant")
    private String merchant;

    @Column(name = "partner_portion")
    private Long partnerPortion;

    @Column(name = "partnership_status")
    private Long partnershipStatus;

    @Column(name = "price_changed_at")
    private Long priceChangedAt;

//    @Type(type = "jsonb")
    @Column(name = "partner_bonus",columnDefinition = "jsonb")
    private String partnerBonus;

    @Column(name = "installment_status")
    private Long installmentStatus;


}
